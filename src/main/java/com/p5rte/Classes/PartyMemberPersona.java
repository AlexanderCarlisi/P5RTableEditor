package com.p5rte.Classes;

import com.p5rte.Utils.Enums.EPartyMemberPersona;

public class PartyMemberPersona extends Persona {
    public EPartyMemberPersona epartyPersona;
    public int[][] statGain;
    public int copyOfPersona;

    public PartyMemberPersona(EPartyMemberPersona ePartyMemberPersona, Skill[] partySkills, int[][] statGain) {
        // The way i did inheritance with persona is really weird here, but its for the Skill Tab
        super(new boolean[10], 0, 0, new int[5], 0, "");
        super.setSkills(partySkills);

        this.epartyPersona = ePartyMemberPersona;
        this.statGain = statGain;
    }
}
