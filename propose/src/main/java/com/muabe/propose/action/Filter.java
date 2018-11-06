package com.muabe.propose.action;

import com.muabe.propose.guesture.GesturePlugin;

import java.lang.reflect.ParameterizedType;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong - Oh)
 * @email markjmind@gmail.com
 * @since 2018-11-06
 */
public class Filter {
    private String name;

    public Filter(Class<? extends GesturePlugin> gesturePlugin){
        ParameterizedType type = (ParameterizedType)gesturePlugin.getClass().getGenericSuperclass();
        name = ((Class)type.getActualTypeArguments()[0]).getName();
    }

    public String getTypeName(){
        return this.name;
    }
}
