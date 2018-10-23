package com.muabe.propose.guesture;


import com.muabe.propose.action.ActionModule;
import com.muabe.propose.action.ModuleInterface;
import com.muabe.propose.combine.MotionPriority;

import java.lang.reflect.ParameterizedType;

public abstract class GesturePlugin<EventType> extends MotionPriority<EventType> implements ModuleInterface {
    private String name;
    private int priority = 0;

    public GesturePlugin(){
        name = ((Class)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getName();
    }

    @Override
    public String getTypeName(){
        return name;
    }

    public void setPriority(int priority){
        this.priority = priority;
    }

    public abstract void get(EventType eventType);

    public boolean equalsAction(ActionModule actionModule, EventType event){
        boolean isAction = getTypeName().equals(actionModule.getTypeName());
        if(isAction){
            setEvent(event);
        }
        return isAction;
    }
}
