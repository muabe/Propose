package com.muabe.propose.guesture;

import com.muabe.propose.touch.detector.single.SingleTouchEvent;

public class RightGesture extends BaseGesture{

    @Override
    public boolean gesture(SingleTouchEvent touchEvent, Point point){
        return touchEvent.getDragX() > 0;
    }

    @Override
    public float getPositionToDistance(SingleTouchEvent touchEvent, float position){
        return position;
    }

    @Override
    public float getDistanceToPosition(SingleTouchEvent touchEvent, float distance){
        return distance;
    }

    @Override
    public float getPoint(SingleTouchEvent touchEvent, Point point){
//        float distance = touchEvent.getDragX();
//        float thisPosition = getDistanceToPosition(null, distance);
        return touchEvent.getDragX();
    }
}
