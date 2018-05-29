package com.muabe.propose.guesture;

public class Score {
    private float max;
    private float min;
    private float score;

    public void init(float min, float max, float score){
        setMin(min);
        setMax(max);
        set(score);
    }

    public void set(float scroe){
        if(scroe<min){
            scroe = min;
        }
        this.score = scroe;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getScore() {
        return score;
    }
}
