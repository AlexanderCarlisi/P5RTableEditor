package com.p5rte.Classes;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;

import com.p5rte.Utils.Constants;
import com.p5rte.Utils.Enums.AffinityDataIndex;
import com.p5rte.Utils.Enums.AffinityIndex;
import com.p5rte.Utils.FileStreamUtil;


public class PersonaStream {
    
    private static InputStream m_inputStreamPersona;
    private static InputStream m_inputStreamUnit;
    private static Persona[] m_personas;


    public static void start() {

        do { 
            if (m_inputStreamPersona != null) break;
            m_inputStreamPersona = FileStreamUtil.startInputStream(Constants.Path.INPUT_PERSONA_TABLE, Constants.Path.OUTPUT_PERSONA_TABLE);    
        } while (m_inputStreamPersona == null);

        do { 
            if (m_inputStreamUnit != null) break;
            m_inputStreamUnit = FileStreamUtil.startInputStream(Constants.Path.INPUT_UNIT_TABLE, Constants.Path.OUTPUT_UNIT_TABLE);
        } while (m_inputStreamUnit == null);

        readPersonas();
    }


    private static void readPersonas() {
        if (m_inputStreamPersona == null || m_inputStreamUnit == null) {
            start();
            return;
        }
        if (m_personas != null) return;

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
            // I honestly don't know why it's 10 here, it makes more sense to be 16. Its 16 in the write method.
        } catch (IOException e) {
            System.err.println("\n\n\nCouldn't Use Inputstream for PERSONA.TBL.\n\n\n");
            System.exit(0);
        }

        for (Persona m_persona : m_personas) {
            int[] statWeights = new int[5];
            Skill[] skills = null;
            
            try {
                // Stat Growth Weights
                byte[] statWeightsBytes = m_inputStreamPersona.readNBytes(5);
                for (int sw = 0; sw < 5; sw++) {
                    statWeights[sw] = statWeightsBytes[sw];
                }

                m_inputStreamPersona.skip(0x1); // Skip 1 Empty Byte

                // Skills
                // byte[] skillBytes = m_inputStreamPersona.readNBytes(64); // 16 total skills, 4 bytes each
                // for (int s = 0; s < 16; s++) {
                //     int pendingLevels = skillBytes[s * 4];
                //     int learnability = skillBytes[s * 4 + 1];
                //     int id = ((skillBytes[s * 4 + 2] & 0xFF) << 8) | (skillBytes[s * 4 + 3] & 0xFF);
                //     skills[s] = new Skill(pendingLevels, learnability, id);
                // }
                
                skills = FileStreamUtil.readSkills(m_inputStreamPersona, 16);

            } catch (IOException e) {
                e.printStackTrace();
            }

            // Update Persona
            m_persona.setStatWeights(statWeights);
            m_persona.setSkills(skills);
        }

        // Segment 3 of Unit Table | Affinities
        try {
            m_inputStreamUnit.skip(84580); // Skip 84580 Bytes to reach beginning of affinity data
        } catch (IOException e) {
            System.err.println("\n\n\nCouldn't Use Inputstream for UNIT.TBL.\n\n\n");
            System.exit(0);
        }

        for (Persona m_persona : m_personas) {
            HashMap<AffinityIndex, AffinityElement> elements = new HashMap<>();
            for (AffinityIndex ai : AffinityIndex.values()) {
                elements.put(ai, readAffinityElement());
            }
            m_persona.setAffinities(elements);
        }

        // Close Stream
        try {
            m_inputStreamPersona.close();
            m_inputStreamUnit.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static AffinityElement readAffinityElement() {
        HashMap<AffinityDataIndex, Boolean> data = new HashMap<>();
        try {
            byte[] bytes = m_inputStreamUnit.readNBytes(2);
            for (int shift = 0; shift < 8; shift++) {
                // Shift through the bits and set boolean values
                data.put(AffinityDataIndex.values()[7 - shift], (bytes[0] >> shift & 1) == 1);
            }
            return new AffinityElement(bytes[1], data);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Serialize a Persona object into Multiple Byte Arrays
     * @param persona
     * @return byte[][] {Persona Table Segment Data [0], Unit Table Segment Data [1]}
     */
    private static byte[][] serializePersona() {

        // Create a 2D byte array to hold the serialized data
        byte[][] tables = new byte[2][];

        // Serialize Persona Table Data Segement 1
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
         DataOutputStream dos = new DataOutputStream(baos)) {

            // Segment 1 Data Serialization
            for (Persona persona : m_personas) {
                // Serialize bitFlags into two bytes
                boolean[] bitFlags = persona.getBitFlags();
                int firstByte = 0;
                int secondByte = 0;

                // First byte (6 bits padding, 2 bits for bitFlags)
                for (int i = 0; i < 2; i++) {
                    if (bitFlags[i]) {
                        firstByte |= (1 << (1 - i)); // Set bits in the first byte
                    }
                }

                // Second byte (remaining 8 bits for bitFlags)
                for (int i = 2; i < 10; i++) {
                    if (bitFlags[i]) {
                        secondByte |= (1 << (9 - i)); // Set bits in the second byte
                    }
                }

                // Write the two bytes
                dos.writeByte(firstByte);
                dos.writeByte(secondByte);
        
                // Serialize other fields like arcanaID, level, etc.
                dos.writeByte(persona.getArcanaID());
                dos.writeByte(persona.getLevel());
                
                // Serialize stats
                int[] stats = persona.getStats();
                for (int stat : stats) {
                    dos.writeByte(stat);
                }

                // Blank byte
                dos.write(new byte[1]);
                
                // Serialize skillInheritanceID
                dos.writeShort(persona.getSkillInheritanceID());
        
                // Add 2 blank bytes
                dos.write(new byte[2]);
            }

            // Serialize Persona Table Data Segement 2
            dos.write(new byte[12]); // 16 Blank Bytes between Segments
            dos.writeInt(32480); // Write Segment2 Size

            for (Persona persona : m_personas) {
                // Serialize statWeights
                int[] statWeights = persona.getStatWeights();
                for (int statWeight : statWeights) {
                    dos.writeByte(statWeight);
                }
        
                // Add Blank Byte
                dos.write(new byte[1]);
        
                // Serialize skills
                for (Skill skill : persona.getSkills()) {
                    dos.writeByte(skill.getPendingLevels());
                    dos.writeByte(skill.getLearnability().ID);
                    dos.writeShort(skill.getID());
                }
            }

            // Convert to byte array
            tables[0] = baos.toByteArray();
            baos.reset();

            // Serialize Unit Table Data Segement 3

            for (Persona persona : m_personas) {
                // Serialize Affinities
                HashMap<AffinityIndex, AffinityElement> affinities = persona.getAffinities();
                for (AffinityIndex ai : AffinityIndex.values()) {
                    AffinityElement element = affinities.get(ai);
                    int boolByte = 0;
                    for (int i = 0; i < 8; i++) {
                        if (element.data.get(AffinityDataIndex.values()[7 - i])) {
                            boolByte |= (1 << i);
                        }
                    }
                    dos.writeByte(boolByte);
                    dos.writeByte(element.multiplier);
                }
            }

            // Convert to byte array
            tables[1] = baos.toByteArray();
            return tables;
    
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void writeToTables() {
        if (m_personas == null) {
            readPersonas();
        }

        try (RandomAccessFile rafPersona = new RandomAccessFile(Constants.Path.OUTPUT_PERSONA_TABLE, "rw");
         RandomAccessFile rafUnit = new RandomAccessFile(Constants.Path.OUTPUT_UNIT_TABLE, "rw");) {

            byte[][] segments = serializePersona();

            rafPersona.seek(4);
            rafPersona.write(segments[0]);

            rafUnit.seek(84580);
            rafUnit.write(segments[1]);

            rafPersona.close();
            rafUnit.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static Persona getPersona(int index) {
        if (m_personas == null) {
            readPersonas();
        }
        return m_personas[index];
    }


    public static void resetFiles() {
        m_personas = null;
        m_inputStreamPersona = null;
        m_inputStreamUnit = null;
        start();
    }


    public static void resetToOriginals() {
        m_personas = null;
        m_inputStreamPersona = null;
        m_inputStreamUnit = null;

        try {
            Files.copy(Paths.get(Constants.Path.ORIGINAL_PERSONA_TABLE), Paths.get(Constants.Path.INPUT_PERSONA_TABLE), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(Paths.get(Constants.Path.ORIGINAL_UNIT_TABLE), Paths.get(Constants.Path.INPUT_UNIT_TABLE), StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            e.printStackTrace();
        }

        start();
    }
}