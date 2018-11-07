package com.muabe.propose.guesture;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong - Oh)
 * @email markjmind@gmail.com
 * @since 2018-11-07
 */
public class Point {
    protected float point = 0f;
    private float minPoint = 0f;
    private float maxPoint = 0f;

    Point(float minPoint, float maxPoint){
        this.minPoint = minPoint;
        this.maxPoint = maxPoint;
    }

    public float value(){
        return point;
    }

    public float getMinPoint(){
        return minPoint;
    }
    public boolean isMinPoint(){
        return point <= minPoint;
    }

    public float getMaxPoint(){
        return maxPoint;
    }
    public boolean isMaxPoint(){
        return point >= maxPoint;
    }

    public boolean updatePoint(float increase){
        if(point + increase <= minPoint ){
            if(point == minPoint){
                return false;
            }
            point = minPoint;
        }else if(point + increase >= maxPoint){
            if(point == maxPoint){
                return false;
            }
            point = maxPoint;
        }else{
            point +=  increase;
        }
        return true;
    }
}
