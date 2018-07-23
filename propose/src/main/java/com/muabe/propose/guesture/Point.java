package com.muabe.propose.guesture;

import com.muabe.propose.Motion2;

public class Point {
    private float max = Motion2.INFINITY;
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
        if(distance > max){
            distance = max;
        }
        this.score = distance;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }
    public float getMin(){
        return this.min;
    }
    public float get() {
        return score;
    }
}
