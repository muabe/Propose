package com.markjmind.propose.extension;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.markjmind.propose.extension.mapper.ClassInjectAdapter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

import androidx.databinding.ViewDataBinding;

public class HolderTypeAdapter extends ClassInjectAdapter<RecycleHolder> {
    private Class targetClass;
    private View root;
    private LayoutInflater inflater;
    private String typeName;
    private ViewGroup parents;
    private ViewDataBinding vb;

    public HolderTypeAdapter(ViewGroup parents){
        this.inflater = LayoutInflater.from(parents.getContext());
        this.parents = parents;
    }

    @Override
    public Class<RecycleHolder> getAnnotationType() {
        return RecycleHolder.class;
    }

    @Override
    public void injectClass(RecycleHolder annotation, Class targetClass, Object targetObject) {
        try {
            this.targetClass = targetClass;
            typeName = annotation.value();
            Class<?> genericClass = (Class<?>)((ParameterizedType)targetClass.getGenericSuperclass()).getActualTypeArguments()[1];
            Method method = genericClass.getDeclaredMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
            vb = (ViewDataBinding)method.invoke(null, inflater, parents, false);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("HolderTypeAdapter Annotation Exception:1",e);
        }
    }

    public UniViewHolder getBindInstance(int viewType){
        try {
            UniViewHolder holder = (UniViewHolder)targetClass.getConstructor(View.class).newInstance(vb.getRoot());
            holder.setBinder(vb);
            targetClass.getField("binder").set(holder, vb);
            this.root = holder.binder.getRoot();
            holder.setViewType(viewType);
            return holder;
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException | NoSuchFieldException e) {
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
