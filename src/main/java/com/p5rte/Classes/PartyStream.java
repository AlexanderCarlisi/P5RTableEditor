package com.p5rte.Classes;

import java.io.InputStream;

import com.p5rte.Utils.Constants;
import com.p5rte.Utils.FileStreamUtil;

public class PartyStream {
    
    private static InputStream m_personaInputStream;
    private static PartyMember[] m_partyMembers;

    public static void start() {
        do { 
            m_personaInputStream = FileStreamUtil.startInputStream(Constants.Path.INPUT_PERSONA_TABLE, Constants.Path.OUTPUT_PERSONA_TABLE);
        } while (m_personaInputStream == null);

        readPartyMembers(false);
    }

    /**
     * Reads the party members from the input file
     * @param readIndividualLevelThresholds : If false, the level threshold is read only from the first pm, and set to the others.
     */
    private static void readPartyMembers(boolean readIndividualLevelThresholds) {

        // m_partyMembers = new PartyMember[]

        // try {


            
        // } catch (IOException e) {

        // }
    }
}
