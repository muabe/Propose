package com.muabe.propose.guesture;

import android.util.Log;

import com.muabe.propose.touch.detector.single.SingleTouchEvent;


public abstract class BaseGesture {
    private enum State{
        NONE,
        MIN,
        BETWEEN,
        MAX
    }
    private State state;
    protected Point point;

    public void init(Point point){
        this.point = point;
        state = State.NONE;
    }
    public abstract boolean gesture(SingleTouchEvent touchEvent, Point point);
    public abstract float getPositionToDistance(SingleTouchEvent touchEvent, float position);
    public abstract float getDistanceToPosition(SingleTouchEvent touchEvent, float distance);
    public abstract float getPoint(SingleTouchEvent touchEvent, Point point);

    public void setPoint(float point){
        if(point >= this.point.getMax()) {
            if(state.equals(State.MAX)) {
                setState(State.NONE);
            }else{
                setState(State.MAX);
                this.point.set(point);
            }

        }else if(point <= this.point.getMin()){
            if(state.equals(State.MIN)){
                setState(State.NONE);
            }else{
                setState(State.MIN);
                this.point.set(point);
            }
        }else{
            setState(State.BETWEEN);
            this.point.set(point);
        }
    }


    public boolean isSwitch(){
        boolean isSwitch = state.equals(State.MAX) || state.equals(State.MIN);
        if(isSwitch){
            Log.e("ddd","state:"+state);
        }
        return isSwitch;
    }

    public boolean isGesture(SingleTouchEvent touchEvent){
//        boolean isGuesture = gesture(touchEvent, point);
//        return state.equals(State.BETWEEN);
        return false;
    }

    private boolean setState(State state){
        if(!this.state.equals(state)){
            this.state = state;
            return true;
        }
        if(state.equals(State.BETWEEN)){
            return true;
        }
        return false;
    }
}
