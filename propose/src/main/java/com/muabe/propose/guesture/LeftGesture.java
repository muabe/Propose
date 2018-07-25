package com.muabe.propose.guesture;

import com.muabe.propose.touch.detector.single.SingleTouchEvent;

public class LeftGesture extends GesturePlugin {
    @Override
    public float increaseDistance(SingleTouchEvent event) {
        return -(event.getX()-event.getPreX());
    }

    @Override
    public float getPointValue(float distance) {
        return distance;
    }
}
