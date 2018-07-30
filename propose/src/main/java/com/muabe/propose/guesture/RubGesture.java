package com.muabe.propose.guesture;

import com.muabe.propose.touch.detector.single.SingleTouchEvent;

public class RubGesture extends GesturePlugin {
    @Override
    public float getPointValue(float distance) {
        return Math.abs(distance);
    }

    @Override
    public float distancePriority(SingleTouchEvent event) {
        return (float)Math.sqrt(Math.pow(event.getX(), 2) + Math.pow(event.getY(), 2));
    }
}
