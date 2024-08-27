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
    
    private static PartyMember[] s_partyMembers;
    private static boolean s_writeThresholdsIndividually = true;


    public static void start() {
        readPartyMembers();
    }


    /**
     * Reads the party members from the input file
     * @param readIndividualLevelThresholds : If false, the level threshold is read only from the first pm, and set to the others.
     */
    private static void readPartyMembers() {

        s_partyMembers = new PartyMember[9];

        try (FileInputStream personaInputStream = new FileInputStream(Constants.Path.INPUT_PERSONA_TABLE)) {
            // Persona TBL Segment 2 : Initialize party members
            personaInputStream.skip(39012);

            for (int pm = 0; pm < s_partyMembers.length; pm++) {
                s_partyMembers[pm] = new PartyMember(EPartyMember.values()[pm+1]); // Skip protagonist

                int[] levelThreshold = new int[98];
                for (int i = 0; i < 98; i++) {
                    levelThreshold[i] = FileStreamUtil.readInt(personaInputStream);
                }

                s_partyMembers[pm].levelThreshold = levelThreshold;
            }

            // Persona TBL Segment 3 : Party Persona Data
            personaInputStream.skip(8);

            int personaIndex = 0;
            for (int pm = 0; pm < s_partyMembers.length - 1; pm++) { // Kasumi not included here (-1)
                s_partyMembers[pm].personas[0] = readPartyMemberPersona(personaInputStream, EPartyMemberPersona.values()[personaIndex], 0);
                personaIndex++;
            }

            personaInputStream.skip(1244); // 2 Blank PMs

            for (int pm = 0; pm < s_partyMembers.length - 1; pm++) { // Kasumi not included here (-1)
                s_partyMembers[pm].personas[1] = readPartyMemberPersona(personaInputStream, EPartyMemberPersona.values()[personaIndex], 1);
                personaIndex++;
            }

            personaInputStream.skip(11196); // 18 Blank PMs
            personaInputStream.skip(1244); // Skip Akechi (I think this ones fake)

            // Kasumi's First 2 Personas, her index is 8
            s_partyMembers[8].personas[0] = readPartyMemberPersona(personaInputStream, EPartyMemberPersona.Cendrillon, 0);
            s_partyMembers[8].personas[1] = readPartyMemberPersona(personaInputStream, EPartyMemberPersona.Vanadis, 1);
            personaIndex += 2;

            // 3rd Evolution personas
            for (int pm = 0; pm < s_partyMembers.length; pm++) {
                s_partyMembers[pm].personas[2] = readPartyMemberPersona(personaInputStream, EPartyMemberPersona.values()[personaIndex], 2);
                personaIndex++;
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void writeToTables() {
        try(
            RandomAccessFile rafPersona = new RandomAccessFile(Constants.Path.OUTPUT_PERSONA_TABLE, "rw");
            ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
            DataOutputStream dos = new DataOutputStream(baos)) 
        {

            rafPersona.seek(39012);

            // Level Thresholds Seg 2
            for (int i = 0; i < 9; i++) {
                for (int lvl = 0; lvl < 98; lvl++) {
                    // should probably flip the loop and ifs
                    if (s_writeThresholdsIndividually)
                        dos.writeInt(s_partyMembers[i].levelThreshold[lvl]);
                    else
                        dos.writeInt(s_partyMembers[0].levelThreshold[lvl]);
                }
            }

            rafPersona.write(baos.toByteArray());
            baos.reset();
            rafPersona.seek(42548);

            // Seg 3, Personas
            for (int i = 0; i < 8; i++) {
                serializePartyPersona(dos, i, 0);
            }

            dos.write(new byte[1244]); // 2 Blank PMs

            for (int i = 0; i < 8; i++) {
                serializePartyPersona(dos, i, 1);
            }

            dos.write(new byte[11196]); // 18 Blank PMs
            dos.write(new byte[1244]); // Skip Akechi (I think this ones fake)
            
            // Kasumi's First 2 Personas
            serializePartyPersona(dos, 8, 0);
            serializePartyPersona(dos, 8, 1);

            // 3rd Evolution personas
            for (int i = 0; i < 9; i++) {
                serializePartyPersona(dos, i, 2);
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
    private static PartyMemberPersona readPartyMemberPersona(FileInputStream personaInputStream, EPartyMemberPersona partyPersona, int personaIndex) throws IOException {
       
        // Skipping Character (because you shouldnt change that), Levels Available (because it doesnt do anything), and a blank byte
        personaInputStream.skip(4);
        Skill[] skills = FileStreamUtil.readSkills(personaInputStream, 32);

        int[][] statGain = new int[98][5];
        for (int lvl = 0; lvl < 98; lvl++) {
            for (int stat = 0; stat < 5; stat++) {
                statGain[lvl][stat] = personaInputStream.read();
            }
        }

        return new PartyMemberPersona(partyPersona, skills, statGain, personaIndex);
    }


    private static void serializePartyPersona(DataOutputStream dos, int pmIndex, int personaIndex) throws IOException {
        PartyMemberPersona partyPersona = s_partyMembers[pmIndex].personas[personaIndex];
        PartyMemberPersona persona = s_partyMembers[pmIndex].personas[partyPersona.copyOfPersona];

        dos.writeShort(pmIndex + 2); // character
        dos.writeByte(99); // levels available
        dos.writeByte(0); // blank byte

        // Skills
        Skill[] skills = persona.getSkills();
        for (Skill skill : skills) {
            dos.writeByte(skill.getPendingLevels());
            dos.writeByte(skill.getLearnability().ID);
            dos.writeShort(skill.getID());
        }

        // Stat Gains
        for (int lvl = 0; lvl < 98; lvl++) {
            for (int stat = 0; stat < 5; stat++) {
                dos.writeByte(persona.statGain[lvl][stat]);
            }
        }
    }


    public static void restart() {
        s_partyMembers = null;
        start();
    }


    public static PartyMember getPartyMember(EPartyMember partyMember) {
        return s_partyMembers[partyMember.getPMIndex()];
    }


    public static Persona getPersona(EPartyMember partyMember, int personaIndex) {
        return PersonaStream.getPersona(getPartyMember(partyMember).personas[personaIndex].epartyPersona.PERSONA_INDEX);
    }


    public static void setWriteThresholds(boolean writeThresholdsIndividually) {
        s_writeThresholdsIndividually = writeThresholdsIndividually;
    }

    public static boolean getWriteThresholds() {
        return s_writeThresholdsIndividually;
    }
}
