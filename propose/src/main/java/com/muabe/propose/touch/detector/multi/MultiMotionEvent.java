package com.muabe.propose.touch.detector.multi;

import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 */

public class MultiMotionEvent{
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

    float preFocusX, currFocusX, preFocusY, currFocusY;

    public void initMultiTouch(ScaleGestureDetector scaleGestureDetector) {
        if(scaleGestureDetector != null) {
            currFocusX = scaleGestureDetector.getFocusX();
            currFocusY = scaleGestureDetector.getFocusY();
            setMultiDragProperty(scaleGestureDetector);
        }else{
            preFocusX = 0f;
            preFocusY = 0f;
            currFocusX = 0f;
            currFocusY = 0f;
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

    }

    public float getDragX(){
        return currFocusX - preFocusX;
    }

    public float getDragY(){
        return currFocusY - preFocusY;
    }
}
