package com.muabe.propose.guesture;

import com.muabe.propose.touch.detector.single.SingleTouchEvent;

public class RightGesture extends BaseGesture{
    Score score;

    public void setScore(Score score){
        this.score = score;
    }

    public boolean gesture(SingleTouchEvent touchEvent){


        return isGesture(touchEvent);
    }

    private boolean isGesture(SingleTouchEvent touchEvent){
        return (touchEvent.getX() - touchEvent.getFirstX() > 0f);
    }


}
