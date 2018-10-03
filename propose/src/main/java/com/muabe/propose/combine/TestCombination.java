package com.muabe.propose.combine;

import java.util.ArrayList;

public class TestCombination extends Combination {
    public int priority;

    public TestCombination(String name, int priority){
        this.name = name;
        this.priority = priority;
    }



    public void end(ArrayList<Combination> list){
        int index = list.indexOf(this);
    }

    @Override
    public float priority(Object o) {
        return priority;
    }

}
