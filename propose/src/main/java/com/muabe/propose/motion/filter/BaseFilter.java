package com.muabe.propose.motion.filter;

import com.muabe.propose.State;
import com.muabe.propose.motion.LinkedPoint;
import com.muabe.propose.motion.Motion;
import com.muabe.propose.motion.Point;
import com.muabe.propose.touch.detector.single.SingleTouchEvent;
import com.muabe.propose.util.Mlog;

import java.util.ArrayList;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 */

public abstract class BaseFilter implements LinkedPoint.OnPointChangeListener{
    private State.MotionState state = State.MotionState.NONE;

    public State.MotionState getState() {
        return state;
    }

    public void addMotion(Motion motion) {

    }

    @Override
    public void onPointChange(State.MotionState preState, State.MotionState currState) {
        state = currState;
        Mlog.e(this, preState+"->" + state);
    }

    public boolean onDrag(SingleTouchEvent event) {

        return false;
    }

    public boolean onDrag(SingleTouchEvent event, ArrayList<Point> pointList) {
        boolean result = false;
        return result;
    }

    /*
    @Override
    public void addMotion(Motion motion) {
        LinkedPoint point = new LinkedPoint(motion.getMotionState(), motion.getMaxPoint(), motion);
        DirectionFilter.PointObserver observer = new DirectionFilter.PointObserver(point);
        point.setOnPointChangeListener(this);
        pointObservable.put(motion.getMotionState(), observer);

        if (pointObservable.size() > 1) {
            List<State.MotionState> keyList = pointObservable.getKeys();
            pointObservable.get(keyList.get(0)).getPoint().setLinkPoint(pointObservable.get(keyList.get(1)).getPoint());
            pointObservable.get(keyList.get(1)).getPoint().setLinkPoint(pointObservable.get(keyList.get(0)).getPoint());
        }
    }

    @Override
    public boolean onDrag(SingleTouchEvent event) {
        if (pointObservable.size() > 0) {
            float motionDistance = this.motionDistance.get(event);
            if (motionDistance != 0) {
                if (state == State.MotionState.NONE) {
                    for (DirectionFilter.PointObserver observer : pointObservable.getValues()) {
                        if (observer.getPoint().isLikeOrientation(motionDistance)) {
                            onPointChange(state, observer.getPoint().getState());
                            observer.getPoint().setPoint(motionDistance);
                            return true;
                        }
                    }
                } else {
                    if (pointObservable.containsKey(state)) {
                        LinkedPoint point = pointObservable.get(state).getPoint();
                        point.setPoint(motionDistance);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    */
}
