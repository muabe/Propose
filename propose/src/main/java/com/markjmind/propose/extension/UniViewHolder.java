package com.markjmind.propose.extension;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class UniViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder{
    public T binder;

    public UniViewHolder(@NotNull ViewDataBinding binder){
        super(binder.getRoot());
        this.binder = (T)binder;
    }

    public void onPre(){
        
    }
}
