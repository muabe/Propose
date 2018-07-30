package com.muabe.propose.combine;

public class TestCombination extends Combination {
    public String name;
    public int priority;

    public TestCombination(String name, int priority){
        this.name = name;
        this.priority = priority;
    }

    @Override
    public int priority() {
        return priority;
    }
}
