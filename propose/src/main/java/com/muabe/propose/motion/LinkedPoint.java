package com.muabe.propose.motion;

import com.muabe.propose.State;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 */

public class LinkedPoint extends Point {
    private Point linkPoint;
    private OnPointChangeListener onPointChangeListener;

    public interface OnPointChangeListener{
        void onPointChange(State.MotionState preState, State.MotionState currState);
    }

    public LinkedPoint(State.MotionState motionState, float maxPoint, OnPointListener onPointListener) {
        super(motionState, maxPoint, onPointListener);
    }

    public void setLinkPoint(Point linkPoint) {
        this.linkPoint = linkPoint;
    }

    @Override
    protected void onMin(float currentPoint, float distance) {
        super.onMin(currentPoint, distance);
        if(linkPoint!=null){
            if(onPointChangeListener!=null){
                onPointChangeListener.onPointChange(getState(), linkPoint.getState());
            }
            linkPoint.setPoint(distance);
        }
    }

    public void setOnPointChangeListener(OnPointChangeListener onPointChangeListener) {
        this.onPointChangeListener = onPointChangeListener;
    }
}
