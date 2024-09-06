package com.p5rte.Classes;

import com.p5rte.Utils.Enums.ESkill;
import com.p5rte.Utils.Enums.SkillLearnability;

public class Skill {
    private int _pendingLevels;
    private SkillLearnability _learnability;
    private int _id;

    public Skill(int pendingLevels, int learnability, int id) {
        _id = id;
        _pendingLevels = pendingLevels;
        _learnability = SkillLearnability.getSkillLearnability(learnability);
    }

    public int getPendingLevels() {
        return _pendingLevels;
    }

    public SkillLearnability getLearnability() {
        return _learnability;
    }

    public int getID() {
        return _id;
    }

    public ESkill getESkill() {
        return ESkill.getAt(_id);
    }

    public void setPendingLevels(int pendingLevels) {
        _pendingLevels = pendingLevels;
    }

    public void setLearnability(SkillLearnability learnability) {
        _learnability = learnability;
    }

    public void setID(int id) {
        _id = id;
    }
}