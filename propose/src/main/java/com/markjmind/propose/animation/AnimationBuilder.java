package com.markjmind.propose.animation;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.util.Property;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;

public class AnimationBuilder{
    View view;
    ValueAnimator animator;
    long duration = 500;
    public AnimationBuilder(View view, long duration){
        this.view = view;
        this.duration = duration;
    }

    public static AnimationBuilder create(View tagetView, long duration){
        return new AnimationBuilder(tagetView, duration);
    }

    public static AnimationBuilder create(View tagetView){
        return new AnimationBuilder(tagetView, 500);
    }

    public AnimationBuilder property(Property<View, Float> property, float value){
        animator = ObjectAnimator.ofFloat(view, property, value);
        initAnimatorAttr();
        return this;
    }

    public AnimationBuilder trasX(float x){
        return property(View.TRANSLATION_X, x);
    }

    public AnimationBuilder lottie(){
        animator = new LottieAnimation((LottieAnimationView)view);
        initAnimatorAttr();
        return this;
    }

    public ValueAnimator getValueAnimator() {
        return animator;
    }

    private void initAnimatorAttr(){
        animator.setDuration(duration);
        animator.setInterpolator(null);
    }
}
