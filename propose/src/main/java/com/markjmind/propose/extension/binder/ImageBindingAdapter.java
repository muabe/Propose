package com.markjmind.propose.extension.binder;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;


import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;

import java.util.List;

public class ImageBindingAdapter {
    @BindingAdapter(value = {"image:url", "image:placeholder", "image:error"}, requireAll = false)
    public static void image_url(ImageView imageView, String url, Drawable placeholder, Drawable error){
        if(url == null){
            if(placeholder != null){
                imageView.setImageDrawable(placeholder);
            }
            return;
        }
        RequestBuilder<Drawable> builder = Glide.with(imageView.getContext()).load(url);
        if(placeholder != null) {
            builder.placeholder(placeholder);
        }
        if(error != null) {
            builder.error(error);
        }
        builder.into(imageView);
    }

    @BindingAdapter(value = {"image:visible"})
    public static void image_visible(ImageView imageView, boolean visible){
        if(visible){
            imageView.setVisibility(View.VISIBLE);
        }else{
            imageView.setVisibility(View.GONE);
        }
    }

    @BindingAdapter(value = {"image:invisible"})
    public static void image_invisible(ImageView imageView, boolean visible){
        if(visible){
            imageView.setVisibility(View.VISIBLE);
        }else{
            imageView.setVisibility(View.INVISIBLE);
        }
    }

    @BindingAdapter(value = {"image:visible_size"})
    public static void image_visible_size(ImageView imageView, List<?> list){
        if(list != null && list.size() > 0){
            imageView.setVisibility(View.VISIBLE);
        }else{
            imageView.setVisibility(View.GONE);
        }
    }
}
