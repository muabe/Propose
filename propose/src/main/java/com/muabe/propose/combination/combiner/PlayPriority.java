package com.muabe.propose.combination.combiner;


import com.muabe.propose.combination.Priority;

public class PlayPriority implements Priority<Float> {
    private int priority = 0;
    private float minRatio; //시작 ratio
    private float maxRatio; //종료 ratio
    private float ratioRange; //시작과 종료를 뺀 ratio 범위


//    public void setPoint(Point point){
//        this.point = point;
//    }

    public void setRatioRange(float minRatio, float maxRatio){
        this.minRatio = minRatio;
        this.maxRatio = maxRatio;
        this.ratioRange = maxRatio - minRatio;
    }

    float getPlayRatio(float ratio){
        float realRatio = (ratio - minRatio)/ratioRange;
        if(realRatio <= ratioRange){
            return realRatio;
        }
        return -1f;
    }


    @Override
    public float compare(Float ratio) {
//        this.point = param;
//        float ratio = point.getRatio();
        if (minRatio < ratio && ratio <= maxRatio) {
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
