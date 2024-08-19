package com.p5rte.Classes;

import com.p5rte.Utils.Enums.EPartyMemberPersona;

public class PartyMemberPersona {
    public EPartyMemberPersona epartyPersona;
    public Skill[] partySkills;
    public int[][] statGain;

    public PartyMemberPersona(EPartyMemberPersona ePartyMemberPersona, Skill[] partySkills, int[][] statGain) {
        this.epartyPersona = ePartyMemberPersona;
        this.partySkills = partySkills;
        this.statGain = statGain;
    }
}
