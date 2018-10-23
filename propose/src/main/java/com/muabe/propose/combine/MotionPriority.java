package com.muabe.propose.combine;


/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong - Oh)
 * @email markjmind@gmail.com
 * @since 2018-10-01
 */
public abstract class MotionPriority<EventType> implements Priority {
    private float gauge = 0f;
    private float min = 0f;
    private float max = 0f;
    private EventType event;

    public void setEvent(EventType event){
        this.event = event;
    }

    public abstract float increase(EventType event);

    private float increase(){
        return increase(event);
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
    public float compare() {
        return getGauge()+increase();
    }
}
