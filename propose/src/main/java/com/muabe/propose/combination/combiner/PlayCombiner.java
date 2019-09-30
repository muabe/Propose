package com.muabe.propose.combination.combiner;


import com.muabe.propose.combination.CombinationBridge;
import com.muabe.propose.combination.Combine;
import com.muabe.propose.player.Player;

import java.util.ArrayList;

public abstract class PlayCombiner<thisCombination extends PlayCombiner, PlayInterfaceType extends PlayerPlugBridge> extends CombinationBridge<thisCombination> {
    private PlayPriority priority = new PlayPriority();
    private PlayInterfaceType playPlugin;

    protected PlayCombiner(){}

    protected PlayCombiner(PlayInterfaceType playPlugin){
        this.playPlugin = playPlugin;
    }

    public void initRatio(){
        priority.setRatioRange(playPlugin.getMinRatio(), playPlugin.getMaxRatio());
    }

    @Override
    public int getPriority() {
        return priority.getPriority();
    }

    @Override
    public float compare(Object param) {
        return priority.compare((float)param);
    }

    public PlayerPlugBridge getPlugin(){
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

    public Player next(T... combinations){
        if (combinations.length > 0) {
            return (T) Combine.all(copyArray(combinations));
        }
        return (T) this;
    }

    public T with(T... combinations){
        if (combinations.length > 0) {
            return (T) Combine.all(copyArray(combinations));
        }
        return (T) this;
    }
}
