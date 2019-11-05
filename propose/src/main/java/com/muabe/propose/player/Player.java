package com.muabe.propose.player;


import com.muabe.propose.combination.Combine;
import com.muabe.propose.combination.combiner.PlayCombiner;

public class Player extends PlayCombiner<Player, PlayPlugin> {

    private int weight = 1;
    private int weightSum = 0;

//    private float startRatio = 0f;
//    private float endRatio = 0f;
    protected float ratio = 1f;

    private Player() {
    }


    public Player(PlayPlugin playPlugin) {
        super(playPlugin);
        setRatioRange(0f, 1f);
    }

    @Override
    public Player setName(String name) {
        super.setName(name);
        return this;
    }

    public void setWeightSum(int weightSum) {
        this.weightSum = weightSum;
        //Todo weightSum을 설정 하면 자식들의 ratio 바뀜
    }

    public void setWeight(int weight){
        this.weight = weight;
        setRawRatio();
    }

    public void setRawRatio(){
        if(this.getParents() != null){
            for(Player broPlayer : this.getParents().getChild()){
                broPlayer.rawRoopRatio();
            }
        }else{
            this.rawRoopRatio();
        }
    }

    public void rawRoopRatio(){
        int realWeightSum = this.getRealWeightSum();

        float start = getStartRatio();
        float end;
        int i = 0;
        int childSize = this.getChild().size();
        for (Player child : this.getChild()) {
            child.ratio = (float) child.weight * this.ratio / realWeightSum;
            if(childSize == ++i){
                end = getEndRatio();
            }else{
                end = start + child.ratio;
            }
            child.setRatioRange(start, end);
            start = end;
            if (child.getMode() != Combine.ELEMENT) {
                child.rawRoopRatio();
            }

        }
    }

    public int getRealWeightSum(){
        int parentsWeightSum = weightSum;
        if(parentsWeightSum == 0){
            for(Player childPlayer : this.getChild()){
                parentsWeightSum += childPlayer.weight;
            }
        }
        return parentsWeightSum;
    }



    public int getWeight() {
        return weight;
    }

    public Player next(Player... combinations) {
        if (combinations.length > 0) {
            Player player = (Player)Combine.one(copyArray(combinations));
            player.setRatioRange(0f, 1f);
            player.ratio = 1f;
            player.setWeight(1);
            return  player;
        }
        return this;
    }

    public Player with(Player... combinations) {
        if (combinations.length > 0) {
            Player player = (Player)Combine.all(copyArray(combinations));
            player.setRatioRange(0f, 1f);
            player.ratio = 1f;
            player.setWeight(1);
            return player;
        }
        return this;
    }
}
