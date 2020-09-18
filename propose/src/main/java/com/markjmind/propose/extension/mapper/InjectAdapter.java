package com.markjmind.propose.extension.mapper;

import android.content.Context;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class InjectAdapter<T extends Annotation>{
    private Store<?> result = new Store();
    private Mapper mapper;
    private InjectListener<T, Class> classInjectListener;
    private InjectListener<T, Field> fieldInjectListener;
    private InjectListener<T, Method> methodInjectListener;


    public InjectAdapter(){
        classInjectListener = new InjectListener<T, Class>() {
            @Override
            public void inject(T annotation, Class element, Object targetObject) {
                injectClass(annotation, element, targetObject);
        }

            @Override
            public Class<T> getAnnotationType() {
                return InjectAdapter.this.getAnnotationType();
            }
        };

        fieldInjectListener = new InjectListener<T, Field>() {
            @Override
            public void inject(T annotation, Field element, Object targetObject) {
                injectField(annotation, element, targetObject);
            }

            @Override
            public Class<T> getAnnotationType() {
                return InjectAdapter.this.getAnnotationType();
            }
        };

        methodInjectListener = new InjectListener<T, Method>() {
            @Override
            public void inject(T annotation, Method element, Object targetObject) {
                injectMethod(annotation, element, targetObject);
            }

            @Override
            public Class<T> getAnnotationType() {
                return InjectAdapter.this.getAnnotationType();
            }
        };
    }
    public abstract Class<T> getAnnotationType();
    public abstract void injectClass(T annotation, Class targetClass, Object targetObject);
    public abstract void injectField(T annotation, Field field, Object targetObject);
    public abstract void injectMethod(T annotation, Method method, Object targetObject);

    public InjectListener<T, Class> getClassInjector(){
        return classInjectListener;
    }

    public InjectListener<T, Field> getFieldInjector(){
        return fieldInjectListener;
    }

    public InjectListener<T, Method> getMethodInjector(){
        return methodInjectListener;
    }

    void setMapper(Mapper mapper){
        this.mapper = mapper;
    }

    public Context getContext(){
        return mapper.context;
    }

    public Object getObject(){
        return mapper.targetObject;
    }

    public void addViewCache(int id, View view){
        mapper.viewHash.put(id, view);
    }

    public View findViewById(int id){
        if(mapper.viewHash.containsKey(id)){
            return mapper.viewHash.get(id);
        }
        return mapper.finder.findViewById(id);
    }

    public int getIdentifier(String name, String defType){
        int resID = mapper.context.getResources().getIdentifier(name, defType, mapper.context.getPackageName());
        if(resID!=-1){
            return resID;
        }else{
            throw new RuntimeException("getIdentifier Exception");
        }
    }

    public void setField(Field field, Object value){
        try {
            field.setAccessible(true);
            field.set(getObject(), value);
        }catch (IllegalAccessException e) {
            throw new RuntimeException("getIdentifier Exception");
        }
    }
}
