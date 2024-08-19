package com.p5rte.Classes;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;

import com.p5rte.Utils.Constants;
import com.p5rte.Utils.Enums.AffinityDataIndex;
import com.p5rte.Utils.Enums.AffinityIndex;
import com.p5rte.Utils.FileStreamUtil;


public class PersonaStream {
    
    private static Persona[] m_personas;


    public static void start() {
        readPersonas();
    }


    private static void readPersonas() {
        m_personas = new Persona[464];

        // Segment 1 of Persona Table | BitFlags, ArcanaID, Level, Stats, SkillInheritanceID
        try (FileInputStream personaInputStream = new FileInputStream(Constants.Path.INPUT_PERSONA_TABLE);
         FileInputStream unitInputStream = new FileInputStream(Constants.Path.INPUT_UNIT_TABLE);) {
            
            personaInputStream.skip(0x4); // start of bitflags for first persona

            for (int p = 0; p < m_personas.length; p++) {
                boolean[] bitFlags = new boolean[10];
                int arcanaID;
                int level;
                int[] stats = new int[5];
                int skillInheritanceID;

                // BitFlags
                byte[] bitFlagsBytes = personaInputStream.readNBytes(2);
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
                arcanaID = personaInputStream.read();

                // Level
                level = personaInputStream.read();

                // Stats
                byte[] statBytes = personaInputStream.readNBytes(5);
                for (int s = 0; s < 5; s++) {
                    stats[s] = statBytes[s];
                }

                personaInputStream.skip(0x2); // Skip blank byte + Redundent skillInheritance byte

                // Skill Inheritance ID
                skillInheritanceID = personaInputStream.read();
                personaInputStream.skip(0x2); // Skip Unkown Bytes

                m_personas[p] = new Persona(bitFlags, arcanaID, level, stats, skillInheritanceID, Constants.personaIDtoName[p]);
            }

            // Segment 2 of Persona Table | Stat Growth Weights, Skills, and Traits
            personaInputStream.skip(0x10); // Skip 10 Empty Bytes between Segments, it should be 16, but its 10.

            for (Persona m_persona : m_personas) {
                int[] statWeights = new int[5];
                Skill[] skills;
            
                // Stat Growth Weights
                byte[] statWeightsBytes = personaInputStream.readNBytes(5);
                for (int sw = 0; sw < 5; sw++) {
                    statWeights[sw] = statWeightsBytes[sw];
                }

                personaInputStream.skip(0x1); // Skip 1 Empty Byte

                // Skills
                skills = FileStreamUtil.readSkills(personaInputStream, 16);

                // Update Persona
                m_persona.setStatWeights(statWeights);
                m_persona.setSkills(skills);
            }

            // Segment 3 of Unit Table | Affinities
            unitInputStream.skip(84580); // Skip 84580 Bytes to reach beginning of affinity data
        
            for (Persona m_persona : m_personas) {
                HashMap<AffinityIndex, AffinityElement> elements = new HashMap<>();
                for (AffinityIndex ai : AffinityIndex.values()) {
                    elements.put(ai, readAffinityElement(unitInputStream));
                }
                m_persona.setAffinities(elements);
            }

            // Close Streams
            personaInputStream.close();
            unitInputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static AffinityElement readAffinityElement(FileInputStream unitInputStream) throws IOException{
        HashMap<AffinityDataIndex, Boolean> data = new HashMap<>();
        byte[] bytes = unitInputStream.readNBytes(2);
        for (int shift = 0; shift < 8; shift++) {
            // Shift through the bits and set boolean values
            data.put(AffinityDataIndex.values()[7 - shift], (bytes[0] >> shift & 1) == 1);
        }
        return new AffinityElement(bytes[1], data);
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


    public static void restart() {
        m_personas = null;
        start();
    }
}