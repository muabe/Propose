package com.markjmind.propose.animation;

import android.animation.ValueAnimator;

import com.airbnb.lottie.LottieAnimationView;

public class LottieAnimation extends ValueAnimator implements ValueAnimator.AnimatorUpdateListener {
    private LottieAnimationView lottieAnimationView;
    private int count;

    LottieAnimation(final LottieAnimationView lottieAnimationView){
        this.lottieAnimationView = lottieAnimationView;
        this.setFloatValues(0f, 1f);
        count = lottieAnimationView.getRepeatCount();
        addUpdateListener(this);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        float value = (float)animation.getAnimatedValue();
        if(value != 1f) {
            value = value * count;
            if (value > 1f) {
                value = value - (int)value;
            }
        }
        lottieAnimationView.setProgress(value);
    }
}
