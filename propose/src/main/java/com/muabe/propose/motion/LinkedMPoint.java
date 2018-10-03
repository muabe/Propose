package com.muabe.propose.motion;

import com.muabe.propose.State;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 */

public class LinkedMPoint extends MPoint {
    private MPoint linkMPoint;
    private OnPointChangeListener onPointChangeListener;

    public interface OnPointChangeListener{
        void onPointChange(State.MotionState preState, State.MotionState currState);
    }

    public LinkedMPoint(State.MotionState motionState, float maxPoint, OnPointListener onPointListener) {
        super(motionState, maxPoint, onPointListener);
    }

    public void setLinkMPoint(MPoint linkMPoint) {
        this.linkMPoint = linkMPoint;
    }

    @Override
    protected void onMin(float currentPoint, float distance) {
        super.onMin(currentPoint, distance);
        if(linkMPoint !=null){
            if(onPointChangeListener!=null){
                onPointChangeListener.onPointChange(getState(), linkMPoint.getState());
            }
            linkMPoint.setPoint(distance);
        }
    }

    public void setOnPointChangeListener(OnPointChangeListener onPointChangeListener) {
        this.onPointChangeListener = onPointChangeListener;
    }
}
