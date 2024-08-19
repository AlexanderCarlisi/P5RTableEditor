package com.p5rte.Classes;

import com.p5rte.Utils.Enums.EPartyMemberPersona;

public class PartyMemberPersona {
    public EPartyMemberPersona epartyPersona;
    public Persona registryPersona;
    public Skill[] partySkills = new Skill[32];
    public int[][] statGain = new int[98][5];
}
