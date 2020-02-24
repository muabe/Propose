package com.muabe.propose.combination.combiner;


import com.muabe.propose.combination.Priority;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong - Oh)
 * @email markjmind@gmail.com
 * @since 2018-10-01
 */
public abstract class ActionPlugBridge<EventType> implements Priority<EventType> {
    private int priority = 0;
    private EventFilter eventFilter;

    protected ActionPlugBridge(){
//        eventFilter = new EventFilter(this);
    }

    protected abstract float motionCompare(EventType event);
    public abstract Point getPoint();

    @Override
    public float compare(EventType param) {
        if (filter(param)) {
            float compareValue = motionCompare(param);
            if(compareValue < 0f){
                compareValue = 0f;
            }
            return compareValue;
        } else {
            return getPoint().value();
        }
    }

    @Override
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority){
        this.priority = priority;
    }


    boolean filter(Object event) {
        return eventFilter.filter(event);
    }

}
