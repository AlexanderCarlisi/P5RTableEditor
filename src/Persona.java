public class Persona {
    
    private boolean[] m_bitFlags;

    private Enums.Arcana m_arcana;
    private int m_arcanaID;

    private int m_level;
    private int[] m_stats;

    private int m_skillInheritanceID;
    private Enums.SkillInheritance m_skillInheritance;
    

    public Persona(boolean[] bitFlags, int arcanaID, int level, int[] stats, int skillInheritanceID) {
        m_bitFlags = bitFlags;
        m_arcanaID = arcanaID;
        m_arcana = Enums.Arcana.getArcana(m_arcanaID);
        m_level = level;
        m_stats = stats;
        m_skillInheritanceID = skillInheritanceID;
        m_skillInheritance = Enums.SkillInheritance.getSkillInheritance(m_skillInheritanceID);
    }

    public boolean[] getBitFlags() {
        return m_bitFlags;
    }

    public Enums.Arcana getArcana() {
        return m_arcana;
    }

    public int getArcanaID() {
        return m_arcanaID;
    }

    public int getLevel() {
        return m_level;
    }

    public int[] getStats() {
        return m_stats;
    }

    public Enums.SkillInheritance getSkillInheritance() {
        return m_skillInheritance;
    }

    public int getSkillInheritanceID() {
        return m_skillInheritanceID;
    }
}
