package com.muabe.propose.player;


import android.util.Log;

import com.muabe.propose.combination.Combine;
import com.muabe.propose.combination.combiner.PlayCombiner;

import java.util.ArrayList;

public class Player extends PlayCombiner<Player, PlayPlugin> {

    private int weight = 1;
    private int weightSum = 0;
//    private float startRatio = 0f;
//    private float endRatio = 0f;
//    private float ratio = 1f;

    private Player() {
    }


    public Player(PlayPlugin playPlugin) {
        super(playPlugin);
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

    //TODO weightsum을 설정하지 않았을땐 0을 리턴해야되는데 자식들의 sum값을 리턴한다. 바꿈 필요
    private int getWeightSum() {
        if (weightSum == 0) {
            int wSum = 0;
            for (Player p : getChild()) {
                wSum = +p.getWeight();
            }
            return wSum;
        }
        return weightSum;
    }

//    public void setWeight(int weight) {
//        this.weight = weight;
//        Player parentsPlayer = getParents();
//        if(parentsPlayer != null){
//            int wSum = parentsPlayer.weightSum;
//            if (wSum == 0) {
//                for (Player p : parentsPlayer.getChild()) {
//                    wSum += p.getWeight();
//                }
//            }
//            //ratio 설정
//            float totalRatio = 0f;
//            ArrayList<Player> childList = getParents().getChild();
//            for (int i=0; i<childList.size(); i++) {
//                Player player = childList.get(i);
//                player.ratio = (float) this.weight / wSum;
//                player.startRatio = totalRatio;
//                totalRatio += player.ratio;
//
//                if(i == childList.size()-1){
//                    player.endRatio = 1f;
//                }else{
//                    player.endRatio = totalRatio;
//                }
//                player.setRatioRange(player.startRatio, player.endRatio);
//                Log.e("dd", ""+i+"="+player.ratio+"  "+player.startRatio+":"+player.endRatio);
//            }
//        }
//    }

    public void setWeight(int weight) {
        this.weight = weight;
        Player parentsPlayer = getParents();
        if(parentsPlayer != null){
            int wSum = parentsPlayer.weightSum;
            if (wSum == 0) {
                for (Player p : parentsPlayer.getChild()) {
                    wSum += p.getWeight();
                }
            }
            //ratio 설정
            float totalRatio = 0f;
            ArrayList<Player> childList = getParents().getChild();
            for (int i=0; i<childList.size(); i++) {
                Player player = childList.get(i);
                float ratio = (float) player.weight / wSum;
                float startRatio = 0f;
                float endRatio = 1f;
                if(parentsPlayer.getMode() == Combine.OR){
                    startRatio = totalRatio;
                    totalRatio += ratio;
                    endRatio = 1f;
                    if (i < childList.size() - 1) {
                        endRatio = totalRatio;
                    }
                }
                player.setRatioRange(startRatio, endRatio);
                Log.e("dd", ""+i+"="+ratio+"  "+startRatio+":"+endRatio);
            }
        }
    }


//    public float getRawRatio() {
//        Player parentsPlayer = getParents();
//        if (parentsPlayer == null) {
//            return ratio;
//        } else {
//            return ratio * parentsPlayer.getRawRatio();
//        }
//    }

    public float getRawStartRatio(float rawRatio){
        Player parentsPlayer = getParents();
        if (parentsPlayer == null) {
            return rawRatio;
        } else {
            return ;
        }
    }

    public int getWeight() {
        return weight;
    }

    public Player next(Player... combinations) {
        if (combinations.length > 0) {
            Player player = (Player)Combine.one(copyArray(combinations));
            this.setWeight(1);
            return  player;
        }
        return this;
    }

    public Player with(Player... combinations) {
        if (combinations.length > 0) {
            Player player = (Player)Combine.all(copyArray(combinations));
            this.setWeight(1);
            return  player;
        }
        return this;
    }
}
