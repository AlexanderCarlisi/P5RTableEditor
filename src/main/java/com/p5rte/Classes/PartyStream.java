package com.p5rte.Classes;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.p5rte.Utils.Constants;
import com.p5rte.Utils.Enums.EPartyMember;
import com.p5rte.Utils.Enums.EPartyMemberPersona;
import com.p5rte.Utils.FileStreamUtil;

public class PartyStream {
    
    private static PartyMember[] m_partyMembers;


    public static void start(boolean readIndividualLevelThresholds, boolean readIndividualSkills) {
        readPartyMembers(readIndividualLevelThresholds, readIndividualSkills);
    }


    /**
     * Reads the party members from the input file
     * @param readIndividualLevelThresholds : If false, the level threshold is read only from the first pm, and set to the others.
     */
    private static void readPartyMembers(boolean readIndividualLevelThresholds, boolean readIndividualSkills) {

        m_partyMembers = new PartyMember[9];

        try (FileInputStream personaInputStream = new FileInputStream(Constants.Path.INPUT_PERSONA_TABLE)) {
            // Persona TBL Segment 2 : Initialize party members
            personaInputStream.skip(39012);

            int[] levelThreshold = null;
            for (int pm = 0; pm < m_partyMembers.length; pm++) {

                if (levelThreshold == null || readIndividualLevelThresholds) {
                    levelThreshold = new int[98];
                    for (int i = 0; i < 98; i++) {
                        levelThreshold[i] = FileStreamUtil.readInt(personaInputStream);
                    }
                }
                
                m_partyMembers[pm] = new PartyMember(levelThreshold, EPartyMember.values()[pm+1]); // Skip protagonist
            }

            // Persona TBL Segment 3 : Party Persona Data
            int bytesToSegment3 = (readIndividualLevelThresholds) ? 8 : 3144;
            personaInputStream.skip(bytesToSegment3);

            int personaIndex = 0;
            for (int pm = 0; pm < m_partyMembers.length - 1; pm++) { // Kasumi not included here (-1)
                m_partyMembers[pm].personas[0] = readPartyMemberPersona(personaInputStream, EPartyMemberPersona.values()[personaIndex]);
                personaIndex++;
            }

            personaInputStream.skip(1244); // 2 Blank PMs

            for (int pm = 0; pm < m_partyMembers.length - 1; pm++) { // Kasumi not included here (-1)
                m_partyMembers[pm].personas[1] = readPartyMemberPersona(personaInputStream, EPartyMemberPersona.values()[personaIndex], (readIndividualSkills) ? null : m_partyMembers[pm].personas[0].getSkills());
                personaIndex++;
            }

            personaInputStream.skip(11196); // 18 Blank PMs
            personaInputStream.skip(1244); // Skip Akechi (I think this ones fake)

            // Kasumi's First 2 Personas, her index is 8
            m_partyMembers[8].personas[0] = readPartyMemberPersona(personaInputStream, EPartyMemberPersona.Cendrillon);
            m_partyMembers[8].personas[1] = readPartyMemberPersona(personaInputStream, EPartyMemberPersona.Vanadis, (readIndividualSkills) ? null : m_partyMembers[8].personas[0].getSkills());
            personaIndex += 2;

            // 3rd Evolution personas
            for (int pm = 0; pm < m_partyMembers.length; pm++) {
                m_partyMembers[pm].personas[2] = readPartyMemberPersona(personaInputStream, EPartyMemberPersona.values()[personaIndex], (readIndividualSkills) ? null : m_partyMembers[pm].personas[0].getSkills());
                personaIndex++;
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void writeToTables(boolean readIndividualLevelThresholds, boolean readIndividualSkills) {
        try(
            RandomAccessFile rafPersona = new RandomAccessFile(Constants.Path.OUTPUT_PERSONA_TABLE, "rw");
            ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
            DataOutputStream dos = new DataOutputStream(baos)) 
        {

            rafPersona.seek(39012);

            // Level Thresholds Seg 2
            for (int i = 0; i < 9; i++) {
                for (int lvl = 0; lvl < 98; lvl++) {
                    if (readIndividualLevelThresholds)
                        dos.writeInt(m_partyMembers[i].levelThreshold[lvl]);
                    else
                        dos.writeInt(m_partyMembers[0].levelThreshold[lvl]);
                }
            }

            rafPersona.write(baos.toByteArray());
            baos.reset();
            rafPersona.seek(42548);

            // Seg 3, Personas
            for (int i = 0; i < 8; i++) {
                serializePartyPersona(dos, i, 0, null);
            }

            dos.write(new byte[1244]); // 2 Blank PMs

            for (int i = 0; i < 8; i++) {
                serializePartyPersona(dos, i, 1, (readIndividualSkills) ? null : m_partyMembers[i].personas[0].getSkills());
            }

            dos.write(new byte[11196]); // 18 Blank PMs
            dos.write(new byte[1244]); // Skip Akechi (I think this ones fake)
            
            // Kasumi's First 2 Personas
            serializePartyPersona(dos, 8, 0, null);
            serializePartyPersona(dos, 8, 1, (readIndividualSkills) ? null : m_partyMembers[8].personas[0].getSkills());

            // 3rd Evolution personas
            for (int i = 0; i < 9; i++) {
                serializePartyPersona(dos, i, 2, (readIndividualSkills) ? null : m_partyMembers[i].personas[0].getSkills());
            }

            rafPersona.write(baos.toByteArray());
            
            rafPersona.close();
            baos.close();
            dos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Reads the next PartyMember Persona from the input stream.
     * @return
     */
    private static PartyMemberPersona readPartyMemberPersona(FileInputStream personaInputStream, EPartyMemberPersona partyPersona) throws IOException {
        return readPartyMemberPersona(personaInputStream, partyPersona, null);
    }

    /**
     * Reads the next PartyMember Persona from the input stream.
     * @return
     */
    private static PartyMemberPersona readPartyMemberPersona(FileInputStream personaInputStream, EPartyMemberPersona partyPersona, Skill[] skills) throws IOException {
       
        // Skipping Character (because you shouldnt change that), Levels Available (because it doesnt do anything), and a blank byte
        personaInputStream.skip(4);

        if (skills == null)
            skills = FileStreamUtil.readSkills(personaInputStream, 32);
        else personaInputStream.skip(32 * 4);

        int[][] statGain = new int[98][5];
        
        for (int lvl = 0; lvl < 98; lvl++) {
            for (int stat = 0; stat < 5; stat++) {
                statGain[lvl][stat] = personaInputStream.read();
            }
        }

        return new PartyMemberPersona(partyPersona, skills, statGain);
    }


    private static void serializePartyPersona(DataOutputStream dos, int pmIndex, int personaIndex, Skill[] skills) throws IOException {
        PartyMemberPersona partyPersona = m_partyMembers[pmIndex].personas[personaIndex];

        dos.writeShort(pmIndex + 2); // character
        dos.writeByte(99); // levels available
        dos.writeByte(0); // blank byte

        // Skills
        if (skills == null) skills = partyPersona.getSkills();
        for (Skill skill : skills) {
            dos.writeByte(skill.getPendingLevels());
            dos.writeByte(skill.getLearnability().ID);
            dos.writeShort(skill.getID());
        }

        // Stat Gains
        for (int lvl = 0; lvl < 98; lvl++) {
            for (int stat = 0; stat < 5; stat++) {
                dos.writeByte(partyPersona.statGain[lvl][stat]);
            }
        }
    }


    public static void restart(boolean readIndividualLevelThresholds, boolean readIndividualSkills) {
        m_partyMembers = null;
        start(readIndividualLevelThresholds, readIndividualSkills);
    }


    public static PartyMember getPartyMember(EPartyMember partyMember) {
        return m_partyMembers[partyMember.getPMIndex()];
    }


    public static Persona getPersona(EPartyMember partyMember, int personaIndex) {
        return PersonaStream.getPersona(getPartyMember(partyMember).personas[personaIndex].epartyPersona.PERSONA_INDEX);
    }
}
