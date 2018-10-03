package com.muabe.propose.combine;

import com.muabe.propose.guesture.Point;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong - Oh)
 * @email markjmind@gmail.com
 * @since 2018-10-01
 */
public abstract class MotionPriority implements ScanPriority<Point> {
    private float gauge = 0f;
    private float min = 0f;
    private float max = 0f;

    public float increase(){
        return 0f;
    }

    public float getGauge(){
        return gauge;
    }

    public float resetGauge(){
        float newGauge = getGauge()+increase();
        if(newGauge < min){
            newGauge = min;
        }else if(newGauge > max){
            newGauge = max;
        }
        gauge = newGauge;
        return gauge;
    }

    @Override
    public float priority(Point point) {
        return 0;
    }

    abstract public String getType();
}
