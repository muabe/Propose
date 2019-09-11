package com.muabe.propose.combination.combiner;


import com.muabe.propose.combination.CombinationBridge;
import com.muabe.propose.combination.Combine;

import java.util.ArrayList;

public abstract class PlayCombiner<thisCombination extends PlayCombiner, PlayInterfaceType extends PlayIPlugBridge> extends CombinationBridge<thisCombination> {
    private PlayPriority priority = new PlayPriority();
    private PlayInterfaceType playPlugin;

    protected PlayCombiner(){}

    protected PlayCombiner(PlayInterfaceType playPlugin){
        this.playPlugin = playPlugin;
    }

    public static <T extends PlayCombiner>T and(T... playCombiners){
        return Combine.all(playCombiners);
    }

    public static <T extends PlayCombiner>T or(T... playCombiners){
        return Combine.one(playCombiners);
    }

    public void setRatioRange(float minRatio, float maxRatio){
        priority.setRatioRange(minRatio, maxRatio);
    }


    @Override
    public int getPriority() {
        return priority.getPriority();
    }

    @Override
    public float compare(Object param) {
        return priority.compare((float)param);
    }

    public PlayIPlugBridge getPlugin(){
        return this.playPlugin;
    }

    public boolean play(float ratio){
        boolean result = false;
        ArrayList<PlayCombiner> players = Combine.scan((PlayCombiner)this, ratio);
        for (PlayCombiner player : players) {
            if (player.getPlugin() !=null && player.getPlugin().play(this, player.priority.getPlayRatio(ratio))) {
                result = true;
            }
        }
        return result;
    }
}
