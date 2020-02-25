package com.muabe.propose.player;


import com.muabe.propose.combination.Priority;

class PlayPriority implements Priority<Float> {
    private int priority = 0;
    private float currentRatio = 0f;
    private float startRatio = 0f; //시작 ratio
    private float endRatio = 0f; //종료 ratio
    private float ratioRange = 0f; //시작과 종료를 뺀 ratio 범위

    void setRatioRange(float minRatio, float maxRatio){
        this.startRatio = minRatio;
        this.endRatio = maxRatio;
        this.ratioRange = maxRatio - minRatio;
    }

    void setCurrentRatio(float currentRatio){
        this.currentRatio = currentRatio;
    }

    public float getCurrentRatio() {
        return currentRatio;
    }

    float getRatio(){
        return ratioRange;
    }

    float getStartRatio(){
        return startRatio;
    }

    float getEndRatio(){
        return endRatio;
    }

    @Override
    public float compare(Float ratio) {
        if (startRatio < ratio && ratio <= endRatio) {
            return 1f;
        }
        return 0f;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority){
        this.priority = priority;
    }
}
