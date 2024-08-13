package com.p5rte.Classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.p5rte.GUI.GUIManager;
import com.p5rte.Utils.Constants;


public class PersonaTable {
    
    private static InputStream m_inputStreamPersona;
    private static Persona[] m_personas;


    public static void startPersonaStream() {
        File inputFile = new File(Constants.Path.INPUT_PERSONA_TABLE);
        File outputFile = new File(Constants.Path.OUTPUT_PERSONA_TABLE);

        try {
            // Check if output file already exists
            if (outputFile.exists()) {
                GUIManager.checkAndDeleteOutputFile(outputFile);
                return;
            }

            // Create a new output file
            if (!outputFile.createNewFile()) {
                System.err.println("Failed to create new output file.");
                return;
            }

            // Copy the data from input file to output file
            try (InputStream inputStream = new FileInputStream(inputFile);
                OutputStream outputStream = new FileOutputStream(outputFile)) {

                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
            }

            // Generate new Inputstream
            m_inputStreamPersona = new FileInputStream(outputFile);

        } catch (IOException e) {
            System.err.println("An error occurred during file operations:");
            e.printStackTrace();
        }
    }


    public static void readPersonas() {
        if (m_inputStreamPersona == null && m_personas == null) {
            startPersonaStream();

        } else if (m_personas != null) {
            return;
        }

        m_personas = new Persona[464];

        // Segment 1 of Persona Table | BitFlags, ArcanaID, Level, Stats, SkillInheritanceID
        try {
            m_inputStreamPersona.skip(0x4); // start of bitflags for first persona
        } catch (IOException e) {
            System.err.println("\n\n\nCouldn't Use Inputstream for PERSONA.TBL.\n\n\n");
            System.exit(0);
        }

        for (int p = 0; p < m_personas.length; p++) {
            boolean[] bitFlags = new boolean[10];
            int arcanaID = 0;
            int level = 0;
            int[] stats = new int[5];
            int skillInheritanceID = 0;

            try {

                // BitFlags
                byte[] bitFlagsBytes = m_inputStreamPersona.readNBytes(2);
                int shift = 1;
                int byteIndex = 0;
                for (int bf = 0; bf < 10; bf++) {
                    // Go to the Next Byte
                    if (shift < 0) {
                        shift = 7;
                        byteIndex++;
                    }
                    bitFlags[bf] = ((Byte.toUnsignedInt(bitFlagsBytes[byteIndex]) >> shift) & 1) == 1; // Set boolean to value of Bit at shift
                    shift--;
                }
                
                // Arcana ID
                arcanaID = m_inputStreamPersona.read();

                // Level
                level = m_inputStreamPersona.read();

                // Stats
                byte[] statBytes = m_inputStreamPersona.readNBytes(5);
                for (int s = 0; s < 5; s++) {
                    stats[s] = statBytes[s];
                }

                m_inputStreamPersona.skip(0x2); // Skip blank byte + Redundent skillInheritance byte

                // Skill Inheritance ID
                skillInheritanceID = m_inputStreamPersona.read();

                m_inputStreamPersona.skip(0x2); // Skip Unkown Bytes

            } catch (IOException e) {
                e.printStackTrace();
            }

            m_personas[p] = new Persona(bitFlags, arcanaID, level, stats, skillInheritanceID, Constants.personaIDtoName[p]);
        }

        // Segment 2 of Persona Table | Stat Growth Weights, Skills, and Traits
        try {
            m_inputStreamPersona.skip(0x10); // Skip 10 Empty Bytes between Segments
        } catch (IOException e) {
            System.err.println("\n\n\nCouldn't Use Inputstream for PERSONA.TBL.\n\n\n");
            System.exit(0);
        }

        for (Persona m_persona : m_personas) {
            int[] statWeights = new int[5];
            Skill[] skills = new Skill[16];
            
            try {
                // Stat Growth Weights
                byte[] statWeightsBytes = m_inputStreamPersona.readNBytes(5);
                for (int sw = 0; sw < 5; sw++) {
                    statWeights[sw] = statWeightsBytes[sw];
                }

                m_inputStreamPersona.skip(0x1); // Skip 1 Empty Byte

                // Skills
                byte[] skillBytes = m_inputStreamPersona.readNBytes(64); // 16 total skills, 4 bytes each
                for (int s = 0; s < 16; s++) {
                    int pendingLevels = skillBytes[s * 4];
                    int learnability = skillBytes[s * 4 + 1];
                    int id = ((skillBytes[s * 4 + 2] & 0xFF) << 8) | (skillBytes[s * 4 + 3] & 0xFF);
                    skills[s] = new Skill(pendingLevels, learnability, id);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            // Update Persona
            m_persona.setStatWeights(statWeights);
            m_persona.setSkills(skills);
        }

        // Close Stream
        try {
            m_inputStreamPersona.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void clearPersonas() {
        m_personas = null;
    }


    public static Persona getPersona(int index) {
        if (m_personas == null) {
            readPersonas();
        }
        return m_personas[index];
    }

    public static Persona[] getPersonas() {
        if (m_personas == null) {
            readPersonas();
        }
        return m_personas;
    }
}