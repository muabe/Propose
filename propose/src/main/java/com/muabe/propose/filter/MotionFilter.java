package com.muabe.propose.filter;

import com.muabe.propose.Propose;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong - Oh)
 * @email markjmind@gmail.com
 * @since 2018-10-15
 */
public abstract class MotionFilter<EventType> {
    private EventType event;
    private Propose propose;

    abstract public String getTypeName();

    public void bind(Propose propose){
        this.propose = propose;
    }

    public void setEvent(EventType event){
        this.event = event;
    }

    public EventType getEvent(){
        return event;
    }

    protected void callScan(){
        propose.callScan(event);
    }
}
