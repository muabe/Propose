package com.muabe.propose;


import com.muabe.propose.combination.Combine;
import com.muabe.propose.combination.combiner.PlayCombiner;
import com.muabe.propose.combination.combiner.PlayPlugin;

public class Player extends PlayCombiner<Player> {

    private int weight = 1;
    private int weightSum = 0;
    protected float ratio = 1f;
    private PlayPlugin plugin;

    protected Player() {
    }

    public static Player create(PlayPlugin plugin){
        Player player = new Player();
        player.setRatioRange(0f, 1f);
        player.setPlugin(plugin);
        return player;
    }

    public Player setPlugin(PlayPlugin plugin){
        this.plugin = plugin;
        setName(plugin.getClass().getSimpleName());
        return this;
    }

    @Override
    public PlayPlugin getPlugin() {
        return plugin;
    }

    public void setWeightSum(int weightSum) {
        this.weightSum = weightSum;
        //Todo weightSum을 설정 하면 자식들의 ratio 바뀜
    }

    public void setWeight(int weight){
        this.weight = weight;
        setRawRatio();
    }

    private void setRawRatio(){
        if(this.getParents() != null){
            for(Player broPlayer : this.getParents().getChild()){
                broPlayer.rawRoopRatio();
            }
        }else{
            this.rawRoopRatio();
        }
    }

    private void rawRoopRatio(){
        int realWeightSum = this.getRealWeightSum();

        float start = getStartRatio();
        float end;
        int i = 0;
        int childSize = this.getChild().size();
        for (Player child : this.getChild()) {
            if(this.getMode() == Combine.ONEOF){
                child.ratio = (float) child.weight * this.ratio / realWeightSum;
                if(childSize == ++i){
                    end = getEndRatio();
                }else{
                    end = start + child.ratio;
                }
                child.setRatioRange(start, end);
                start = end;
            }else{
                child.ratio = (float) child.weight * this.ratio;
                child.setRatioRange(start, getEndRatio());
                if(childSize == ++i){
                    start = getEndRatio();
                }
            }
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
            Player player = (Player)Combine.oneof(copyArray(combinations));
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
