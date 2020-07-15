package com.markjmind.propose.extension;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public abstract class UniRecyclerAdapter<ItemType> extends RecyclerView.Adapter<UniViewHolder<?, ?>> {
    protected ArrayList<Class> holderList = new ArrayList<>();
    @NotNull
    protected List<ItemType> itemList = new ArrayList<>();
    final static String defaultType = "uni_recycler_default_type";
    protected RecyclerView recyclerView;
    Store paramStore = new Store();

    @NotNull
    protected abstract Class<? extends UniViewHolder<?,?>> getType(ItemType item, int position, List<ItemType> list);

    public UniRecyclerAdapter(@NotNull RecyclerView recyclerView) {
        initRecyclerView(recyclerView);
    }

    public UniRecyclerAdapter initRecyclerView(@NotNull RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        recyclerView.setAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        return this;
    }

    public UniRecyclerAdapter initRecyclerView(@NotNull RecyclerView recyclerView, @NotNull RecyclerView.LayoutManager layoutManager) {
        this.recyclerView = recyclerView;
        recyclerView.setAdapter(this);
        recyclerView.setLayoutManager(layoutManager);
        return this;
    }

    @NotNull
    public RecyclerView getRecyclerView() {
        return this.recyclerView;
    }

    @NonNull
    @Override
    public UniViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return (UniViewHolder) getInstance(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull UniViewHolder holder, int position) {
        holder.setItem(itemList.get(position));
        Mapper mapper = new Mapper(holder.binder.getRoot(), holder);
        holder.param = (Store)paramStore.opt(holder.getClass().toString(), new Store());
        mapper.inject(new ParamAdapter(holder.param), new GetViewAdapter(), new OnClickAdapter(), new OnCheckedChangeAdapter());
        holder.onPre();
    }



    public UniRecyclerAdapter<ItemType> addParam(Class<? extends UniViewHolder> holderClass, String key, Object value){
        Store param = (Store)paramStore.get(holderClass.toString());
        if(param == null){
            param = new Store();
            paramStore.add(holderClass.toString(), param);
        }
        param.add(key, value);
        return this;
    }



    private Object getInstance(ViewGroup parents, int viewType){
        try {
            LayoutInflater inflater = LayoutInflater.from(parents.getContext());
            Class<?> targetClass = holderList.get(viewType);
            Class<?> genericClass = (Class<?>)((ParameterizedType)targetClass.getGenericSuperclass()).getActualTypeArguments()[1];
            Method method = genericClass.getDeclaredMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
            ViewDataBinding vb = (ViewDataBinding)method.invoke(null, inflater, parents, false);

            UniViewHolder holder = (UniViewHolder)targetClass.getConstructor(View.class).newInstance(vb.getRoot());
            holder.setBinder(vb);
            holder.setViewType(viewType);
            return holder;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new RuntimeException("HolderTypeAdapter Annotation Exception:1",e);
        }
    }

    @NotNull
    @Override
    public int getItemViewType(int position) {
        ItemType item = itemList.get(position);
        Class type = getType(item, position, getList());
        if(!holderList.contains(type)){
            holderList.add(type);
        }
        return holderList.indexOf(type);
    }

    @NotNull
    @Override
    public int getItemCount() {
        return itemList.size();
    }


    @NotNull
    public UniRecyclerAdapter setList(@NotNull List list) {
        itemList = list;
        return this;
    }

    @NotNull
    public List<ItemType> getList() {
        return itemList;
    }
}

