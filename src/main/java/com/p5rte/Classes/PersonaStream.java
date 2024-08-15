package com.p5rte.Classes;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.p5rte.GUI.GUIManager;
import com.p5rte.Utils.Constants;
import com.p5rte.Utils.Enums.AffinityDataIndex;
import com.p5rte.Utils.Enums.AffinityIndex;


public class PersonaStream {
    
    private static InputStream m_inputStreamPersona;
    private static InputStream m_inputStreamUnit;
    private static Persona[] m_personas;


    public static void start() {

        do { 
            if (m_inputStreamPersona != null) break;
            m_inputStreamPersona = startInputStream(Constants.Path.INPUT_PERSONA_TABLE, Constants.Path.OUTPUT_PERSONA_TABLE);    
        } while (m_inputStreamPersona == null);

        do { 
            if (m_inputStreamUnit != null) break;
            m_inputStreamUnit = startInputStream(Constants.Path.INPUT_UNIT_TABLE, Constants.Path.OUTPUT_UNIT_TABLE);
        } while (m_inputStreamUnit == null);

        readPersonas();
    }


    private static InputStream startInputStream(String inputPath, String outputPath) {
        File inputFile = new File(inputPath);
        File outputFile = new File(outputPath);

        try {

            if (!inputFile.exists()) {
                System.err.println("Input File not found: " + inputFile.getAbsolutePath());
                GUIManager.DisplayWarning(
                    "Input File Error", 
                    "Input File not found", 
                    "The input file could not be found. Please make sure the file is in the correct location and try again. \n\n" + inputFile.getAbsolutePath());
                return null;
            }

            // Check if the output file already exists
            if (outputFile.exists()) GUIManager.checkOutputFile(inputFile, outputFile);
            else outputFile.createNewFile();
            
            // Copy the data from input file to output file
            copyTo(inputFile, outputFile);

            // Generate new Inputstream
            return new FileInputStream(outputFile);

        } catch (IOException e) {
            System.err.println("An error occurred during file operations:");
            e.printStackTrace();
            return null;
        }
    }

    public static void copyTo(File toCopy, File toPaste) {
        try (InputStream inputStream = new FileInputStream(toCopy);
            OutputStream outputStream = new FileOutputStream(toPaste)) {

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        } catch(IOException e) {
            System.err.println("An error occurred during file operations: " + e.getMessage());
        }
    }


    public static void readPersonas() {
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
     * @return byte[][] {Persona Table Segment1 [0], Persona Table Segment2 [1], Unit Table Segement3 [2]}
     */
    private static byte[][] serializePersona(Persona persona) {

        // Create a 2D byte array to hold the serialized data
        byte[][] tables = new byte[3][];

        // Serialize Persona Table Data Segement 1
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
         DataOutputStream dos = new DataOutputStream(baos)) {

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
    
            // Convert to byte array
            tables[0] = baos.toByteArray();
    
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


        // Serialize Persona Table Data Segement 2
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
         DataOutputStream dos = new DataOutputStream(baos)) {

            // Serialize statWeights
            int[] statWeights = persona.getStatWeights();
            for (int statWeight : statWeights) {
                dos.writeByte(statWeight);
            }
    
            // Add Blank Byte
            dos.write(new byte[1]);
    
            // Serialize skills
            Skill[] skills = persona.getSkills();
            for (int i = 0; i < 16; i++) {
                dos.writeByte(skills[i].getPendingLevels());
                dos.writeByte(skills[i].getLearnability().ID);
                dos.writeShort(skills[i].getID());
            }
    
            // Convert to byte array
            tables[1] = baos.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


        // Serialize Unit Table Data Segement 3
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
         DataOutputStream dos = new DataOutputStream(baos)) {

            // Serialize Affinities
            HashMap<AffinityIndex, AffinityElement> affinities = persona.getAffinities();
            for (AffinityIndex ai : AffinityIndex.values()) {
                AffinityElement element = affinities.get(ai);
                dos.writeByte(element.multiplier);
                for (int i = 0; i < element.data.size(); i++) {
                    int booleanByte = 0;
                    if (element.data.get(AffinityDataIndex.values()[i])) {
                        booleanByte |= (1 << (9 - i));
                    }        
                    dos.writeByte(booleanByte);
                }
            }
    
            // Convert to byte array
            tables[2] = baos.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return tables;
    }


    public static void writeToTables() {
        if (m_personas == null) {
            readPersonas();
        }

        try (RandomAccessFile rafPersona = new RandomAccessFile(Constants.Path.OUTPUT_PERSONA_TABLE, "rw");
         FileOutputStream fosUnit = new FileOutputStream(Constants.Path.OUTPUT_UNIT_TABLE);) {
        
            // Store Segment Data for Tables to be used later.
            List<byte[]> pTableSegment1 = new ArrayList<>();
            List<byte[]> pTableSegment2 = new ArrayList<>();
            // List<byte[]> uTableSegment3 = new ArrayList<>();
            
            // unitChannel.position(84580);

            // Serialize Persona Objects.
            for (Persona persona : m_personas) {
                byte[][] tables = serializePersona(persona);
                pTableSegment1.add(tables[0]);
                pTableSegment2.add(tables[1]);
                // uTableSegment3.add(tables[2]);
            }
            
            // Write Persona Table Segment 1
            int personaPos = 4;
            for (byte[] data : pTableSegment1) {
                // personaChannel.write(ByteBuffer.wrap(data));
                rafPersona.seek(personaPos);
                rafPersona.write(data);
                personaPos += data.length;
            }

            // Write Persona Table Segment 2
            personaPos += 16; // SO its the right number here, but a different number in ReadPersonas.
            for (byte[] skillstat : pTableSegment2) {
                // personaChannel.write(ByteBuffer.wrap(skillstat));
                rafPersona.seek(personaPos);
                rafPersona.write(skillstat);
                personaPos += skillstat.length;
            }

            // // Write Unit Table Segment 3 
            // for (byte[] affinityData : uTableSegment3) {
            //     unitChannel.write(ByteBuffer.wrap(affinityData));
            // }

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
}