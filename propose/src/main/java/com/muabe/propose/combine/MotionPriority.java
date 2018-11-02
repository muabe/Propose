package com.muabe.propose.combine;


import java.lang.reflect.ParameterizedType;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong - Oh)
 * @email markjmind@gmail.com
 * @since 2018-10-01
 */
public abstract class MotionPriority<EventType> implements Priority<EventType> {
    private int priority = 0;
    private String name;

    public MotionPriority(){
        name = ((Class)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getName();
    }

    public abstract float motionCompare(EventType event);

    public String getTypeName(){
        return name;
    }

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
