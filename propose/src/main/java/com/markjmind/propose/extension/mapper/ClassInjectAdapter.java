package com.markjmind.propose.extension.mapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2016-07-05
 */
public abstract class ClassInjectAdapter<T extends Annotation> extends InjectAdapter<T>{


    public InjectListener<T, Field> getFieldInjector(){
        return null;
    }

    public InjectListener<T, Method> getMethodInjector(){
        return null;
    }

    public void injectField(T annotation, Field field, Object targetObject){}
    public void injectMethod(T annotation, Method method, Object targetObject){}
}
