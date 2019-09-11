package com.muabe.sample.menu.combine;

import com.muabe.propose.combination.CombinationBridge;

public class TestCombination extends CombinationBridge<TestCombination> {
    float value;

    public TestCombination(){
        value = 0;

    }
    public TestCombination(float value){
        setValue(value);
    }


    public TestCombination(String name, float value){
        setName(name);
        setValue(value);
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public float compare(Object param) {
        return value;
    }

    public void setValue(float value){
        this.value = value;
    }

    public TestCombination getParents(){
        return (TestCombination)parents;
    }

    public float getValue(){
        return this.value;
    }
}
