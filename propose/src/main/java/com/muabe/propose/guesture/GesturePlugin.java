package com.muabe.propose.guesture;


import android.util.Log;

import com.muabe.propose.combine.MotionPriority;

public abstract class GesturePlugin<EventType> extends MotionPriority<EventType> {

    protected float point = 0f;



    public abstract float preemp(EventType event);
    public abstract float increase(EventType event);


    @Override
    public float motionCompare(EventType event) {
        if(getPoint() <= 0){
            return preemp(event);
        }else{
            return point + increase(event);
        }
    }


    public float getPoint(){
        return point;
    }

    public float updatePoint(EventType event){
        point +=  increase(event);
        return point;
    }

    public void play(EventType event){
        updatePoint(event);
        Log.e("GesturePlugin", getClass().getName()+":"+getPoint());
    }

}
