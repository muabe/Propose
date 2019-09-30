package com.muabe.propose.player;


import com.muabe.propose.combination.Combine;
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
        //Todo weightSum을 설정 하면 자식들의 ratio 바뀜
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
        //Todo weight를 설정 하면 ratio가 형성됨, 그리고 wegitSum에 따라(0일경우) 자식들의 ratio도 바뀜
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


    public Player next(Player... combinations){
        if (combinations.length > 0) {
            return (Player)Combine.all(copyArray(combinations));
        }
        return this;
    }

    public Player with(Player... combinations){
        if (combinations.length > 0) {
            return (Player)Combine.all(copyArray(combinations));
        }
        return this;
    }
}
