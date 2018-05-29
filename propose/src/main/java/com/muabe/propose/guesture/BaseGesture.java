package com.muabe.propose.guesture;

import com.muabe.propose.touch.detector.single.SingleTouchEvent;

public abstract class BaseGesture {
    protected MotionDistance motionDistance;

    public void init(MotionDistance motionDistance){
        this.motionDistance = motionDistance;
    }
    public abstract boolean gesture(SingleTouchEvent touchEvent);
}
