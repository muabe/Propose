package com.muabe.propose.combination.combiner;


import com.muabe.propose.combination.CombinationBridge;
import com.muabe.propose.combination.Combine;

import java.util.ArrayList;

public abstract class PlayCombiner<thisCombination extends PlayCombiner, PlayInterfaceType extends PlayerPlugBridge> extends CombinationBridge<thisCombination> {
    private PlayPriority priority = new PlayPriority();
    private PlayInterfaceType playPlugin;

    protected PlayCombiner(){}

    protected PlayCombiner(PlayInterfaceType playPlugin){
        this.playPlugin = playPlugin;
    }

    public void setRatioRange(float start, float end){
        priority.setRatioRange(start, end);
    }

    public float getStartRatio(){
        return priority.getStartRatio();
    }

    public float getEndRatio(){
        return priority.getEndRatio();
    }

    public float getRatio(){
        return priority.getRatio();
    }

    private float getRawRatio(){
        PlayCombiner parentsPlayer = getParents();
        float ratio = priority.getRatio();
        if (parentsPlayer == null) {
            return 1f;
        } else {
            return ratio * parentsPlayer.getRawRatio();
        }
    }

    @Override
    public int getPriority() {
        return priority.getPriority();
    }

    @Override
    public float compare(Object param) {
        if (priority.getStartRatio() < (float)param && (float)param <= priority.getEndRatio()) {
            return 1f;
        }
        return 0f;
    }

    public PlayerPlugBridge getPlugin()
    {
        return this.playPlugin;
    }

    public boolean play(float rawRatio){
        boolean result = false;
        ArrayList<PlayCombiner> players = Combine.scan((PlayCombiner)this, rawRatio);
        for (PlayCombiner player : players) {
            float relRatio = (rawRatio - player.getStartRatio())/player.getRatio();
            if (player.getPlugin() !=null && player.getPlugin().play(this, relRatio)) {
                result = true;
            }
        }
        return result;
    }
}
