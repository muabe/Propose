package com.muabe.propose.combine;

import java.util.ArrayList;

public class TestCombination extends Combination {
    public int priority;

    public TestCombination(String name, int priority){
        this.name = name;
        this.priority = priority;
    }

    @Override
    public float priority() {
        return priority;
    }

    public void end(ArrayList<Combination> list){
        int index = list.indexOf(this);
    }
}
