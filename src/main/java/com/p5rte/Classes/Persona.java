package com.p5rte.Classes;

import java.util.HashMap;

import com.p5rte.Utils.Enums;
import com.p5rte.Utils.Enums.AffinityIndex;
import com.p5rte.Utils.Enums.SkillLearnability;

public class Persona {
    
    private final boolean[] m_bitFlags;

    private Enums.Arcana m_arcana;
    private int m_arcanaID;

    private int m_level;
    private final int[] m_stats;

    private int m_skillInheritanceID;
    private Enums.SkillInheritance m_skillInheritance;

    private final String m_personaName;

    private int[] m_statWeights;
    private Skill[] m_skills;

    private HashMap<AffinityIndex, AffinityElement> m_affinities;
    

    public Persona(boolean[] bitFlags, int arcanaID, int level, int[] stats, int skillInheritanceID, String name) {
        m_bitFlags = bitFlags;
        m_arcanaID = arcanaID;
        m_arcana = Enums.Arcana.getArcana(m_arcanaID);
        m_level = level;
        m_stats = stats;
        m_skillInheritanceID = skillInheritanceID;
        m_skillInheritance = Enums.SkillInheritance.getSkillInheritance(m_skillInheritanceID);
        m_personaName = name;
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

    public String getName() {
        return m_personaName;
    }
    
    public Enums.SkillInheritance getSkillInheritance() {
        return m_skillInheritance;
    }

    public int getSkillInheritanceID() {
        return m_skillInheritanceID;
    }

    public int[] getStatWeights() {
        return m_statWeights;
    }

    public Skill[] getSkills() {
        return m_skills;
    }

    public AffinityElement getAffinity(AffinityIndex index) {
        return m_affinities.get(index);
    }

    public HashMap<AffinityIndex, AffinityElement> getAffinities() {
        return m_affinities;
    }

    public void setBitFlag(int flag, boolean value) {
        m_bitFlags[flag] = value;
    }

    public void setStat(int stat, int value) {
        m_stats[stat] = value;
    }

    public void setLevel(int lvl) {
        m_level = lvl;
    }

    public void setStatWeights(int[] weights) {
        m_statWeights = weights;
    }

    public void setSkills(Skill[] skills) {
        m_skills = skills;
    }

    public void setSkill(int index, int id, SkillLearnability learnability, int pendingLevels) {
        m_skills[index].setID(id);
        m_skills[index].setLearnability(learnability);
        m_skills[index].setPendingLevels(pendingLevels);
    }

    public void setAffinities(HashMap<AffinityIndex, AffinityElement> affinities) {
        m_affinities = affinities;
    }

    public void setSkillInheritance(Enums.SkillInheritance inheritance) {
        m_skillInheritance = inheritance;
        m_skillInheritanceID = inheritance.ID;
    }

    public void setArcana(Enums.Arcana arcana) {
        m_arcana = arcana;
        m_arcanaID = arcana.ID;
    }

    public void setStatWeight(int index, int weight) {
        m_statWeights[index] = weight;
    }
}