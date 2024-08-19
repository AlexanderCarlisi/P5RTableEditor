package com.p5rte.Classes;

import java.io.FileInputStream;
import java.io.IOException;

import com.p5rte.Utils.Constants;
import com.p5rte.Utils.Enums.EPartyMember;
import com.p5rte.Utils.Enums.EPartyMemberPersona;
import com.p5rte.Utils.FileStreamUtil;

public class PartyStream {
    
    private static PartyMember[] m_partyMembers;


    public static void start() {
        readPartyMembers(false);
    }


    /**
     * Reads the party members from the input file
     * @param readIndividualLevelThresholds : If false, the level threshold is read only from the first pm, and set to the others.
     */
    private static void readPartyMembers(boolean readIndividualLevelThresholds) {

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
                m_partyMembers[pm].personas[1] = readPartyMemberPersona(personaInputStream, EPartyMemberPersona.values()[personaIndex]);
                personaIndex++;
            }

            personaInputStream.skip(11196); // 18 Blank PMs
            personaInputStream.skip(1244); // Skip Akechi (I think this ones fake)

            // Kasumi's First 2 Personas
            m_partyMembers[EPartyMember.Kasumi.getPMIndex()].personas[0] = readPartyMemberPersona(personaInputStream, EPartyMemberPersona.Cendrillon);
            m_partyMembers[EPartyMember.Kasumi.getPMIndex()].personas[1] = readPartyMemberPersona(personaInputStream, EPartyMemberPersona.Vanadis);
            personaIndex += 2;

            // 3rd Evolution personas
            for (int pm = 0; pm < m_partyMembers.length; pm++) {
                m_partyMembers[pm].personas[2] = readPartyMemberPersona(personaInputStream, EPartyMemberPersona.values()[personaIndex]);
                personaIndex++;
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Reads the next PartyMember Persona from the input stream.
     * @return
     */
    private static PartyMemberPersona readPartyMemberPersona(FileInputStream personaInputStream, EPartyMemberPersona partyPersona) throws IOException {
       
        // Skipping Character (because you shouldnt change that), Levels Available (because it doesnt do anything), and a blank byte
        personaInputStream.skip(4);

        Skill[] skills = FileStreamUtil.readSkills(personaInputStream, 32);
        int[][] statGain = new int[98][5];
        
        for (int lvl = 0; lvl < 98; lvl++) {
            for (int stat = 0; stat < 5; stat++) {
                statGain[lvl][stat] = personaInputStream.read();
            }
        }

        return new PartyMemberPersona(partyPersona, skills, statGain);
    }


    public static void restart() {
        m_partyMembers = null;
        start();
    }
}
