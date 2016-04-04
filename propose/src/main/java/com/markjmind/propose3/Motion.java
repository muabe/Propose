package com.markjmind.propose3;

import android.animation.ValueAnimator;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2016-03-24
 */
public class Motion {
    // 현재 위치지점
    public enum STATUS{
        ready,run,end
    }

    protected STATUS status;
    protected MotionBuilder builder;
//    protected JwDefalutAnimation anim;

//    protected MotionListener motionListener;
//    protected OnSingleTapUpListener onSingleTapUpListener;

    private int directionArg=1;


    protected boolean isOver=false;
    /**총 움직이는 거리*/
    protected float motionDistance=0f;
    /**총 duration*/
    protected long totalDuration=-1;
    /**현재 distance위치*/
    protected float currDistance = 0f;
    /**현재 duration위치*/
    protected long currDuration=0;
    /**현재 애니메이션 진행방향*/
    private boolean isForward=true;
    /**JwPoint의 reverse와 다른다. 진행방향이 아닌 방향시점 상태를 나타냄*/
    protected boolean reverse=false;

    /**move중 tabUp 사용 설정*/
    protected boolean enableMoveTabUp=true;
    /**single tabUp 사용 설정*/
    protected boolean enableSingleTabUp=true;
    /**fling 사용 설정*/
    protected boolean enableFling=true;
    /**move(drag) 사용 설정*/
    protected boolean enableMove=true;
    /**reverse 사용 설정*/
    protected boolean enableReverse=true;
    /**tabUp시 duration을 거리로 환산할지 여부*/
    protected boolean enableDuration=true;
    /**tabUp시 forward시 gravity비율*/
    protected float forwardGravity=0.5f;
    /**tabUp시 reverse시 gravity비율*/
    protected float reverseGravity=0.5f;

//    protected Motion(int direction, Propose propose, JwDefalutAnimation anim){
//        this.status = STATUS.ready;
//        this.direction = direction;
//        this.propose = propose;
//        if(direction==Propose.DIRECTION_RIGHT || direction==Propose.DIRECTION_LEFT){
//            orientation = Propose.HORIZONTAL;
//        }else if(direction==Propose.DIRECTION_DOWN || direction==Propose.DIRECTION_UP){
//            orientation = Propose.VERTICALITY;
//        }else{
//            orientation = Propose.DIRECTION_NONE;
//        }
//        directionArg = Propose.getDirectionArg(direction);
//        this.anim = anim;
//    }

    protected Motion(int directionArg){
        this.status = STATUS.ready;
        this.directionArg = directionArg;
    }

    protected int getDirectionArg(){
        return directionArg;
    }

    protected void init(){
        this.status = STATUS.ready;
        currDuration=0;
        totalDuration=-1;
        isOver=false;
        motionDistance=0f;
        currDistance = 0f;
        isForward=true;
        reverse=false;
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

//    protected void reset_pg(){
//        if(resetPoint()){
//            setStatus(Motion.STATUS.ready);
//            setCurrDuration(0);
//            setCurrDistance(0);
//            isOver=false;
//            setForward(true);
//            setReverse(false);
//            for(int i= scrollItemList.size()-1;i>=0;i--){
//                scrollItemList.get(i).reset();
//            }
//        }
//    }

//    private boolean resetPoint(){
//        if(propose.currX.getDirection() == direction){
//            propose.currX.setPoint(0f);
//            propose.currX.setAcc(0f);
//            propose.currX.setForward(true);
//            propose.currX.setDirection(Propose.DIRECTION_NONE);
//        }else if(propose.currY.getDirection() == direction){
//            propose.currY.setPoint(0f);
//            propose.currY.setAcc(0f);
//            propose.currY.setForward(true);
//            propose.currY.setDirection(Propose.DIRECTION_NONE);
//        }else{
//            return false;
//        }
//        return true;
//    }

    /**
     * 모션에 따라 motion을 표현할 ValueAnimator 지정한다.
     * @param valueAnimator ValueAnimator
     * @param distance 애니메이션을 play할 거리
     * @return MotionBuilder
     */
    public MotionBuilder play(ValueAnimator valueAnimator, int distance){
        init();
//        resetPoint();
        setMotionDistance(distance);
        MotionScrollItem adapter = new MotionScrollItem(valueAnimator,0);
        builder = new MotionBuilder(this, adapter);
        return builder;
    }

    /**
     * 거리에 대한 duration을 리턴한다.
     * @param distance 거리
     * @return duration
     */
    protected long getDistanceToDuration(float distance){
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
    protected float getDurationToDistance(long duration){
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
//        if(currDuration>0){
//            currDistance =  getDurationToDistance(currDuration);
//            if(propose.currX.getDirection() == direction){
//                propose.currX.setPoint(currDistance*directionArg);
//                propose.currX.setAcc(0f);
//            }else if(propose.currY.getDirection() == direction){
//                propose.currY.setPoint(currDistance*directionArg);
//                propose.currY.setAcc(0f);
//            }
//        }
    }

    protected void setCurrDistance(float distance){
        this.currDistance = distance;
    }

    protected void setCurrDuration(long duration){
        this.currDuration = duration;
    }

    protected void setStatus(STATUS status){
//        if(this.status== STATUS.ready && status== STATUS.run){
//            if(motionListener!=null){
//                motionListener.onStart(true);
//            }
//        }else if(this.status== STATUS.end && status== STATUS.run){
//            if(motionListener!=null){
//                motionListener.onStart(false);
//            }
//        }
        this.status = status;
    }

    protected boolean isForward() {
        return isForward;
    }

    protected void setForward(boolean isForward) {
        this.isForward = isForward;
    }

    protected void setReverse(boolean reverse){
        this.reverse = reverse;
    }

    /***********************************  추가 ******************************/

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

    // setMotionDistance는 이미 있음
//    /**
//     * 모션으로 움직이는 거리를 지정한다.<br>
//     * 기본값은 가로 윈도우의 크기이다.
//     * @param distance
//     */
//    public Motion setMotionDistance(float distance){
//        setMotionDistance(distance);
//        return this;
//    }
    public float getMotionDistance(){
        return motionDistance;
    }
}
