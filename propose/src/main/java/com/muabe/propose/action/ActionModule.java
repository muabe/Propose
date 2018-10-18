package com.muabe.propose.action;

import com.muabe.propose.Propose;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong - Oh)
 * @email markjmind@gmail.com
 * @since 2018-10-15
 */
public abstract class ActionModule<EventType> implements ModuleInterface {
    private Propose propose;
    private String eventName;

    public String getTypeName(){
        return eventName;
    }

    public void bind(Propose propose){
        this.propose = propose;
    }


    protected boolean callScan(EventType event){
        if(propose!=null) {
            if(eventName == null){
                this.eventName = event.getClass().getName();
            }
            return propose.callScan(this, event);
        }else{
            return false;
        }
    }
}
