import java.io.*;

public class Table {
    
    private static InputStream m_inputStreamPersona;
    private static Persona[] m_personas;


    public static void startPersonaStream() {
        try {
            m_inputStreamPersona = new FileInputStream(Constants.Path.kTablePersona);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void readPersonas() {
        if (m_inputStreamPersona == null) {
            startPersonaStream();
        }

        if (m_personas != null) {
            return;
        }

        m_personas = new Persona[464];

        try {
            m_inputStreamPersona.skip(0x4); // start of bitflags for first persona
        } catch (IOException e) {
            e.printStackTrace();
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
                    if (shift < 0) {
                        shift = 7;
                        byteIndex++;
                    }
                    bitFlags[bf] = ((Byte.toUnsignedInt(bitFlagsBytes[byteIndex]) >> shift) & 1) == 1;
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
            m_personas[p] = new Persona(bitFlags, arcanaID, level, stats, skillInheritanceID);
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
