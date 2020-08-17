package com.markjmind.propose.extension.binder;

import android.view.View;

import androidx.databinding.BindingAdapter;

import java.util.List;

public class ViewBindingAdapter {
    @BindingAdapter(value = {"view:visible"})
    public static void view_visible (View view, boolean visible){
        if(visible){
            view.setVisibility(View.VISIBLE);
        }else{
            view.setVisibility(View.GONE);
        }
    }

    @BindingAdapter(value = {"view:invisible"})
    public static void view_invisible(View view, boolean visible){
        if(visible){
            view.setVisibility(View.VISIBLE);
        }else{
            view.setVisibility(View.INVISIBLE);
        }
    }

    @BindingAdapter(value = {"view:visible_size"})
    public static void view_visible_size(View view, List<?> list){
        if(list != null && list.size() > 0){
            view.setVisibility(View.VISIBLE);
        }else{
            view.setVisibility(View.GONE);
        }
    }
}
