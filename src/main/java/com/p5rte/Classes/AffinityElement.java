package com.p5rte.Classes;

import java.util.HashMap;

import com.p5rte.Utils.Enums.AffinityDataIndex;

public class AffinityElement {
    public int multiplier;
    public HashMap<AffinityDataIndex, Boolean> data;

    public AffinityElement(int multiplier, HashMap<AffinityDataIndex, Boolean> data) {
        this.multiplier = multiplier;
        this.data = data;
    }
}