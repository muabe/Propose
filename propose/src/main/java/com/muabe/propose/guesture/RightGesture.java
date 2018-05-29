package com.muabe.propose.guesture;

import com.muabe.propose.touch.detector.single.SingleTouchEvent;

public class RightGesture extends BaseGesture{

    @Override
    public boolean gesture(SingleTouchEvent touchEvent){
        float currScore = motionDistance.get()+touchEvent.getDragX();
        if(currScore > 0){
            motionDistance.set(currScore);
            return true;
        }else{
            motionDistance.set(0);
            return false;
        }
    }
}
