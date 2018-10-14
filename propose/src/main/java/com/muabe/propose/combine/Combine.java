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
            public float priority(Object o) {
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

    public static int count = 0;

    public static ArrayList<Combination> scan(Combination combination, Object param){
        count = 0;

        ArrayList<Combination> list = new ArrayList<>();
        scanLoop(combination, list, param);
        for(Combination cacheCombination : list){
            addCache(cacheCombination);
        }

        Log.e("dd", "[검색 횟수:"+count+"]");
        return list;
    }

    private static void scanLoop(Combination combination, ArrayList<Combination> list, Object param){
        if(combination.deletedCache){
            combination.deletedCache = false;
            return;
        }
        count++;
        switch(combination.mode) {
            case Combine.ELEMENT:
                if(combination.priority(param) > 0) {
                    list.add(combination);
                }else{
                    updateCache(combination, list, param);
                }
                break;

            case Combine.AND:
                for (Combination childCombine : combination.child) {
                    scanLoop(childCombine, list, param);
                }
                break;
            case Combine.OR:
                ArrayList<Combination> winner = null;
                if(combination.cache.size()>0){
                    winner = new ArrayList<>();
                    scanLoop(combination.cache.get(0), winner, param);
                }else{
                    for (Combination childCombination : combination.child) {
                        if (winner == null) {
                            winner = new ArrayList<>();
                            scanLoop(childCombination, winner, param);
                        } else {
                            ArrayList<Combination> challener = new ArrayList<>();
                            scanLoop(childCombination, challener, param);

                            float winnerScore = score(winner, param);
                            float challenerScore = score(challener, param);
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

    private static <T>void updateCache(Combination combination, ArrayList<Combination> list, Object param){
        Combination reScan = deleteCache(combination);
        if(reScan.mode != Combine.ELEMENT) {
            Log.e("dd", "재스캔!! = "+reScan.toString());
            scanLoop(reScan, list, param);
        }
    }

    /**
     * 부모의 캐쉬에 이미 등록되지 않은 상태라면 빠져나간다.
     * 캐쉬가 등록되어 있을 경우 캐쉬를 삭제하고 부모의 캐쉬가 하나도 남지 않으면
     * 부모의 부모를 재귀호출을 하면서 캐쉬의 삭제를 진행한다.
     * @param combination 캐쉬를 삭제할 대상
     */
    private static Combination deleteCache(Combination combination){
        if(combination.parents!=null && combination.parents.cache.contains(combination)){
            combination.parents.cache.remove(combination);
            Log.i("dd", combination.parents+" delete="+combination);
            if(combination.parents.cache.size()==0){
                if(combination.parents.mode == Combine.OR && combination.mode != Combine.OR){
                    combination.deletedCache = true;
                }
                return deleteCache(combination.parents);
            }
        }
        return combination;
    }


    private static <T>void updateCascheBottomTop(Combination combination, ArrayList<Combination> list, Object param){
        if(combination.parents!=null && combination.parents.cache.contains(combination)){
            combination.parents.cache.remove(combination);
            Log.i("dd", combination.parents+" delete="+combination);
            if(combination.parents.cache.size()==0){
                if(combination.parents.mode == Combine.OR){
                    combination.deletedCache = true;
                    ArrayList<Combination> tempList = new ArrayList<>();
                    scanLoop(combination.parents, tempList, param);
                    if(tempList.size()==0){
                        updateCascheBottomTop(combination.parents, list, param);
                    }else{
                        list.addAll(tempList);
                    }
                }else if(combination.parents.mode == Combine.AND){
                    updateCascheBottomTop(combination.parents, list, param);
                }
            }
        }
    }


    public static void clearCache(Combination combination){
        combination.cache.clear();
        if(combination.mode != Combine.ELEMENT) {
            for (Combination childCombine : combination.child) {
                clearCache(childCombine);
            }
        }
    }


    /**
     * 중요!! 모든 리스트는 Element로 구성되어야함
     * @param list mode가 전부 Element 이여야함
     * @return combination의 priority 값
     */
    private static <T>float score(ArrayList<Combination> list, Object param){
        int i = 0;
        float score = -1f;
        for(Combination combination : list){
            if(i==0){
                score = combination.priority(param);
                i++;
            }else{
                if(score < combination.priority(param)){
                    score = combination.priority(param);
                }
            }
        }
        return score;
    }
}
