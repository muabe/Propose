package com.muabe.sample.menu.ratio;

import com.muabe.propose.combination.combiner.PlayPlugin;
import com.muabe.propose.Player;

class RatioCombination extends Player {
    float value;

    private RatioCombination() {
        super();
        setPlugin(new RatioPlugIn());
    }


    public RatioCombination(PlayPlugin playPlugin) {
        super();
        setPlugin(playPlugin);
    }


    @Override
    public int getPriority() {
        return 0;
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

    public String getRealName(){
        return super.getName();
    }
}
