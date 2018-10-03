package com.muabe.propose.motion;

import com.muabe.propose.State;
import com.muabe.propose.util.Mlog;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 */

public class Motion3 implements MPoint.OnPointListener {
    private State.MotionState motionState;
    private MPoint MPoint;

    public Motion3(State.MotionState motionState, float maxPoint){
        this.motionState = motionState;
        MPoint = new MPoint(getMotionState(), maxPoint, this);
    }



    @Override
    public void onPoint(float prePoint, float point) {
        Mlog.d(this, motionState+" : "+point);
    }

    @Override
    public void onMin(float currentPoint, float distance) {
        Mlog.d(this, motionState+" : Min");
    }

    @Override
    public void onMax(float currentPoint, float distance) {
        Mlog.d(this, motionState+" : Max");
    }

    public float getMaxPoint(){
        return MPoint.getMaxPoint();
    }

    public float getMinPoint(){
        return 0;
    }

    public State.MotionState getMotionState(){
        return this.motionState;
    }

    public MPoint getMPoint(){
        return this.MPoint;
    }
}
