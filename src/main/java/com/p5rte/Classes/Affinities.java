package com.p5rte.Classes;

import java.util.HashMap;

import com.p5rte.Utils.Enums.AffinityIndex;

public class Affinities {
    public HashMap<AffinityIndex, AffinityElement> elements;

    public Affinities(HashMap<AffinityIndex, AffinityElement> elements) {
        this.elements = elements;
    }
}
