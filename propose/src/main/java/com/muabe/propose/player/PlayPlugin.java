package com.muabe.propose.player;

import com.muabe.propose.combination.combiner.PlayerPlugBridge;

public abstract class PlayPlugin implements PlayerPlugBridge<Player> {


    @Override
    public float getMinRatio(){
        return 0f;
    }

    @Override
    public float getMaxRatio(){
        return 1f;
    }
}
