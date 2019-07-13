package com.muabe.propose.player;

import com.muabe.combination.combiner.PlayCombiner;

public class Player extends PlayCombiner<PlayPlugin> {
    private float startRatio;
    private float endRatio;
    private float ratio;

    private Player(){}

    Player(float ratio, PlayPlugin playPlugin) {
        super(playPlugin);
        startRatio = 0f;
        endRatio = 0f;
        this.ratio = ratio;
    }

    public Player setRatio(float ratio){
        this.ratio = ratio;
        return this;
    }

    void setRelationRatio(float startRatio){
        this.startRatio = startRatio;
        this.endRatio = this.startRatio + ratio + endRatio;
    }

    public static Player and(Player... Players){
        return PlayCombiner.and(Players);
    }

    public static Player pr(Player... players){
        return PlayCombiner.or(players);
    }

}
