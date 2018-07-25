package com.muabe.propose.guesture;

import com.muabe.propose.Motion;

public class Point {
    private float max = Motion.INFINITY;
    private float min = 0;
    private float point;

    public void init(float max){
        setMax(max);
    }

    public void set(float point){
        if(point < min){
            point = min;
        }
        if(point > max){
            point = max;
        }
        this.point = point;
    }

    public void add(float point){
        set(get()+point);
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
        return point;
    }
}
