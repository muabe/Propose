package com.muabe.propose.touch.detector.multi;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import com.muabe.propose.touch.detector.OnTouchDetectListener;

/**
 * <br>捲土重來<br>
 * 외부에서 사용하면 안됌(java9에서 모듈 사용할 예정)
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 */

public class MultiDetector implements ScaleGestureDetector.OnScaleGestureListener{
    private MultiTouchEvent multiEvent;
    private MultiGestureDetector gestureDetector;
    private OnTouchDetectListener listener;

    public MultiDetector(Context context, OnTouchDetectListener listener){
        multiEvent = new MultiTouchEvent();
        gestureDetector = new MultiGestureDetector(context, this);
        this.listener = listener;
    }

    public boolean onTouchEvent(MotionEvent e){
        multiEvent.setMotionEvent(e);
        return gestureDetector.onTouchEvent(e);
    }


    boolean onMultiUp(){
        return listener.onMultiUp(multiEvent);
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        multiEvent.initMultiTouch(detector);
        return listener.onMulitBegin(multiEvent);
    }


    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        multiEvent.setMultiDragProperty(detector);
        return listener.onMultiDrag(multiEvent);
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        listener.onMultiEnd(multiEvent);
        multiEvent.initMultiTouch(null);
    }

    private class MultiGestureDetector extends ScaleGestureDetector {
        private MultiDetector adapter;

        public MultiGestureDetector(Context context, MultiDetector adapter) {
            super(context, adapter);
            this.adapter = adapter;
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if(event.getPointerCount()<=1){
                return false;
            }
            boolean result = super.onTouchEvent(event);
            if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_UP) {
                result = adapter.onMultiUp() || result;
            }
            return result;
        }
    }
}
