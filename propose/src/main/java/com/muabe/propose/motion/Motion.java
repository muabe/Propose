package com.muabe.propose.motion;

import com.muabe.propose.State;
import com.muabe.propose.util.Mlog;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 */

public class Motion implements Point.OnPointListener {
    private State.MotionState motionState;
    private Point point;

    public Motion(State.MotionState motionState, float maxPoint){
        this.motionState = motionState;
        point = new Point(getMotionState(), maxPoint, this);
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
        return point.getMaxPoint();
    }

    public float getMinPoint(){
        return 0;
    }

    public State.MotionState getMotionState(){
        return this.motionState;
    }

    public Point getPoint(){
        return this.point;
    }
}
