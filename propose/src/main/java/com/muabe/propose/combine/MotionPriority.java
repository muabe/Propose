package com.muabe.propose.combine;


/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong - Oh)
 * @email markjmind@gmail.com
 * @since 2018-10-01
 */
public abstract class MotionPriority<EventType> implements Priority<EventType> {
    private int priority = 0;

    protected abstract float motionCompare(EventType event);

    @Override
    public float compare(EventType param) {
        float compareValue = motionCompare(param);
        if(compareValue < 0f){
            compareValue = 0f;
        }
        return compareValue;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority){
        this.priority = priority;
    }


}
