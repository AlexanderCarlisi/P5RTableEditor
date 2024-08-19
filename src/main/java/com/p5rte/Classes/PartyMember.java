package com.p5rte.Classes;

import com.p5rte.Utils.Enums.EPartyMember;

public class PartyMember {
    
    public int[] levelThreshold;
    public EPartyMember member;
    public PartyMemberPersona[] personas = new PartyMemberPersona[3];
    

    public PartyMember(int[] levelThreshold, EPartyMember member) {
        this.levelThreshold = levelThreshold;
        this.member = member;
    }
}
