package com.muabe.propose.combination.combiner;

import java.lang.reflect.ParameterizedType;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong - Oh)
 * @email markjmind@gmail.com
 * @since 2018-11-06
 */
class EventFilter {
    private String name;

    EventFilter(ActionPlugBridge<?> actionPriority){
        ParameterizedType type = (ParameterizedType)actionPriority.getClass().getGenericSuperclass();
        name = ((Class)type.getActualTypeArguments()[0]).getName();
    }

    boolean filter(Object event){
        return name.equals(event.getClass().getName());
    }
}
