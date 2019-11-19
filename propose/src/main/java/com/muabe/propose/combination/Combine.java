package com.muabe.propose.combination;

import android.util.Log;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

/**
 *
 */
public class Combine {
    private static boolean DEFAULT_OPTIMIZE = true;
    public static final int ELEMENT = 0;
    public static final int AND = 1;
    public static final int OR = 2;


    private Combine() {
    }

    @SafeVarargs
    public static <T extends Combination> T all(T... combinations) {
        return combine(Combine.AND, combinations);
    }

    @SafeVarargs
    public static <T extends Combination> T one(T... combinations) {
        return combine(Combine.OR, combinations);
    }

    @SafeVarargs
    public static <T extends Combination> T all(boolean optimize, T... combinations) {
        return combine(Combine.AND, optimize, combinations);
    }

    @SafeVarargs
    public static <T extends Combination> T one(boolean optimize, T... combinations) {
        return combine(Combine.OR, optimize, combinations);
    }

    @SafeVarargs
    private static <T extends Combination> T combine(int mode, T... combinations) {
        return combine(mode, DEFAULT_OPTIMIZE, combinations);
    }

    @SafeVarargs
    private static <T extends Combination> T combine(int mode, boolean optimize, T... combinations) {
        if (combinations.length > 0) {
            T root;
            try {
                Constructor<T> constructor = (Constructor<T>) combinations[0].getClass().getDeclaredConstructor();
                constructor.setAccessible(true);
                root = constructor.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            root.setMode(mode);


            for (T combination : combinations) {
                if (combination.getMode() == Combine.ELEMENT) { // Element 라면 루트에 차일드로 추가
                    root.getRootManager().addElement(combination);
                    combination.disableRoot();
                } else { //기존 루트라면 해당 루트의 차일드리스트(element순서 리스트)를 복사하고 루트해제
                    ArrayList<?> list = combination.getRootManager().getElementList();
                    combination.disableRoot();
                    root.getRootManager().addElementAll(list);
                }
                combination.parents = root;
                if(optimize && mode != Combine.ELEMENT && mode == combination.getMode()){
                    root.child.addAll(combination.child);
                }else{
                    root.child.add(combination);
                }
            }
            return root;
        } else {
            return null;
        }
    }

    public static boolean isDefaultOptimize(){
        return DEFAULT_OPTIMIZE;
    }

    public static void setDefaultOptimize(boolean isDefaultOptimize){
        Combine.DEFAULT_OPTIMIZE = isDefaultOptimize;
    }

    public static void optimize(Combination combination){
        if(combination.getMode() == Combine.ELEMENT){
            return;
        }

        ArrayList<Combination> list = new ArrayList<>();
        for(Combination child : combination.getChild(Combination.class)){
            if(child.getMode() != Combine.ELEMENT){
                optimize(child);
            }
            if(combination.getMode() == child.getMode()){
                list.addAll(child.getChild(Combination.class));
            }else{
                list.add(child);
            }
        }
        combination.resetChild(list);
    }

    public static <T extends Combination> ScanResult<T> scan(T combination, Object param) {
        ScanResult<T> scanResult = new ScanResult<>();
        scanLoop(combination, scanResult, param);
        for (Combination cacheCombination : scanResult.getScanList()) {
            addCache(cacheCombination);
        }
        return scanResult;
    }

    public static int count = 0;
//    public static <T extends Combination> ArrayList<T> scan2(T combination, Object param) {
//        count = 0;
//        ArrayList<T> list = new ArrayList<>();
//        scanLoop(combination, scanResult, param);
//        String log = "";
//        for (Combination cacheCombination : list) {
//            addCache(cacheCombination);
//            log = log+cacheCombination.getName()+", ";
//        }
//        Log.e("dd", log);
//        Log.i("Combine", "[검색 횟수:" + count + "]");
//        return list;
//    }


    private static void scanLoop(Combination combination, ScanResult list, Object param) {
        if (combination.deletedCache) {
            combination.deletedCache = false;
            return;
        }
        count++;
        switch (combination.getMode()) {
            case Combine.ELEMENT:
                if (combination.compare(param) > 0) {
                    list.add(combination);
                } else {
                    deleteCache(combination, list, param);
                }
                break;

            case Combine.AND:
                for (Combination childCombine : combination.child) {
                    scanLoop(childCombine, list, param);
                }
                break;
            case Combine.OR:
                ScanResult winner = null;
                if (combination.cache.size() > 0) {
                    winner = new ScanResult();
                    scanLoop(combination.cache.get(0), winner, param); //OR의 경우 cache가 하나 밖에 없다.
                } else {
                    for (Combination childCombination : combination.child) {
                        if (winner == null) {
                            winner = new ScanResult();
                            scanLoop(childCombination, winner, param);
                        } else {
                            ScanResult challener = new ScanResult();
                            scanLoop(childCombination, challener, param);

                            float[] winnerScore = preemp(winner.getScanList(), param);
                            float[] challenerScore = preemp(challener.getScanList(), param);

                            if (challenerScore[1] > 0) {
                                if (winnerScore[1] > 0) {
                                    if (winnerScore[0] < challenerScore[0] || (winnerScore[0] == challenerScore[0] && winnerScore[1] < challenerScore[1])) {
                                        winner = challener;
                                    }
                                } else {
                                    winner = challener;
                                }
                            }
                        }
                    }
                }
                list.addAll(winner);
                break;
        }
    }

    /**
     * 캐시는 부모 노드 즉 and,or에 element를 저장한다.
     * 따라서 최종 하위 노드인 elemnet에서 시작하여 자신의 부모를 검색하면서
     * 루트노드까지 변경된 캐시를 업데이트 한다.
     * Or의 경우 이미 동일한 캐시가 존재하는지 검사하고 동일하지 않으면 클리어하고 캐시를 다시 설정한다.
     * And의 경우 모는 자식을 캐시로 저장해야 하기 때문에 이전 캐시의 사이즈가 동일한지 검사하고
     * 동일하지 않으면 클리어하고 캐시를 다시 설정한다.
     * 이런식으로 하위 노드에서 루트노드까지 재귀하면서 캐시를 저장하게 된다.
     * 이렇게 하여 최종 root에서 사용가능한 모든 combination이 캐시로 저장되게 된다.
     * or,and에서 이미 동일한 캐시가 있을 경우 더 이상 검사하지 않고 재귀 호출을 빠져 나온다.
     *
     * 부모에 캐시가 등록되어 있지 않으면 캐시를 부모에 등록하고
     * 또 다시 부모의 부모에 대해서 재귀호출을 하면서 캐시를 변경 및 등록해준다.
     * 이미 부모의 캐시가 등록되어 있는 상황이라면 캐시 등록을 끝마친다.
     * OR일 경우 캐시는 한개만 유지
     *
     * @param combination 캐시를 등록할 대상
     */
    private static void addCache(Combination combination) {
        if (combination.parents != null) {
            if (combination.parents.getMode() == Combine.OR && !combination.parents.cache.contains(combination)) {
                combination.parents.cache.clear();
                combination.parents.cache.add(combination);
            } else if (combination.parents.getMode() == Combine.AND && combination.parents.cache.size() != combination.parents.child.size()) {
                combination.parents.cache.clear();
                combination.parents.cache.addAll(combination.parents.child);
            }
            addCache(combination.parents);
            Log.i("Combine", "cache=" + combination.parents);
        }
    }

    /**
     * 이전의 캐시를 지우기 위해서 쓰는거임
     * @param combination
     * @param list
     * @param param
     */
    private static void deleteCache(Combination combination, ScanResult list, Object param) {
        Combination reScan = deleteCache(combination, list);
        if (reScan.getMode() != Combine.ELEMENT) {
            Log.i("Combine", "Reset Cache = " + reScan.toString());
            scanLoop(reScan, list, param);
        }
    }

    /**
     * 부모의 캐시에 이미 등록되지 않은 상태라면 빠져나간다.
     * 캐시가 등록되어 있을 경우 캐시를 삭제하고 부모의 캐시가 하나도 남지 않으면
     * 부모의 부모를 재귀호출을 하면서 캐시의 삭제를 진행한다.
     *
     * @param combination 캐시를 삭제할 대상
     */
    private static Combination deleteCache(Combination combination, ScanResult list) {
        if (combination.parents != null && combination.parents.cache.contains(combination)) {
            combination.parents.cache.remove(combination);
            list.addDelete(combination);
            Log.i("Combine", combination.parents + " delete=" + combination);
            if (combination.parents.cache.size() == 0) {
                if (combination.parents.getMode() == Combine.OR && combination.getMode() != Combine.OR) {
                    combination.deletedCache = true;
                }
                return deleteCache(combination.parents, list);
            }
        }
        return combination;
    }


//    private static void deleteCacheBottomTop(Combination combination, ArrayList<Combination> list, Object param) {
//        if (combination.parents != null && combination.parents.cache.contains(combination)) {
//            combination.parents.cache.remove(combination);
//            Log.i("Combine", combination.parents + " delete=" + combination);
//            if (combination.parents.cache.size() == 0) {
//                if (combination.parents.getMode() == Combine.OR) {
//                    combination.deletedCache = true;
//                    ArrayList<Combination> tempList = new ArrayList<>();
//                    scanLoop(combination.parents, tempList, param);
//                    if (tempList.size() == 0) {
//                        deleteCacheBottomTop(combination.parents, list, param);
//                    } else {
//                        list.addAll(tempList);
//                    }
//                } else if (combination.parents.getMode() == Combine.AND) {
//                    deleteCacheBottomTop(combination.parents, list, param);
//                }
//            }
//        }
//    }


    public static void clearCache(Combination combination) {
        combination.cache.clear();
        if (combination.getMode() != Combine.ELEMENT) {
            for (Combination childCombine : combination.child) {
                clearCache(childCombine);
            }
        }
    }

    /**
     * 중요!! 모든 리스트는 Element로 구성되어야함
     *
     * @param list mode가 전부 Element 이여야함
     * @return combination의 compare 값
     */
    private static float[] preemp(ArrayList<Combination> list, Object param) {
        int i = 0;
        float[] score = {0f, -1f};
        for (Combination combination : list) {
            if (i == 0) {
                score[0] = combination.getPriority();
                score[1] = combination.compare(param);
                i++;
            } else {
                int tempPriority = combination.getPriority();
                float tempCompare = combination.compare(param);

                if (tempCompare > 0) {
                    if (score[0] < tempPriority) {
                        score[0] = tempPriority;
                        score[1] = tempCompare;
                    } else if (score[0] > tempPriority) {
                        if (score[1] == 0) {
                            score[0] = tempPriority;
                            score[1] = tempCompare;
                        }
                    } else {
                        if (score[1] < combination.compare(param)) {
                            score[1] = combination.compare(param);
                        }
                    }
                }
            }
        }
        return score;
    }
}
