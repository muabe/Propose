package com.markjmind.propose.animation;

import android.animation.ValueAnimator;

import com.airbnb.lottie.LottieAnimationView;

public class LottieAnimation extends ValueAnimator implements ValueAnimator.AnimatorUpdateListener {
    private LottieAnimationView lottieAnimationView;
    private int count;
    float start;
    float end;

    LottieAnimation(LottieAnimationView lottieAnimationView){
        this(lottieAnimationView, 0f, 1f);
    }

    LottieAnimation(LottieAnimationView lottieAnimationView, float start, float end){
        this.start = start;
        this.end = end;
        this.lottieAnimationView = lottieAnimationView;
        this.setFloatValues(0f, 1f);
        count = lottieAnimationView.getRepeatCount();
        addUpdateListener(this);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        float value = (float)animation.getAnimatedValue();
        float progresss = 0f;
        if(value == 0f){
            progresss = start;
        }else if(value != 1f) {
            value = value * count;
            if (value > 1f) {
                value = value - (int)value;
            }
            progresss = value*(end - start)+start;
        }else{
            progresss = end;
        }
        lottieAnimationView.setProgress(progresss);
    }
}
