package com.markjmind.propose;

import android.animation.ValueAnimator;

import com.markjmind.propose.actor.Mover;
import com.markjmind.propose.actor.Taper;
import com.markjmind.propose.animation.TimeAnimation;

import java.util.Hashtable;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2016-03-24
 */
public class Motion {
    public static int NONE  = 0;
    public static int LEFT  = -100;
    public static int RIGHT = 100;
    public static int UP    = -101;
    public static int DOWN  = 101;
    public static int ROTATION  = 170;
    public static int MULTI_LEFT  = -102;
    public static int MULTI_RIGHT = 102;
    public static int MULTI_UP    = -103;
    public static int MULTI_DOWN  = 103;
    public static int MULTI_ROTATION  = 171;
    public static int PINCH  = 180;


    private PointEvent pointEvent;
    private Mover mover = new Mover();
    private Taper taper = new Taper();

    // 현재 위치지점
    public enum STATUS{
        ready,run,end
    }

    private ActionState state;
    protected STATUS status;
    protected MotionBuilder builder;

    private int direction;


    /**현재 애니메이션 진행방향*/
    private boolean isForward=true;

    protected boolean isOver=false;
    /**총 움직이는 거리*/
    protected float motionDistance=0f;
    /**총 duration*/
    protected long totalDuration=-1;
    /**현재 distance위치*/
    protected float currDistance = 0f;
    /**현재 duration위치*/
    protected long currDuration=0;

    /**move중 tabUp 사용 설정*/
    protected boolean enableMoveTabUp=true;
    /**single tabUp 사용 설정*/
    protected boolean enableSingleTabUp=true;
    /**fling 사용 설정*/
    protected boolean enableFling=true;
    /**moveDistance(drag) 사용 설정*/
    protected boolean enableMove=true;
    /**reverse 사용 설정*/
    protected boolean enableReverse=true;
    /**tabUp시 duration을 거리로 환산할지 여부*/
    protected boolean enableDuration=true;
    /**tabUp시 forward시 gravity비율*/
    protected float forwardGravity=0.5f;
    /**tabUp시 reverse시 gravity비율*/
    protected float reverseGravity=0.5f;

    public Motion(){
        this(Motion.NONE);
    }

    public Motion(int direction){
        this.status = STATUS.ready;
        setDirection(direction);
    }

    protected void init(){
        this.status = STATUS.ready;
        currDuration=0;
        totalDuration=-1;
        isOver=false;
        motionDistance=0f;
        currDistance = 0f;
        isForward=true;
        enableMoveTabUp=true;
        enableSingleTabUp=true;
        enableFling=true;
        enableMove=true;
        enableReverse=true;
        enableDuration=true;
        forwardGravity=0.5f;
        reverseGravity=0.5f;
        if(builder!=null) {
            builder.clear();
        }
    }

    private int loop = Loop.REVERSE;
    public void setLoop(int loop){
        this.loop = loop;
    }

    protected void setAnimationPool(Hashtable<Integer, TimeAnimation> pool){
        taper.setAnimationPool(pool);
    }

    protected void setActionState(ActionState state){
        this.state = state;
    }

    public int getDirectionArg(){
        return direction/100;
    }

    protected void setDirection(int direction){
        this.direction = direction;
    }

    public int getDirection(){
        return this.direction;
    }

    /**
     * 모션에 따라 motion을 표현할 ValueAnimator 지정한다.
     * @param valueAnimator ValueAnimator
     * @param distance 애니메이션을 play할 거리
     * @return MotionBuilder
     */
    public MotionBuilder play(ValueAnimator valueAnimator, int distance){
        init();
        setMotionDistance(distance);
        MotionScrollItem adapter = new MotionScrollItem(valueAnimator,0);
        builder = new MotionBuilder(this, adapter);
        return builder;
    }

    public long getTotalDuration(){
        return this.totalDuration;
    }


    /**
     * 거리에 대한 duration을 리턴한다.
     * @param distance 거리
     * @return duration
     */
    public long getDurationToDistance(float distance){
        if(motionDistance==0){
            return 0;
        }
        return (long)Math.abs(totalDuration*(distance/motionDistance));
    }

    /**
     * duration에 대한 distance를 리턴한다.
     * @param duration
     * @return distance
     */
    protected float getDistanceToDuration(long duration){
        if(totalDuration==0){
            return 0f;
        }
        return motionDistance*duration/(float)totalDuration;
    }

    /**
     * 모션으로 움직이는 거리를 지정한다.<br>
     * 기본값은 가로 윈도우의 크기이다.
     * @param distance
     */
    protected void setMotionDistance(float distance){
        motionDistance = distance;
    }

    protected void setCurrDistance(float distance){
        this.currDistance = distance;
        this.currDuration = getDurationToDistance(distance);
    }

    protected void setCurrDuration(long duration){
        this.currDuration = duration;
        this.currDistance = getDistanceToDuration(duration);
    }

    public void setStatus(STATUS status){
        if(!this.status.equals(status)){
            this.status = status;
            if(state!=null){
                state.addTarget(this);
            }
        }
    }

    protected boolean isForward() {
        return isForward;
    }

    protected void setForward(boolean isForward) {
        this.isForward = isForward;
    }

    /*********************************** 추가 ***********************************

    /**
     * 모션의 애니메이션 상태를 리턴해준다.
     * @return
     */
    public Motion.STATUS getStatus(){
        return this.status;
    }

    /**
     * play할 animation이 있는지 여부<br>
     * play() 메소드로 애니메이션이 등록했는지 여부이며<br>
     * 이것을 가지고 해당 모션이 사용되고 있는지 알수 있다.
     * @return play할 animation이 있는지 여부
     */
    public boolean hasAnimation(){
        if(totalDuration>0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Move시 애니메이션 사용할것인지 여부
     * @return Move시 애니메이션 사용할것인지 여부
     */
    public boolean isEnableMove() {
        return enableMove;
    }

    /**
     * 현재 모션의 Duration을 리턴한다.
     * @return
     */
    public long getCurrDuration(){
        return currDuration;
    }

    public float getMotionDistance(){
        return motionDistance;
    }

    protected void setPointEvent(PointEvent pointEvent){
        this.pointEvent = pointEvent;
    }
    /*********************************** Move ***********************************/
    public boolean move(long duration){
        if(duration >= getTotalDuration()){
            if(STATUS.end.equals(getStatus())){
                return false;
            }
            setStatus(Motion.STATUS.end);
            duration = getTotalDuration();
        }else if(duration == 0){
            if(STATUS.ready.equals(getStatus())){
                return false;
            }
            setStatus(STATUS.ready);
        }else{
            setStatus(STATUS.run);
        }

        if(mover.move(builder, duration)){
            setCurrDuration(duration);
            if(pointEvent!=null) {
                pointEvent.setPoint(currDistance*getDirectionArg());
            }
            state.scroll(this);
            return true;
        }else{
            return false;
        }
    }

    public boolean moveDistance(float distance){
        long duration = getDurationToDistance(distance);
        return this.move(duration);
    }

    public boolean animate(){
        if(loop == Loop.REVERSE){
            if(!isForward){
                return this.animate(getCurrDuration(), 0);
            }
        }else if(loop == Loop.RESTART){
            if(status == STATUS.end){
                this.animate(0, getTotalDuration());
            }
        }
        return this.animate(getCurrDuration(), getTotalDuration());
    }

    public boolean animate(long startDuration, long endDuration){
        return this.animate(startDuration, endDuration, Math.abs(endDuration-startDuration));
    }

    public boolean animate(long startDuration, long endDuration, long playDuration){
        return taper.tap(this, startDuration, endDuration, playDuration);
    }
}
