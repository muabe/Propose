package com.muabe.propose.combine;

import java.util.ArrayList;

/**
 *
 */
public class Combiner{
    protected static final int ELEMENT = 0;
    protected static final int MIX = 1;
    protected static final int PART = 2;

    private Combiner(){}

//    public static ArrayList<Combination> filter(Combination combination){
//        ArrayList<Combination> list = new ArrayList<>();
//
//        switch(combination.mode){
//            case Combiner.ELEMENT :
//                list.add(combination);
//                break;
//
//            case Combiner.MIX :
//                for(Combination childCombine : combination.child){
//                    list.addAll(Combiner.filter(childCombine));
//                }
//                break;
//
//            case Combiner.PART :
//                for(Combination childCombine : combination.child){
//
//                }
//                break;
//        }
//
//        if(combination.mode == Combiner.MIX){
//            ArrayList<Combination> combineList = combination.child;
//            for(Combination childCombine : combineList){
//                list.addAll(Combiner.filter(childCombine));
//            }
//        }else if(combination.mode == Combiner.PART){
//
//        }else{
//            list.add(combination);
//        }
//        return list;
//    }
//
//    public static Combination priority(ArrayList<Combination> combinations){
//        Combination list = null;
//        for(Combination combination : combinations) {
//
//        }
//        return list;
//    }

    public static Combination mix(Combination... combinations){
        return combine(Combiner.MIX, combinations);
    }

    public static Combination part(Combination... combinations){
        return combine(Combiner.PART, combinations);
    }

    private static Combination combine(int mode, Combination... combinations){
        Combination newCombination = new Combination() {
            @Override
            public int priority() {
                return 0;
            }
        };
        newCombination.mode = mode;
        for(Combination combiner : combinations){
            newCombination.child.add(combiner);
        }
        return newCombination;
    }

    public static void getPriorityElements(Combination combination, ArrayList<Combination> list){
        switch(combination.mode) {
            case Combiner.ELEMENT:
                list.add(combination);
                break;

            case Combiner.MIX:
                for (Combination childCombine : combination.child) {
                    getPriorityElements(childCombine, list);
                }
                break;

            case Combiner.PART:
                ArrayList<Combination> winner = null;
                for (Combination childCombine : combination.child) {
                    if (winner == null) {
                        winner = new ArrayList<>();
                        getPriorityElements(childCombine, winner);
                    } else {
                        ArrayList<Combination> challener = new ArrayList<>();
                        getPriorityElements(childCombine, challener);

                        int winnerScore = score(winner);
                        int challenerScore = score(challener);
                        if (winnerScore < challenerScore) {
                            winner = challener;
                        }

                    }
                }
                list.addAll(winner);
                break;
        }
    }

    /**
     * 중요!! 모든 리스트는 Element로 구성되어야함
     * @param list mode가 전부 Element 이여야함
     * @return
     */
    private static int score(ArrayList<Combination> list){
        int i = 0;
        int score = 0;
        for(Combination combination : list){
            if(i==0){
                score = combination.priority();
                i++;
            }else{
                if(score < combination.priority()){
                    score = combination.priority();
                }
            }
        }
        return score;
    }
}
