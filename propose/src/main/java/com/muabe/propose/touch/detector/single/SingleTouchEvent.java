package com.muabe.propose.touch.detector.single;

import android.view.MotionEvent;

import com.muabe.propose.touch.detector.TouchEvent;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 */

public class SingleTouchEvent implements TouchEvent {
    private MotionEvent firstMotionEvent;
    private MotionEvent preMotionEvent;
    private MotionEvent motionEvent;
    private float dragX;
    private float dragY;

    void setMotionEvent(MotionEvent event){
        if(motionEvent==null){
            preMotionEvent = event;
        }else{
            preMotionEvent = motionEvent;
        }
        this.motionEvent = event;
    }

    void setDragProperty(MotionEvent firstDragEvent, float distanceX, float distanceY){
        this.firstMotionEvent = firstDragEvent;
        this.dragX = distanceX;
        this.dragY = distanceY;
    }

    void clearDragProperty(){
        this.firstMotionEvent = null;
        this.preMotionEvent = null;
        this.motionEvent = null;
        this.dragX = 0f;
        this.dragY = 0f;
    }

    public MotionEvent getFirstMotionEvent(){
        return firstMotionEvent;
    }

    public MotionEvent getPreMotionEvent() {
        return preMotionEvent;
    }

    public MotionEvent getMotionEvent(){
        return motionEvent;
    }

    public float getDragX(){
        return dragX;
    }

    public float getDragY(){
        return dragY;
    }

    public float getFirstX(){
        return firstMotionEvent.getRawX();
    }

    public float getFirstY(){
        return firstMotionEvent.getRawY();
    }


    public float getPreX(){
        return preMotionEvent.getRawX();
    }

    public float getPreY(){
        return preMotionEvent.getRawY();
    }

    public float getX(){
        return motionEvent.getRawX();
    }

    public float getY(){
        return motionEvent.getRawY();
    }

    public float getTotalX(){
        return motionEvent.getRawX() - firstMotionEvent.getRawX();
    }

    public float getTotalY(){
        return motionEvent.getRawY() - firstMotionEvent.getRawY();
    }

}
