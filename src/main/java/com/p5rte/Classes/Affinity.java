package com.p5rte.Classes;

import java.util.HashMap;

import com.p5rte.Utils.Enums.AffinityDataIndex;

public class Affinity {
    public int multiplier;
    public HashMap<AffinityDataIndex, Boolean> data;

    public Affinity(int multiplier, HashMap<AffinityDataIndex, Boolean> data) {
        this.multiplier = multiplier;
        this.data = data;
    }
}