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
    private SingleMotionEvent singleEvent;
    private SingleGestureDetector gestureDetector;
    private OnTouchDetectListener listener;


    public SingleDetector(Context context, OnTouchDetectListener listener){
        singleEvent = new SingleMotionEvent();
        gestureDetector = new SingleGestureDetector(context, this);
        this.listener = listener;
    }

    public boolean onTouchEvent(MotionEvent e){
        singleEvent.setMotionEvent(e);
        return gestureDetector.onTouchEvent(e);
    }


    @Override
    public boolean onDown(MotionEvent e) {
        singleEvent.clearDragProperty();
        return listener.onDown(singleEvent);
    }

    boolean onUp(MotionEvent e){
        singleEvent.setMotionEvent(e);
        return listener.onUp(singleEvent);
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        singleEvent.setDragProperty(e1, distanceX*-1, distanceY*-1);
        return listener.onDrag(singleEvent);
    }

    @Override
    public void onShowPress(MotionEvent e) {
        singleEvent.setMotionEvent(e);
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        singleEvent.setMotionEvent(e);
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        singleEvent.setMotionEvent(e);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        singleEvent.setMotionEvent(e2);
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
