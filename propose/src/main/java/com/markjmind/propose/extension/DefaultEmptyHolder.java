package com.markjmind.propose.extension;

import android.view.View;

import androidx.databinding.ViewDataBinding;


public class DefaultEmptyHolder extends UniViewHolder<String, ViewDataBinding>{

    public DefaultEmptyHolder(View view) {
        super(view);
    }

    void initLayout(View view){
//        binder.uniDefaultEmptyHolderLayout.addView(view);
    }
}
