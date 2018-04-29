package com.muabe.propose.touch.detector.single;

import android.view.MotionEvent;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 */

public class SingleMotionEvent {
    private MotionEvent firstDragEvent;
    private MotionEvent motionEvent;
    private float dragX;
    private float dragY;

    void setMotionEvent(MotionEvent event){
        this.motionEvent = event;
    }

    public MotionEvent getMotionEvent(){
        return this.motionEvent;
    }

    void setDragProperty(MotionEvent firstDragEvent, float distanceX, float distanceY){
        this.firstDragEvent = firstDragEvent;
        this.dragX = distanceX;
        this.dragY = distanceY;
    }

    void clearDragProperty(){
        this.firstDragEvent = null;
        this.dragX = 0f;
        this.dragY = 0f;
    }

    public float getDragX(){
        return dragX;
    }

    public float getDragY(){
        return dragY;
    }

    public float getTotalX(){
        return motionEvent.getX() - firstDragEvent.getX();
    }

    public float getTotalY(){
        return motionEvent.getY() - firstDragEvent.getY();
    }

}
