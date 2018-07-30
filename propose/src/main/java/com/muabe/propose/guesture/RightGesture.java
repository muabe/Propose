package com.muabe.propose.guesture;

import com.muabe.propose.touch.detector.single.SingleTouchEvent;

public class RightGesture extends GesturePlugin {

    @Override
    public float distancePriority(SingleTouchEvent event) {
        return event.getX()-event.getPreX();
    }

    @Override
    public float getPointValue(float distance) {
        return distance;
    }
}
