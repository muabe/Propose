package com.markjmind.propose;

import android.animation.ValueAnimator;

import com.markjmind.propose.actor.Mover;
import com.markjmind.propose.actor.Taper;
import com.markjmind.propose.animation.TimeAnimation;
import com.markjmind.propose.listener.MotionListener;

import java.util.ArrayList;
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
    public enum Position {
        start, between, end
    }

    private ActionState globalState;
    protected Position position;
    protected MotionBuilder builder;
    private MotionListener motionListener;
    private boolean isMotionRunning;

    private int direction;
    private int loop = Loop.REVERSE;

    /**현재 애니메이션 진행방향*/
    private boolean isForward=true;
    /**총 움직이는 거리*/
    protected float motionDistance=0f;
    /**총 duration*/
    protected long totalDuration=-1;
    /**현재 distance위치*/
    protected float currDistance = 0f;
    /**현재 duration위치*/
    protected long currDuration=0;

    /********************** 속성 **********************/
    /**moveDistance(drag) 사용 설정*/
    protected boolean enableMove=true;
    /**move중 tabUp 사용 설정*/
    protected boolean enableMoveTabUp=true;
    /**single tabUp 사용 설정*/
    protected boolean enableSingleTabUp=true;
    /**fling 사용 설정*/
    protected boolean enableFling=true;
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
        this.position = Position.start;
        isMotionRunning = false;
        setDirection(direction);
    }

    protected void init(){
        this.position = Position.start;
        loop = Loop.REVERSE;

        isMotionRunning = false;

        currDuration=0;
        totalDuration=-1;
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

    protected void setAnimationPool(Hashtable<Integer, TimeAnimation> pool){
        taper.setAnimationPool(pool);
    }

    protected void setActionState(ActionState globalState){
        this.globalState = globalState;
        this.globalState.addObserver(new ActionState.StateObserver() {
            @Override
            public void onChangeState(int preState, int currState, ArrayList<Motion> targetList) {
                if(motionListener!=null && isMotionRunning && currState == ActionState.STOP){
                    motionListener.onEnd(Motion.this);
                    isMotionRunning = false;
                }
            }

            @Override
            public void scroll(Motion motion) {
                if(motionListener!=null && motion.equals(Motion.this)){
                    if(!isMotionRunning){
                        isMotionRunning = true;
                        motionListener.onStart(Motion.this);
                    }
                    motionListener.onScroll(Motion.this, getCurrDuration(), getTotalDuration());

                }
            }
        });
    }

    protected void setDirection(int direction){
        this.direction = direction;
    }



    protected void setCurrDistance(float distance){
        this.currDistance = distance;
        this.currDuration = getDurationToDistance(distance);
    }

    protected void setCurrDuration(long duration){
        this.currDuration = duration;
        this.currDistance = getDistanceToDuration(duration);
    }



    protected boolean isForward() {
        return isForward;
    }

    protected void setForward(boolean isForward) {
        this.isForward = isForward;
    }

    protected void setPointEvent(PointEvent pointEvent){
        this.pointEvent = pointEvent;
    }

    public void setPosition(Position position){
        if(!this.position.equals(position)){
            this.position = position;
            if(globalState !=null){
                globalState.addTarget(this);
            }
        }
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

    /*********************************** Move ***********************************/
    public boolean move(long duration){
        if (duration >= getTotalDuration()) {
            if(loop == Loop.RESTART) {
                boolean result = false;
                if (!Position.end.equals(getPosition())) {
                    result = moveAndSave(getTotalDuration());
                }
                duration = duration%getTotalDuration();
                if(duration == 0){
                    setPosition(Position.end);
                    return result;
                }
                setPosition(Position.between);
            }else{
                if (Position.end.equals(getPosition())) {
                    return false;
                }
                duration = getTotalDuration();
                setPosition(Position.end);
            }
        } else if (duration == 0) {
            if (Position.start.equals(getPosition())) {
                return false;
            }
            setPosition(Position.start);
        } else {
            setPosition(Position.between);
        }

        return moveAndSave(duration);
    }

    private boolean moveAndSave(long duration){
        if(mover.move(builder, duration)){
            setCurrDuration(duration);
            if(pointEvent!=null) {
                pointEvent.setPoint(currDistance*getDirectionArg());
            }
            globalState.scroll(this);
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
       if(loop == Loop.RESTART){
            if(position == Position.end){
                this.animate(0, getTotalDuration());
            }
        }else{
            if(!isForward){
                return this.animate(getCurrDuration(), 0);
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

    /*********************************** 지원함수 ***********************************/

    public void setLoop(int loop){
        this.loop = loop;
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
     * 현재 모션의 Duration을 리턴한다.
     * @return
     */
    public long getCurrDuration(){
        return currDuration;
    }

    public float getMotionDistance(){
        return motionDistance;
    }

    public void setMotionListener(MotionListener motionListener){
        this.motionListener = motionListener;
    }

    public int getDirectionArg(){
        return direction/100;
    }

    public int getDirection(){
        return this.direction;
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
    public float getDistanceToDuration(long duration){
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
    public void setMotionDistance(float distance){
        motionDistance = distance;
    }

    /**
     * 모션의 애니메이션 상태를 리턴해준다.
     * @return
     */
    public Position getPosition(){
        return this.position;
    }

    /*********************************** 속성 ***********************************/


    /**
     * Move시 애니메이션 사용할것인지 여부
     * @return Move시 애니메이션 사용할것인지 여부
     */
    public boolean isEnableMove() {
        return enableMove;
    }

//    /**현재 애니메이션 진행방향*/
//    private boolean isForward=true;
    public boolean isReverse(){
        return !isForward;
    }

//    /**총 움직이는 거리*/
//    protected float motionDistance=0f;
//    /**총 duration*/
//    protected long totalDuration=-1;
//    /**현재 distance위치*/
//    protected float currDistance = 0f;
//    /**현재 duration위치*/
//    protected long currDuration=0;
//
//    /**move중 tabUp 사용 설정*/
//    protected boolean enableMoveTabUp=true;
//    /**single tabUp 사용 설정*/
//    protected boolean enableSingleTabUp=true;
//    /**fling 사용 설정*/
//    protected boolean enableFling=true;
//    /**moveDistance(drag) 사용 설정*/
//    protected boolean enableMove=true;
//    /**reverse 사용 설정*/
//    protected boolean enableReverse=true;
//    /**tabUp시 duration을 거리로 환산할지 여부*/
//    protected boolean enableDuration=true;
//    /**tabUp시 forward시 gravity비율*/
//    protected float forwardGravity=0.5f;
//    /**tabUp시 reverse시 gravity비율*/
//    protected float reverseGravity=0.5f;


}
