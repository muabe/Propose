package com.muabe.sample.menu.cache;

import com.muabe.propose.combination.CombinationBridge;
import com.muabe.propose.combination.Combine;

class CacheCombination extends CombinationBridge<CacheCombination> {
    float value;

    public CacheCombination(){
        value = 0;

    }
    public CacheCombination(float value){
        setValue(value);
    }


    public CacheCombination(String name, float value){
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

    public CacheCombination getParents(){
        return (CacheCombination)parents;
    }

    public float getValue(){
        return this.value;
    }

    @Override
    public String getName() {
        String name = super.getName();
        if(name == null){
            if(getMode() == Combine.WITH) {
                name = "WITH";
            }else if(getMode() == Combine.ONEOF){
                name = "ONEOF";
            }else{
                name = "El";
            }
        }

        if(getMode() == Combine.ELEMENT) {
            name = name+"="+(int)this.value;
        }else{
            name = name+":"+cache.size();
        }

        return name;
    }
}
