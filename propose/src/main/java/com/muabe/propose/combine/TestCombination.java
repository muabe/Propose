package com.muabe.propose.combine;

import java.util.ArrayList;

public class TestCombination extends Combination {
    public int compare;
    public int priority = 0;

    public TestCombination(){
        this.name = "";
        this.compare = 0;
    }

    public TestCombination(String name, int compare){
        this.name = name;
        this.compare = compare;
    }



    public void end(ArrayList<Combination> list){
        int index = list.indexOf(this);
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public float compare() {
        return compare;
    }

}
