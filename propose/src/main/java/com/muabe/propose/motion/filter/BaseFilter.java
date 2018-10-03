package com.muabe.propose.motion.filter;

import com.muabe.propose.State;
import com.muabe.propose.motion.LinkedMPoint;
import com.muabe.propose.motion.MPoint;
import com.muabe.propose.motion.Motion3;
import com.muabe.propose.touch.detector.single.SingleTouchEvent;
import com.muabe.propose.util.Mlog;

import java.util.ArrayList;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 */

public abstract class BaseFilter implements LinkedMPoint.OnPointChangeListener{
    private State.MotionState state = State.MotionState.NONE;

    public State.MotionState getState() {
        return state;
    }

    public void addMotion(Motion3 motion3) {

    }

    @Override
    public void onPointChange(State.MotionState preState, State.MotionState currState) {
        state = currState;
        Mlog.e(this, preState+"->" + state);
    }

    public boolean onDrag(SingleTouchEvent event) {

        return false;
    }

    public boolean onDrag(SingleTouchEvent event, ArrayList<MPoint> MPointList) {
        boolean result = false;
        return result;
    }

    /*
    @Override
    public void addMotion(Motion3 motion) {
        LinkedMPoint point = new LinkedMPoint(motion.getMotionState(), motion.getMaxPoint(), motion);
        DirectionFilter.PointObserver observer = new DirectionFilter.PointObserver(point);
        point.setOnPointChangeListener(this);
        pointObservable.put(motion.getMotionState(), observer);

        if (pointObservable.size() > 1) {
            List<State.MotionState> keyList = pointObservable.getKeys();
            pointObservable.get(keyList.get(0)).getPointValue().setLinkMPoint(pointObservable.get(keyList.get(1)).getPointValue());
            pointObservable.get(keyList.get(1)).getPointValue().setLinkMPoint(pointObservable.get(keyList.get(0)).getPointValue());
        }
    }

    @Override
    public boolean onDrag(SingleTouchEvent event) {
        if (pointObservable.size() > 0) {
            float point = this.point.get(event);
            if (point != 0) {
                if (state == State.MotionState.NONE) {
                    for (DirectionFilter.PointObserver observer : pointObservable.getValues()) {
                        if (observer.getPointValue().isLikeOrientation(point)) {
                            onPointChange(state, observer.getPointValue().getState());
                            observer.getPointValue().setPoint(point);
                            return true;
                        }
                    }
                } else {
                    if (pointObservable.containsKey(state)) {
                        LinkedMPoint point = pointObservable.get(state).getPointValue();
                        point.setPoint(point);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    */
}
