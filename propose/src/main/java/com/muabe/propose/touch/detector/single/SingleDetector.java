package com.muabe.propose.touch.detector.single;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.muabe.propose.touch.detector.OnTouchDetectListener;

/**
 * <br>捲土重來<br>
 * 외부에서 사용하면 안됌(java9에서 모듈할 예정)
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 */

public class SingleDetector implements GestureDetector.OnGestureListener{
    private SingleTouchEvent singleTouchEvent;
    private SingleGestureDetector gestureDetector;
    private OnTouchDetectListener listener;


    public SingleDetector(Context context, OnTouchDetectListener listener){
        singleTouchEvent = new SingleTouchEvent();
        gestureDetector = new SingleGestureDetector(context, this);
        this.listener = listener;
    }

    public boolean onTouchEvent(MotionEvent e){
        singleTouchEvent.setMotionEvent(e);
        return gestureDetector.onTouchEvent(e);
    }


    @Override
    public boolean onDown(MotionEvent e) {
        singleTouchEvent.clearDragProperty();
        return listener.onDown(singleTouchEvent);
    }

    boolean onUp(MotionEvent e){
        singleTouchEvent.setMotionEvent(e);
        return listener.onUp(singleTouchEvent);
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        singleTouchEvent.setDragProperty(e1, distanceX*-1, distanceY*-1);
        return listener.onDrag(singleTouchEvent);
    }

    @Override
    public void onShowPress(MotionEvent e) {
        singleTouchEvent.setMotionEvent(e);
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        singleTouchEvent.setMotionEvent(e);
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        singleTouchEvent.setMotionEvent(e);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        singleTouchEvent.setMotionEvent(e2);
        return false;
    }


    private class SingleGestureDetector extends GestureDetector {
        SingleDetector adapter;
        public SingleGestureDetector(Context context, SingleDetector adapter) {
            super(context, adapter);
            this.adapter = adapter;
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            boolean result = super.onTouchEvent(event);
            if (event.getAction() == MotionEvent.ACTION_UP) {
                result = adapter.onUp(event) || result;
            }
            return result;
        }

    }
}
