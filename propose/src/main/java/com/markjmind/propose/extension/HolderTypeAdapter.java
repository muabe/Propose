package com.markjmind.propose.extension;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.ViewDataBinding;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

public class HolderTypeAdapter {
    private Class targetClass;
    private View root;
    private LayoutInflater inflater;
    private String typeName;
    private ViewGroup parents;
    private Object vb;

    public HolderTypeAdapter(ViewGroup parents){
        this.inflater = LayoutInflater.from(parents.getContext());
        this.parents = parents;
    }

    public Class<RecycleHolder> getAnnotationType() {
        return RecycleHolder.class;
    }

    public void injectClass(RecycleHolder annotation, Class targetClass, Object targetObject) {
        try {
            this.targetClass = targetClass;
            typeName = annotation.value();
            Class<?> genericClass = (Class<?>)((ParameterizedType)targetClass.getGenericSuperclass()).getActualTypeArguments()[0];
            Method method = genericClass.getMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
            vb = method.invoke(null, inflater, parents, false);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("HolderTypeAdapter Annotation Exception:1",e);
        }
    }

    public UniViewHolder getInstance(){
        try {
            UniViewHolder holder = (UniViewHolder)targetClass.getConstructor(ViewDataBinding.class).newInstance(vb);
            targetClass.getField("binder").set(holder, vb);
            this.root = holder.binder.getRoot();
            return holder;
        } catch (IllegalAccessException | NoSuchFieldException | NoSuchMethodException | InvocationTargetException | InstantiationException e) {
            throw new RuntimeException("HolderTypeAdapter Annotation Exception:2",e);
        }
    }

    public String getTypeName(){
        return typeName;
    }

    public View getRoot(){
        return root;
    }

}
