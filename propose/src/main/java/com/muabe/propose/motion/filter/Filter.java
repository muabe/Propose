package com.muabe.propose.motion.filter;

import com.muabe.propose.State;
import com.muabe.propose.motion.DragFilter;
import com.muabe.propose.motion.Motion3;
import com.muabe.propose.util.ObservableMap;

import java.util.List;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 */

public class Filter {
    private static ObservableMap<State.MotionState, DragFilter> singleFilters = new ObservableMap<>();

    static {
        singleFilters.clear();
        DirectionFilter directionH = DirectionFilter.getX();
        DirectionFilter directionV = DirectionFilter.getY();

        Filter.add(State.MotionState.LEFT, directionH);
        Filter.add(State.MotionState.RIGHT, directionH);
        Filter.add(State.MotionState.UP, directionV);
        Filter.add(State.MotionState.DOWN, directionV);
    }

    public static void add(State.MotionState state, DragFilter filter) {
        if(isSingle(state)) {
            Filter.singleFilters.put(state, filter);
        }
    }

    //TODO REMOVE - test
    public static void addMotion(Motion3 motion3) {
        if(isSingle(motion3.getMotionState())) {
            if (Filter.singleFilters.containsKey(motion3.getMotionState())) {
                Filter.singleFilters.get(motion3.getMotionState()).addMotion(motion3);
            }
        }
    }

    public static List<DragFilter> getSingleValues() {
        return singleFilters.getValues();
    }

    private static boolean isSingle(State.MotionState state){
        return state.getPointerCount()==1;
    }
}
