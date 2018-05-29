package com.muabe.propose;

import com.muabe.propose.guesture.BaseGesture;
import com.muabe.propose.guesture.MotionDistance;

public class Motion2 {
    public static final int INFINITY = 1;
    private MotionDistance motionDistance;
    private BaseGesture baseGesture;
    private OnPlayListener playListener;


    public Motion2(BaseGesture baseGesture){
        this.motionDistance = new MotionDistance();
        this.baseGesture = baseGesture;
        this.baseGesture.init(motionDistance);
    }

    public BaseGesture getGesture(){
        return baseGesture;
    }

    public void setOnPlayListener(OnPlayListener playListener){
        this.playListener = playListener;
    }

    boolean play(){
        if(playListener==null){
            return false;
        }
        return playListener.play(motionDistance.get());
    }
}
