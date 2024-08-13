package com.p5rte.Classes;

import com.p5rte.Utils.Enums.SkillLearnability;

public class Skill {
    private int m_pendingLevels;
    private SkillLearnability m_learnability;
    private int m_id;

    public Skill(int pendingLevels, int learnability, int id) {
        m_id = id;
        m_pendingLevels = pendingLevels;
        m_learnability = SkillLearnability.getSkillLearnability(learnability);
    }

    public int getPendingLevels() {
        return m_pendingLevels;
    }

    public SkillLearnability getLearnability() {
        return m_learnability;
    }

    public int getID() {
        return m_id;
    }
}
