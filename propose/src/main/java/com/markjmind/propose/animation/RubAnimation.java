package com.markjmind.propose.animation;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class RubAnimation implements GestureDetector.OnGestureListener{
    private View targetView;
    private OnRubListener listener;
    float minRub = 0f;
    float maxRub = 0f;
    float currentRub = 0f;
    interface OnRubListener{
        void onStart();
        void onRub(float rubRatio);
        void onEnd();
    }

    public RubAnimation(View targetView, final OnRubListener listener, float minRub, float maxRub){
        this.targetView = targetView;
        this.setRubListener(listener);
        this.setRunSize(minRub, maxRub);
        targetView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if((action & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP){
                    listener.onEnd();
                    return true;
                }
                return gestureDetector().onTouchEvent(event);
            }
        });
    }

    private GestureDetector gestureDetector(){
        return new GestureDetector(targetView.getContext(), this);
    }

    public RubAnimation setRubListener(OnRubListener listener){
        this.listener = listener;
        return this;
    }

    public RubAnimation setRunSize(float minRub, float maxRub){
        this.minRub = minRub;
        this.minRub = minRub;
        return this;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        currentRub = minRub;
        listener.onStart();
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        currentRub = Math.abs(distanceX) + Math.abs(distanceY);
        if(currentRub > maxRub){
            currentRub = maxRub;
        }
        listener.onRub(currentRub/maxRub);
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
