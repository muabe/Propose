package com.muabe.propose.guesture;

import com.muabe.propose.touch.detector.single.SingleTouchEvent;

public class RightGesture extends BaseGesture{

    @Override
    public void gesture(SingleTouchEvent touchEvent){
        float currScore = getPosition()+touchEvent.getDragX();
        setPosition(currScore);
    }
}
