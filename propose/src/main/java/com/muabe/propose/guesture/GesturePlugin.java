package com.muabe.propose.guesture;


import com.muabe.propose.action.ModuleName;

import java.lang.reflect.ParameterizedType;

public abstract class GesturePlugin<EventType> implements ModuleName {
    private String name;

    public GesturePlugin(){
        name = ((Class)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getName();
    }

    @Override
    public String getTypeName(){
        return name;
    }

    public abstract void get(EventType eventType);
}
