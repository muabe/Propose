package com.muabe.sample.menu.combine;

import com.muabe.combination.Combination;
import com.muabe.combination.Combine;

public class TestCombination extends Combination {
    float value;

    public TestCombination(){
        value = 0;

    }

    public TestCombination(String name, float value){
        this.name = name;
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

    public String getName(){
        if(name == null){
            if(mode == Combine.AND){
                name = "AND";
            }else if(mode == Combine.OR){
                name = "OR";
            }

        }
        return this.name;
    }

    public float getValue(){
        return this.value;
    }
}
