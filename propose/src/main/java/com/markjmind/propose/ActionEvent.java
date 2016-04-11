package com.markjmind.propose;

import com.markjmind.propose.actor.Motion;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2016-03-28
 */
public class ActionEvent {
    private float point;
    private float prePoint;
    private float raw;
    private float preRaw;
    private float acceleration;
    private  int minus, plus;

    protected ActionEvent(int minus, int plus){
        this.minus = minus;
        this.plus = plus;
        reset();
    }

    protected void reset(){
        point = 0f;
        prePoint = 0f;
        raw = 0f;
        acceleration = 0f;
    }

    public float getAbsPoint(){
        return Math.abs(point);
    }

    public float getPoint() {
        return point;
    }

    public void setPoint(float point) {
        prePoint = this.point;
        this.point = point;
    }


    protected float getPrePoint(){
        return prePoint;
    }

    protected float getRaw(){
        return raw;
    }

    protected void setRaw(float raw){
        setPreRaw(this.raw);
        this.raw = raw;
    }

    protected float getPreRaw(){
        return this.preRaw;
    }

    protected void setPreRaw(float raw){
        this.preRaw = raw;
    }

    public void setAcceleration(float acceleration){
        this.acceleration = acceleration;
    }

    public float getAcceleration(){
        return acceleration;
    }


    protected boolean isChangeDirection(){
        if((point > 0 && prePoint <0) || (point < 0 && prePoint >0)){
            return true;
        }else{
            return false;
        }
    }

    protected int getDirection(){
        if(getPoint() < 0){
            return minus;
        }else if(getPoint() > 0){
            return plus;
        }else{
            if(getPrePoint() < 0) {
                return minus;
            }else if(getPrePoint() > 0){
                return plus;
            }
        }
        return Motion.NONE;
    }

    public long getDurationToDistance(Motion motion, float distance){
        long duration = motion.getDistanceToDuration(Math.abs(distance));
        if(duration >= motion.getTotalDuration()){
            if(Motion.STATUS.end.equals(motion.getStatus())){
                return -1;
            }
            motion.setStatus(Motion.STATUS.end);
            duration = motion.getTotalDuration();
        }else {
            motion.setStatus(Motion.STATUS.run);
        }
        return duration;
    }

    public long getPointToDuration(Motion motion){
        long duration = motion.getDistanceToDuration(this.getAbsPoint());
        if(duration >= motion.getTotalDuration()){ //duration(point)가 Max 범위를 벗어 났을때
            this.setPoint(motion.getMotionDistance() * motion.getDirectionArg());
            if(Motion.STATUS.end.equals(motion.getStatus())){
                return -1;
            }
            motion.setStatus(Motion.STATUS.end);
            duration = motion.getTotalDuration();
        }else {
            motion.setStatus(Motion.STATUS.run);
        }
        return duration;
    }
}
