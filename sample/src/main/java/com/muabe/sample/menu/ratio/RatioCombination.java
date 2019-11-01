package com.muabe.sample.menu.ratio;

import com.muabe.propose.player.PlayPlugin;
import com.muabe.propose.player.Player;

class RatioCombination extends Player {
    float value;

    public RatioCombination() {
        super(null);
    }

    public RatioCombination(PlayPlugin playPlugin) {
        super(playPlugin);
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

    public RatioCombination getParents(){
        return (RatioCombination)parents;
    }

    public float getValue(){
        return this.value;
    }

    @Override
    public String getName() {
        return String.format("%.3f", ratio);
    }
}
