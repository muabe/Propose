package com.muabe.propose.guesture;

import com.muabe.propose.touch.detector.single.SingleTouchEvent;

public class UpGesture extends GesturePlugin {
    @Override
    public float getPointValue(float distance) {
        return distance;
    }

    @Override
    public float distancePriority(SingleTouchEvent event) {
        return event.getY()-event.getPreY();
    }
}
