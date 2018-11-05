package com.muabe.propose.guesture;


import android.util.Log;

import com.muabe.propose.combine.MotionPriority;
import com.muabe.propose.player.PlayListener;

public abstract class GesturePlugin<EventType> extends MotionPriority<EventType> {

    protected float point = 0f;
    private PlayListener playListener;

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

    public void updatePoint(EventType event){
        point +=  increase(event);
    }

    public void setOnPlayListener(PlayListener playListener){
        this.playListener = playListener;
    }

    public void play(EventType event){
        updatePoint(event);
        if(playListener != null){
            playListener.play(point);
        }
        Log.e("GesturePlugin", getClass().getName()+":"+getPoint());
    }
}
