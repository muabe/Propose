package com.muabe.propose.guesture;

public class MotionDistance {
    private float max;
    private float min = 0;
    private float score;

    public void init(float max, float distance){
        setMax(max);
        set(distance);
    }

    public void set(float distance){
        if(distance < min){
            distance = min;
        }
        if(score > max){
            score = max;
        }
        this.score = distance;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public float get() {
        return score;
    }
}
