package com.muabe.propose.combine;

import java.util.ArrayList;

public abstract class Combination{
    protected int mode = Combiner.ELEMENT;
    protected ArrayList<Combination> child = new ArrayList<>();

//    public ArrayList<Combination> getPriorityElements(){
//        ArrayList<Combination> list = new ArrayList<>();
//        switch(mode){
//            case Combiner.ELEMENT :
//                list.add(this);
//                break;
//
//            case Combiner.MIX :
//                for(Combination childCombine : child){
//                    list.addAll(childCombine.getPriorityElements());
//                }
//                break;
//
//            case Combiner.PART :
//                ArrayList<Combination> winner = null;
//                for(Combination childCombine : child){
//                    if(winner==null){
//                        winner = childCombine.getPriorityElements();
//                    }else{
//                        ArrayList<Combination> challener = childCombine.getPriorityElements();
//                        int winnerScore = score(winner);
//                        int challenerScore = score(challener);
//                        if(winnerScore < challenerScore){
//                            winner = challener;
//                        }
//                    }
//                }
//                list.addAll(winner);
//                break;
//        }
//        return list;
//    }



    public abstract int priority();

}
