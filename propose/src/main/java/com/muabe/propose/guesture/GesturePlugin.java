package com.muabe.propose.guesture;

import com.muabe.propose.OnPlayListener;
import com.muabe.propose.touch.detector.single.SingleTouchEvent;

public abstract class GesturePlugin {
    private Point point;
    private OnPlayListener playListener;

    public abstract float increaseDistance(SingleTouchEvent event);

    public abstract float getPointValue(float distance);

    public void init(Point point){
        this.point = point;
    }

    public Point getPoint(){
        return point;
    }
}
