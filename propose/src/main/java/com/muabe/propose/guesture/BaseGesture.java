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
    protected MotionDistance motionDistance;

    public void init(MotionDistance motionDistance){
        this.motionDistance = motionDistance;
        state = State.NONE;
    }
    public abstract void gesture(SingleTouchEvent touchEvent);

    public void setPosition(float position){
        if(position >= motionDistance.getMax()) {
            if(state.equals(State.MAX)) {
                setState(State.NONE);
            }else{
                setState(State.MAX);
                motionDistance.set(position);
            }

        }else if(position <= motionDistance.getMin()){
            if(state.equals(State.MIN)){
                setState(State.NONE);
            }else{
                setState(State.MIN);
                motionDistance.set(position);
            }
        }else{
            setState(State.BETWEEN);
            motionDistance.set(position);
        }

//        if(position >= motionDistance.getMax()) {
//            if(!setState(State.MAX)){
//                setState(State.NONE);
//                return;
//            }
//        }else if(position <= motionDistance.getMin()){
//            if(!setState(State.MIN)){
//                setState(State.NONE);
//                return;
//            }
//        }else{
//            setState(State.BETWEEN);
//        }
//        motionDistance.set(position);
    }
    public float getPosition(){
        return motionDistance.get();
    }

    public boolean isSwitch(){
        boolean isSwitch = state.equals(State.MAX) || state.equals(State.MIN);
        if(isSwitch){
            Log.e("ddd","state:"+state);
        }
        return isSwitch;
    }

    public boolean isGesture(SingleTouchEvent touchEvent){
        gesture(touchEvent);
        return state.equals(State.BETWEEN);
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
