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
//
//    private int getAutoWeightSum() {
//        if (weightSum == 0) {
//            int wSum = 0;
//            for (Player p : getChild()) {
//                wSum = +p.getWeight();
//            }
//            return wSum;
//        }
//        return weightSum;
//    }

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
//                Player childPlayer = childList.get(i);
//                float ratio = (float) childPlayer.weight / wSum;
//                float startRatio = 0f;
//                float endRatio = 1f;
//                if(parentsPlayer.getMode() == Combine.OR){
//                    startRatio = totalRatio;
//                    totalRatio += ratio;
//                    endRatio = 1f;
//                    if (i < childList.size() - 1) {
//                        endRatio = totalRatio;
//                    }
//                }
//                childPlayer.setRatioRange(startRatio, endRatio);
//                Log.e("dd", ""+i+"="+ratio+"  "+startRatio+":"+endRatio);
//            }
//        }else{
//            this.setRatioRange(0f, 1f);
//        }
//    }

//    private void setRatio(int weight){
//        this.weight = weight;
//    }


//    private void resetRoopRatio(){
//        if(getParents() != null){
//            ratio = (float) weight*getParents().ratio/getParents().getParentsWeightSum();
//        }else{
//            ratio = 1f;
//        }
//        for(Player player : getChild()){
//            player.resetRoopRatio();
//        }
//    }
//
//    private void resetRatio(){
//        if(getParents() !=null){
//            for(Player player : getParents().getChild()){
//                player.resetRoopRatio();
//            }
//        }else{
//            resetRoopRatio();
//        }
//    }
//
//
//
//    private int getParentsWeightSum(){
//        if(getParents() != null){
//            if(getParents().weightSum == 0){
//                int autoWeightSum = 0;
//                for(Player childPlayer : getParents().getChild()){
//                    autoWeightSum += childPlayer.weight;
//                }
//                return autoWeightSum;
//            }else{
//                return getParents().weightSum;
//            }
//        }else{
//            return 0;
//        }
//    }









//
//    private void resetRoopRatio(){
//        //부모의 weightSum을 구함
//        int parentsWeightSum = 0;
//        if(this.getParents().weightSum == 0){
//            for(Player childPlayer : this.getParents().getChild()){
//                parentsWeightSum += childPlayer.weight;
//            }
//        }else{
//            parentsWeightSum =  getParents().weightSum;
//        }
//
//        ratio = (float) weight*getParents().ratio/parentsWeightSum;
//        for(Player player : getChild()){
//            player.resetRoopRatio();
//        }
//    }
//
//    private void resetRatio(){
//        if(this.getParents() !=null){
//            for(Player player : this.getParents().getChild()){
//                player.resetRoopRatio();
//            }
//        }else{
//            ratio = 1f;
//        }
//    }

    /////////////////////////
    public void setWeight(int weight){
        this.weight = weight;
        selfRatio();
    }

    public void selfRatio(){
        if(this.getParents() != null){
            for(Player broPlayer : this.getParents().getChild()){
                broPlayer.selfRoopRatio();
            }
        }else{
            this.selfRoopRatio();
        }
    }

    public void selfRoopRatio(){
        int realWeightSum = this.getRealWeightSum();
        int i = 0;
        float start = getStartRatio();
        float end = 0;

        for(Player child : this.getChild()){
            child.ratio = (float) child.weight * this.ratio / realWeightSum;
            end = start+child.ratio;
            child.setRatioRange(start, end);
            start = end;
            if(child.getMode() != Combine.ELEMENT){
                child.selfRoopRatio();
            }
            i++;
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

    //////////////////////////
    

//    public float getRawRatio(){
//        Player parentsPlayer = getParents();
//        float ratio = getRatio();
//        if (parentsPlayer == null) {
//            return ratio;
//        } else {
//            return ratio * parentsPlayer.getRawRatio();
//        }
//    }

    public int getWeight() {
        return weight;
    }

    public Player next(Player... combinations) {
        if (combinations.length > 0) {
            Player player = (Player)Combine.one(copyArray(combinations));
            player.setWeight(1);
            return  player;
        }
        return this;
    }

    public Player with(Player... combinations) {
        if (combinations.length > 0) {
            Player player = (Player)Combine.all(copyArray(combinations));
            player.setWeight(1);
            return player;
        }
        return this;
    }
}
