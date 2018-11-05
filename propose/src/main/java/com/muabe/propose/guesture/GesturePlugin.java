package com.muabe.propose.guesture;


import android.util.Log;

import com.muabe.propose.combine.MotionPriority;
import com.muabe.propose.player.PlayListener;

public abstract class GesturePlugin<EventType> extends MotionPriority<EventType> {

    protected float point = 0f;
    private float minPoint = 0f;
    private float maxPoint = 0f;

    private PlayListener playListener;

    public GesturePlugin(float maxPoint){
        this(0f, maxPoint);
    }

    private GesturePlugin(float minPoint, float maxPoint){
        this.minPoint = minPoint;
        this.maxPoint = maxPoint;
    }


    public abstract float preemp(EventType event);
    public abstract float increase(EventType event);

    @Override
    public float motionCompare(EventType event) {
        if(getPoint() == minPoint){
            return preemp(event);
        }else{
            return point + increase(event);
        }
    }


    public float getPoint(){
        return point;
    }

    private boolean updatePoint(EventType event){
        float increasePoint = increase(event);

        if(point + increasePoint <= minPoint ){
            if(point == minPoint){
                return false;
            }
            point = minPoint;
        }else if(point + increasePoint >= maxPoint){
            if(point == maxPoint){
                return false;
            }
            point = maxPoint;
        }else{
            point +=  increasePoint;
        }
        return true;
    }

    public void setPlayListener(PlayListener playListener){
        this.playListener = playListener;
    }

    public void play(EventType event){
        if(playListener != null && updatePoint(event)){
            playListener.play(point, maxPoint);
        }
        Log.e("GesturePlugin", getClass().getName()+":"+getPoint());
    }
}
