package com.muabe.propose.combine;

import android.util.Log;

import java.util.ArrayList;

/**
 *
 */
public class Combine {
    public static final int ELEMENT = 0;
    public static final int AND = 1;
    public static final int OR = 2;

    private Combine(){}

    public static Combination all(Combination... combinations){
        return combine(Combine.AND, combinations);
    }

    public static Combination one(Combination... combinations){
        return combine(Combine.OR, combinations);
    }

    private static Combination combine(int mode, Combination... combinations){
        Combination newCombination = new Combination() {
            @Override
            public int priority() {
                return 0;
            }
        };
        newCombination.mode = mode;
        for(Combination combination : combinations){
            combination.parents = newCombination;
            newCombination.child.add(combination);
        }
        return newCombination;
    }

    public static int count=0;

    public static ArrayList<Combination> scan(Combination combination){
        count = 0;

        ArrayList<Combination> list = new ArrayList<>();
        scan(combination, list);
        for(Combination cacheCombination : list){
            addCache(cacheCombination);
        }

        Log.e("dd", "[검색 회수:"+count+"]");
        return list;
    }

    private static void scan(Combination combination, ArrayList<Combination> list){
        count++;
        switch(combination.mode) {
            case Combine.ELEMENT:
                if(combination.priority() > 0) {
                    list.add(combination);
                }else{
                    Combination reScan = deleteCache(combination);
                    if(reScan.mode != Combine.ELEMENT) {
                        Log.e("dd", "재스캔!! = "+reScan.toString());
                        scan(reScan, list);
                    }
                }
                break;

            case Combine.AND:
                int preListSize = list.size();
                for (Combination childCombine : combination.child) {
                    scan(childCombine, list);
                }
                int postListSize = list.size();
                if(postListSize > preListSize){

                }
                break;

            case Combine.OR:
                ArrayList<Combination> winner = null;
                if(combination.cache.size()>0){
                    winner = new ArrayList<>();
                    scan(combination.cache.get(0), winner);
                }else{
                    for (Combination childCombination : combination.child) {
                        if (winner == null) {
                            winner = new ArrayList<>();
                            scan(childCombination, winner);
                        } else {
                            ArrayList<Combination> challener = new ArrayList<>();
                            scan(childCombination, challener);

                            int winnerScore = score(winner);
                            int challenerScore = score(challener);
                            if (challenerScore > 0 && winnerScore < challenerScore) {
                                winner = challener;
                            }
                        }
                    }
                }
                list.addAll(winner);
                break;
        }
    }

    /**
     * 부모에 캐쉬가 등록되어 있지 않으면 캐쉬를 부모에 등록하고
     * 또 다시 부모의 부모에 대해서 재귀호출을 하면서 캐쉬를 변경 및 등록해준다.
     * 이미 부모의 캐쉬가 등록되어 있는 상황이라면 캐쉬 등록을 끝마친다.
     * OR일 경우 캐쉬는 한개만 유지
     * @param combination 캐쉬를 등록할 대상
     */
    private static void addCache(Combination combination){
        if(combination.parents!=null){
            if(combination.parents.mode == Combine.OR && !combination.parents.cache.contains(combination)){
                combination.parents.cache.clear();
                combination.parents.cache.add(combination);
            }else if(combination.parents.mode == Combine.AND && combination.parents.cache.size() != combination.parents.child.size()){
                combination.parents.cache.clear();
                combination.parents.cache.addAll(combination.parents.child);
            }else{
                return;
            }
            addCache(combination.parents);
            Log.i("dd", "cache="+combination.parents);
        }
    }

    /**
     * 부모의 캐쉬에 이미 등록되지 않은 상태라면 빠져나간다.
     * 캐쉬가 등록되어 있을 경우 캐쉬를 삭제하고 부모의 캐쉬하 하나도 남지 않으면
     * 부모의 부모를 재귀호출을 하면서 캐쉬의 삭제를 진행한다.
     * @param combination 캐쉬를 삭제할 대상
     */
    private static Combination deleteCache(Combination combination){
        if(combination.parents!=null && combination.parents.cache.contains(combination)){
            combination.parents.cache.remove(combination);
            Log.i("dd", combination.parents+" delete="+combination);
            if(combination.parents.cache.size()==0){
                combination.parents.deletedCache = true;
                combination.deletedCache = false;
                return deleteCache(combination.parents);

            }
        }
        return combination;
    }

    /**
     * 중요!! 모든 리스트는 Element로 구성되어야함
     * @param list mode가 전부 Element 이여야함
     * @return combination의 priority 값
     */
    private static int score(ArrayList<Combination> list){
        int i = 0;
        int score = -1;
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
