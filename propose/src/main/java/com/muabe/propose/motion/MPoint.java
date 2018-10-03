package com.muabe.propose.motion;

import com.muabe.propose.State;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 */

public class MPoint {
    public interface OnPointListener {
        void onPoint(float prePoint, float point);
        void onMin(float currentPoint, float distance);
        void onMax(float currentPoint, float distance);
    }

    private float point, minPoint, maxPoint;
    private OnPointListener onPointListener;
    protected State.MotionState state;

    public MPoint(State.MotionState state, float maxPoint, OnPointListener onPointListener) {
        this.maxPoint = maxPoint;
        this.state = state;
        this.minPoint = 0f;
        this.point = minPoint;
        this.onPointListener = onPointListener;
    }

    public boolean isLikeOrientation(float distance){
        return (distance < 0 && state.getOrientation() <0) || (distance > 0 && state.getOrientation() > 0);
    }

    public void setPoint(float distance) {
        float currPoint = this.point+distance*state.getOrientation();
        if (currPoint <= minPoint) { // 이미 최소 포인트가 아니라면
            if(this.point != minPoint) {
                onPoint(this.point, minPoint);
                this.point = minPoint;
                onMin(currPoint, minPoint - currPoint);
            }
        } else if (currPoint >= maxPoint) { // 이미 최대 포인트가 아니라면
            if(this.point != maxPoint) {
                onPoint(this.point, maxPoint);
                this.point = maxPoint;
                onMax(currPoint, currPoint - maxPoint);
            }
        } else {
            onPoint(this.point, currPoint);
            this.point = currPoint;
        }
    }

    public State.MotionState getState() {
        return state;
    }

    protected void onPoint(float prePoint, float point){
        onPointListener.onPoint(prePoint, point);
    }

    protected void onMin(float currentPoint, float distance){
        onPointListener.onMin(currentPoint, distance);
    }
    protected void onMax(float currentPoint, float distance){
        onPointListener.onMax(currentPoint, distance);
    }


    public void setMaxPoint(float maxPoint){
        this.maxPoint = maxPoint;
    }

    public float getMaxPoint(){
        return maxPoint;
    }

}
