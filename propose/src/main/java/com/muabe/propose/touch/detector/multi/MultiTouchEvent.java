package com.muabe.propose.touch.detector.multi;

import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import com.muabe.propose.touch.detector.TouchEvent;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 */

public class MultiTouchEvent implements TouchEvent {
    private MotionEvent firstDragEvent;
    private MotionEvent preDragEvent;
    private MotionEvent motionEvent;

    private ScaleGestureDetector scaleGestureDetector;

    void setMotionEvent(MotionEvent event){
        this.motionEvent = event;
    }
    public MotionEvent getMotionEvent(){
        return this.motionEvent;
    }

    float preFocusX, currFocusX, preFocusY, currFocusY, preScale;
    float currScale = 1f;
    float temp = 1f;

    public void initMultiTouch(ScaleGestureDetector scaleGestureDetector) {
        if(scaleGestureDetector != null) {
            currFocusX = scaleGestureDetector.getFocusX();
            currFocusY = scaleGestureDetector.getFocusY();
            currScale = 1f;
            setMultiDragProperty(scaleGestureDetector);
            Log.i("dd","scale:"+(temp*scaleGestureDetector.getScaleFactor()));
        }else{
            preFocusX = 0f;
            preFocusY = 0f;
            currFocusX = 0f;
            currFocusY = 0f;
            currScale = 1f;
        }
        this.scaleGestureDetector = scaleGestureDetector;
    }

    public ScaleGestureDetector getScaleGestureDetector() {
        return scaleGestureDetector;
    }

    void setMultiDragProperty(ScaleGestureDetector scaleGestureDetector){
        preFocusX = currFocusX;
        preFocusY = currFocusY;
        currFocusX = scaleGestureDetector.getFocusX();
        currFocusY = scaleGestureDetector.getFocusY();
        currScale = scaleGestureDetector.getScaleFactor();

    }

    public float getDragX(){
        return currFocusX - preFocusX;
    }

    public float getDragY(){
        return currFocusY - preFocusY;
    }

    public float getScale(){
        return currScale;
    }


}
