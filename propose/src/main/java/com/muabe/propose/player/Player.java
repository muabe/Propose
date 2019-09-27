package com.muabe.propose.player;


import com.muabe.propose.combination.combiner.PlayCombiner;

public class Player extends PlayCombiner<Player, PlayPlugin> {
    private float startRatio;
    private float endRatio;
    private float ratio;

    private Player(){}

    Player(float ratio, PlayPlugin playPlugin) {
        super(playPlugin);
        startRatio = 0f;
        endRatio = 0f;
        this.ratio = ratio;
        this.setRatioRange(0f, 1f);
    }

    @Override
    public Player setName(String name) {
        super.setName(name);
        return this;
    }

    public Player setRatio(float ratio){
        this.ratio = ratio;
        return this;
    }

    public Player selfAnd(Player... players) {
        this.setRatioRange(0f, 1f);
        for(Player player : players){
            player.setRatioRange(0f, 1f);
        }
        return super.selfAnd(players);
    }

    public Player selfOr(Player... players) {
        for(Player player : players){
            player.setRatioRange(0f, 1f);
        }
        return super.selfOr(players);
    }

    public static Player and(Player... Players){
        for(Player player : Players){
            player.setRatioRange(0f, 1f);
        }
        return PlayCombiner.and(Players);
    }

    public static Player or(Player... players){
        for(Player player : players){
            player.setRatioRange(0f, 1f);
        }
        return PlayCombiner.or(players);
    }
}
