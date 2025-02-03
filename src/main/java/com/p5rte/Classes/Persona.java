package com.p5rte.Classes;

import java.util.HashMap;

import com.p5rte.Utils.Enums;
import com.p5rte.Utils.Enums.AffinityDataIndex;
import com.p5rte.Utils.Enums.AffinityIndex;
import com.p5rte.Utils.Enums.SkillLearnability;

public class Persona {
    
    private final String NAME;
    private final boolean[] BITFLAGS;
    private final int[] STATS;

    private Enums.Arcana _arcana;
    private int _arcanaID;
    private int _level;
    private int _skillInheritanceID;
    private Enums.SkillInheritance _skillInheritance;
    private int[] _statWeights;
    private Skill[] _skills;
    private HashMap<AffinityIndex, Affinity> _affinities;
    

    public Persona(boolean[] bitFlags, int arcanaID, int level, int[] stats, int skillInheritanceID, String name) {
        BITFLAGS = bitFlags;
        _arcanaID = arcanaID;
        _arcana = Enums.Arcana.getArcana(_arcanaID);
        _level = level;
        STATS = stats;
        _skillInheritanceID = skillInheritanceID;
        _skillInheritance = Enums.SkillInheritance.getSkillInheritance(_skillInheritanceID);
        NAME = name;
    }

    public boolean[] getBitFlags() {
        return BITFLAGS;
    }

    public Enums.Arcana getArcana() {
        return _arcana;
    }

    public int getArcanaID() {
        return _arcanaID;
    }

    public int getLevel() {
        return _level;
    }

    public int[] getStats() {
        return STATS;
    }

    public String getName() {
        return NAME;
    }
    
    public Enums.SkillInheritance getSkillInheritance() {
        return _skillInheritance;
    }

    public int getSkillInheritanceID() {
        return _skillInheritanceID;
    }

    public int[] getStatWeights() {
        return _statWeights;
    }

    public Skill[] getSkills() {
        return _skills;
    }

    public Affinity getAffinity(AffinityIndex index) {
        return _affinities.get(index);
    }

    public HashMap<AffinityIndex, Affinity> getAffinities() {
        return _affinities;
    }

    public void setBitFlag(int flag, boolean value) {
        BITFLAGS[flag] = value;
    }

    public void setStat(int stat, int value) {
        STATS[stat] = value;
    }

    public void setLevel(int lvl) {
        _level = lvl;
    }

    public void setStatWeights(int[] weights) {
        _statWeights = weights;
    }

    public void setSkills(Skill[] skills) {
        _skills = skills;
    }

    public void setSkill(int index, int id, SkillLearnability learnability, int pendingLevels) {
        _skills[index].setID(id);
        _skills[index].setLearnability(learnability);
        _skills[index].setPendingLevels(pendingLevels);
    }
    public void setSkillPendingLevel(int index, int pendingLevels) {
        _skills[index].setPendingLevels(pendingLevels);
    }
    public void setSkillLearnability(int index, SkillLearnability learnability) {
        _skills[index].setLearnability(learnability);
    }
    public void setSkillID(int index, int id) {
        _skills[index].setID(id);
    }

    public void setAffinities(HashMap<AffinityIndex, Affinity> affinities) {
        _affinities = affinities;
    }

    public void setSkillInheritance(Enums.SkillInheritance inheritance) {
        _skillInheritance = inheritance;
        _skillInheritanceID = inheritance.ID;
    }

    public void setArcana(Enums.Arcana arcana) {
        _arcana = arcana;
        _arcanaID = arcana.ID;
    }

    public void setStatWeight(int index, int weight) {
        _statWeights[index] = weight;
    }

    public void setAffinityData(AffinityIndex ai, AffinityDataIndex adi, boolean value) {
        _affinities.get(ai).data.put(adi, value);
    }

    public void setAffinityMultiplier(AffinityIndex ai, int multiplier) {
        _affinities.get(ai).multiplier = multiplier;
    }
}