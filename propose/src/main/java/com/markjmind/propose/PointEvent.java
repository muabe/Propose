package com.markjmind.propose;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2016-03-28
 */
public class PointEvent {
    private float point;
    private float prePoint;
    private float raw;
    private float preRaw;
    private float velocity;
    protected int minus, plus;
    private long time;
    protected long diffTime=0;
    float density;
    protected float endBuffer;


    protected PointEvent(int minus, int plus, float density){
        this.minus = minus;
        this.plus = plus;
        this.density = density;
        reset();
    }

    protected void reset(){
        point = 0f;
        prePoint = 0f;
        raw = 0f;
        velocity = 0f;
        endBuffer = 0f;
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

    protected void setEvent(float raw, long time){
        setPreRaw(this.raw);
        this.setVelocity(raw, time);
        setTime(time);
        this.raw = raw;
    }

    protected float getPreRaw(){
        return this.preRaw;
    }

    protected void setPreRaw(float raw){
        this.preRaw = raw;
    }

    protected void setVelocity(float raw, long time){
        diffTime = time-this.time;
        this.velocity = ((raw- this.raw)/density)/(time-this.time);
    }
    protected void setVelocity(float velocity){
        this.velocity = velocity;
    }

    protected float getVelocity(){
        return velocity;
    }

    protected void setTime(long time){
        this.time = time;
    }

    protected int getChangeDirection(float movePoint){
        if((getPoint()+movePoint > 0 && getPoint() < 0)){
            return minus;
        }else if(getPoint()+movePoint < 0 && getPoint() > 0){
            return plus;
        }else {
            return Motion.NONE;
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

    protected int getDirection(float movePoint){
        if(getPoint()+movePoint < 0){
            return minus;
        }else if(getPoint()+movePoint > 0){
            return plus;
        }else{
            if(getPoint() < 0) {
                return minus;
            }else if(getPoint() > 0){
                return plus;
            }
        }
        return Motion.NONE;
    }
}
