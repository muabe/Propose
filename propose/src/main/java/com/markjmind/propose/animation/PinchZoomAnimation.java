package com.markjmind.propose.animation;

import android.animation.ValueAnimator;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

public class PinchZoomAnimation extends ValueAnimator implements ValueAnimator.AnimatorUpdateListener {
    private View touchView;
    private float currentScale = 1f;
    private ScaleGestureDetector detector;
    private long duration = 1000;


    public PinchZoomAnimation(View touchView){
        detector = new ScaleGestureDetector(touchView.getContext(), new PinchDetector());
        setPinchView(touchView);
        setFloatValues(0,1f);
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
        public boolean onScale(ScaleGestureDetector scaleGestureDetector){
            return true;
        }
    }
}