package com.muabe.propose.player;


import com.muabe.propose.combination.combiner.PlayCombiner;

public class Player extends PlayCombiner<Player, PlayPlugin> {

    private int weight = 1;
    private int weightSum = 0;

    private Player(){}


    public Player(int weight, PlayPlugin playPlugin) {
        super(playPlugin);
        this.setWeight(weight);
        this.initRatio();
    }

    @Override
    public Player setName(String name) {
        super.setName(name);
        return this;
    }

    public void setWeightSum(int weightSum){
        this.weightSum = weightSum;
    }

    public int getWeightSum(){
        if(weightSum == 0){
            int wSum = 0;
            for(Player p : getChild()){
                wSum =+ p.getWeight();
            }
            return wSum;
        }
        return weightSum;
    }

    public void setWeight(int weight){
        this.weight = weight;
        this.initRatio();
    }

    public int getWeight(){
        return weight;
    }

    private Player initWeightSumAndRatio(Player player){
        int wSum = 0;
        for(Player p : player.getChild()){
            wSum =+ p.getWeight();
            //TODO ratio설정
        }
        player.setWeightSum(wSum);
        return player;
    }

}
