package com.muabe.propose.combination;

import android.util.Log;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

/**
 *
 */
public class Combine {
    public static final int ELEMENT = 0;
    public static final int AND = 1;
    public static final int OR = 2;

    private Combine() {
    }

    public static <T extends Combination> T all(T... combinations) {
        return combine(Combine.AND, combinations);
    }

    public static <T extends Combination> T one(T... combinations) {
        return combine(Combine.OR, combinations);
    }

    public static <T extends Combination> T all(boolean optimize, T... combinations) {
        return combine(Combine.AND, optimize, combinations);
    }

    public static <T extends Combination> T one(boolean optimize, T... combinations) {
        return combine(Combine.OR, optimize, combinations);
    }

    private static <T extends Combination> T combine(int mode, T... combinations) {
        return combine(mode, false, combinations);
    }

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

            root.mode = mode;


            for (T combination : combinations) {
                if (combination.mode == Combine.ELEMENT) { // Element 라면 루트에 차일드로 추가
                    root.getRootManager().addElement(combination);
                    combination.disableRoot();
                } else { //기존 루트라면 해당 루트의 차일드리스트(element순서 리스트)를 복사하고 루트해제
                    ArrayList<?> list = combination.getRootManager().getElementList();
                    combination.disableRoot();
                    root.getRootManager().addElementAll(list);
                }
                combination.parents = root;
                if(optimize && mode != Combine.ELEMENT && mode == combination.mode){
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

    public static int count = 0;

    public static <T extends Combination> ArrayList<T> scan(T combination, Object param) {
        count = 0;
        ArrayList<T> list = new ArrayList<>();
        scanLoop(combination, (ArrayList<Combination>) list, param);
        for (Combination cacheCombination : list) {
            addCache(cacheCombination);
        }
        Log.i("Combine", "[검색 횟수:" + count + "]");
        return list;
    }


    private static void scanLoop(Combination combination, ArrayList<Combination> list, Object param) {
        if (combination.deletedCache) {
            combination.deletedCache = false;
            return;
        }
        count++;
        switch (combination.mode) {
            case Combine.ELEMENT:
                if (combination.compare(param) > 0) {
                    list.add(combination);
                } else {
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
                if (combination.cache.size() > 0) {
                    winner = new ArrayList<>();
                    scanLoop(combination.cache.get(0), winner, param);
                } else {
                    for (Combination childCombination : combination.child) {
                        if (winner == null) {
                            winner = new ArrayList<>();
                            scanLoop(childCombination, winner, param);
                        } else {
                            ArrayList<Combination> challener = new ArrayList<>();
                            scanLoop(childCombination, challener, param);

                            float[] winnerScore = preemp(winner, param);
                            float[] challenerScore = preemp(challener, param);

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
     * 부모에 캐시가 등록되어 있지 않으면 캐시를 부모에 등록하고
     * 또 다시 부모의 부모에 대해서 재귀호출을 하면서 캐시를 변경 및 등록해준다.
     * 이미 부모의 캐시가 등록되어 있는 상황이라면 캐시 등록을 끝마친다.
     * OR일 경우 캐시는 한개만 유지
     *
     * @param combination 캐시를 등록할 대상
     */
    private static void addCache(Combination combination) {
        if (combination.parents != null) {
            if (combination.parents.mode == Combine.OR && !combination.parents.cache.contains(combination)) {
                combination.parents.cache.clear();
                combination.parents.cache.add(combination);
            } else if (combination.parents.mode == Combine.AND && combination.parents.cache.size() != combination.parents.child.size()) {
                combination.parents.cache.clear();
                combination.parents.cache.addAll(combination.parents.child);
            } else {
                return;
            }
            addCache(combination.parents);
            Log.i("Combine", "cache=" + combination.parents);
        }
    }

    private static void updateCache(Combination combination, ArrayList<Combination> list, Object param) {
        Combination reScan = deleteCache(combination);
        if (reScan.mode != Combine.ELEMENT) {
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
    private static Combination deleteCache(Combination combination) {
        if (combination.parents != null && combination.parents.cache.contains(combination)) {
            combination.parents.cache.remove(combination);
            Log.i("Combine", combination.parents + " delete=" + combination);
            if (combination.parents.cache.size() == 0) {
                if (combination.parents.mode == Combine.OR && combination.mode != Combine.OR) {
                    combination.deletedCache = true;
                }
                return deleteCache(combination.parents);
            }
        }
        return combination;
    }


    private static void updateCacheBottomTop(Combination combination, ArrayList<Combination> list, Object param) {
        if (combination.parents != null && combination.parents.cache.contains(combination)) {
            combination.parents.cache.remove(combination);
            Log.i("Combine", combination.parents + " delete=" + combination);
            if (combination.parents.cache.size() == 0) {
                if (combination.parents.mode == Combine.OR) {
                    combination.deletedCache = true;
                    ArrayList<Combination> tempList = new ArrayList<>();
                    scanLoop(combination.parents, tempList, param);
                    if (tempList.size() == 0) {
                        updateCacheBottomTop(combination.parents, list, param);
                    } else {
                        list.addAll(tempList);
                    }
                } else if (combination.parents.mode == Combine.AND) {
                    updateCacheBottomTop(combination.parents, list, param);
                }
            }
        }
    }


    public static void clearCache(Combination combination) {
        combination.cache.clear();
        if (combination.mode != Combine.ELEMENT) {
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
