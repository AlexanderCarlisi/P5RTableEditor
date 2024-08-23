package com.p5rte.Classes;

import com.p5rte.Utils.Enums.EPartyMember;

public class PartyMember {
    
    public int[] levelThreshold;
    public EPartyMember member;
    public PartyMemberPersona[] personas = new PartyMemberPersona[3];
    public boolean readIndividualThreshold = false;
    

    public PartyMember(EPartyMember member) {
        this.member = member;
    }
}
