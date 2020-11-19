package com.markjmind.propose.animation;

import android.animation.ValueAnimator;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

public class PinchZoomAnimation extends ValueAnimator implements ValueAnimator.AnimatorUpdateListener {
    private View touchView;
        private ScaleGestureDetector detector;
    private long duration = 1000;
    private float density;
    private float maxPinch;

    public PinchZoomAnimation(View touchView){
        init();
        density = touchView.getContext().getResources().getDisplayMetrics().density;
        maxPinch = 100*density;
    }

    public PinchZoomAnimation init(){
        detector = new ScaleGestureDetector(touchView.getContext(), new PinchDetector(this));
        setPinchView(touchView);
        setFloatValues(0,1f);
        return this;
    }

    public PinchZoomAnimation setDuration(long duration){
        this.duration = duration;
        super.setDuration(duration);
        return this;
    }

    public void setProgress(long time){
        if(time > duration){
            time = duration;
        }else if(time < 0){
            time = 0;
        }
        super.setCurrentPlayTime(time);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {


    }

    public float getDensity(){
        return this.density;
    }

    public float getMaxPinch(){
        return maxPinch;
    }

    public PinchZoomAnimation setMaxPinch(float dp){
        maxPinch = dp*density;
        return this;
    }

    public void setPinchView(View view){
        this.touchView = view;
        touchView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return detector.onTouchEvent(event);
            }
        });
    }

    class PinchDetector extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        PinchZoomAnimation animation;
        float total = 0f;
        float totalX = 0f;
        float totalY = 0f;
        float preX = 0f;
        float preY = 0f;

        public PinchDetector(PinchZoomAnimation animation){
            this.animation = animation;
        }
        public boolean onScale(ScaleGestureDetector scaleGestureDetector){
            float x = scaleGestureDetector.getCurrentSpanX() - preX;
            float y = scaleGestureDetector.getPreviousSpanY() - preY;
            totalX += x;
            totalY += y;
            total = totalX + totalY;
            if(total < 0f){
                total = 0f;
            }else if(total > maxPinch){
                total = maxPinch;
            }
            animation.setCurrentPlayTime((long)(animation.getDuration()*(total/maxPinch)));
            preX = scaleGestureDetector.getCurrentSpanX();
            preY = scaleGestureDetector.getPreviousSpanY();
            return true;
        }
    }
}