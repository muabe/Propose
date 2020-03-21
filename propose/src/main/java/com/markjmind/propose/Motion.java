package com.markjmind.propose;

import android.animation.ValueAnimator;

import com.markjmind.propose.actor.Mover;
import com.markjmind.propose.actor.Taper;
import com.markjmind.propose.animation.AnimationBuilder;
import com.markjmind.propose.animation.TimeAnimation;
import com.markjmind.propose.listener.MotionListener;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * 모션이란 드래그, 핀치 등등 사용자가 터치한 상태에서 행동하는 일련의 패턴 정의를 말한다.<br>
 * 모션의 속성을 선택하고 애니메이션을 등록하는 등 개발 인터페이스를 제공하는 클래스이다.<br><br>
 *
 * Motion class에서 등록/속성 설정은 크게 4가지로 나눌 수 있다.<br><br>
 *
 * 1. 모션 선택<br>
 * 드래그 Left,Right 등 상수로 제공하고 있다.<br>
 * 상수는 각 의미를 가지는 int형으로 MotionEngine에서 부호, 값의 합산을 이용하여 구분되어 진다.
 * ex) Motion.LEFT, Motion.RIGHT<br><br>
 *
 * 2. 리스너 등록<br>
 * 모션이 시작, 동작, 종료 시점에서 필요한 모든 이벤트를 제공하고 있다.<br>
 * ex) 리스너 함수는 set***Listener()로 형태로 정의되어 있다.<br><br>
 *
 * 3. 제스쳐 옵션 선택<br>
 * 제스쳐란 Fling 같이 사용자가 터치 이후(손가락을 뗀 직후)의 행동 패턴 정의를 말한다.
 * ex) 제스쳐 옵션 함수는 enable***()로 형태로 정의되어 있다.<br><br>
 *
 * 4. 애니메이션 등록<br>
 * 애니메이션은 여러개로 조합 가능하며 인터페이스는 체이닝 형태로 제공한다.<br>
 * 체이닝은 각 MotionBuilder 리턴되어 메뉴얼 방식의 개발을 지원한다.<br>
 * ex) Motion.play().with().next() -> play()함수 호출후 with(),next() 함수만 호출할 수 있게 된다<br><br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2016-03-24
 */
public class Motion {

    /** 모션이 없음(기본값) */
    public final static int NONE  = 0;

    /** 아래로 드래그*/
    public final static int PRESS  = 50;

    /** 왼쪽으로 드래그 되는 모션*/
    public final static int LEFT  = -100;

    /** 오른쪽로 드래그*/
    public final static int RIGHT = 100;

    /** 위로 드래그*/
    public final static int UP    = -101;

    /** 아래로 드래그*/
    public final static int DOWN  = 101;


    /** 원을 그리며 드래그*/
    public final static int ROTATION  = 170;

    /** 두손가락으로 왼쪽 드래그*/
    public final static int MULTI_LEFT  = -102;

    /** 두손가락으로 오른쪽 드래그*/
    public final static int MULTI_RIGHT = 102;

    /**두손가락으로 위로 드래그*/
    public final static int MULTI_UP    = -103;

    /**두손가락으로 아래로 드래그*/
    public final static int MULTI_DOWN  = 103;

    /**두손가락으로 원을 그리며 드래그*/
    public final static int MULTI_ROTATION  = 171;

    /**두손가락을 벌리며거나 오무리며 드래그*/
    public final static int PINCH  = 180;

    /** 좌표 및 터치 정보 */
    private PointEvent pointEvent;
    /** 드래그 구현체 */
    private Mover mover = new Mover();
    /** Tap 구현체 */
    private Taper taper = new Taper();

    /** 모션 동작 상태*/
    private ActionState globalState;

    /**애니메이션의 동작 위치 */
    public enum Position {
        start, between, end
    }

    /** 애니메이션의 동작 위치*/
    protected Position position;

    /** 애니메이션 등록을 위한 빌더*/
    protected MotionBuilder builder;

    /** 설정된 MotionListener*/
    private MotionListener motionListener;

    /** 모션이 시작 되었는지에 대한 구분자*/
    private boolean isMotionRunning;

    /** 애니메이션 진행 방향*/
    private int direction;

    /** 애니메이션 진행 방향*/
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


    /**single tabUp 사용 설정*/
    private boolean enableSingleTabUp=true;

    /**moveDistance(drag) 사용 설정*/
    private boolean enableMove=true;

    /**move중 tabUp 사용 설정*/
    private boolean enableMoveTabUp=true;

    /**fling 사용 설정*/
    private boolean enableFling=true;

    /** 기본 생성자 */
    public Motion(){
        this(Motion.NONE);
    }

    /** 모션을 선택하여 시작하는 생성자 */
    public Motion(int direction){
        this.position = Position.start;
        isMotionRunning = false;
        setDirection(direction);
    }

    /**
     * Motion 클래스 초기화 함수
     */
    protected void init(){
        this.position = Position.start;
        loop = Loop.REVERSE;

        isMotionRunning = false;

        currDuration=0;
        totalDuration=-1;
        motionDistance=0f;
        currDistance = 0f;
        isForward=true;
//        enableMoveTabUp=true;
//        enableSingleTabUp=true;
//        enableFling=true;
//        enableMove=true;
        if(builder!=null) {
            builder.clear();
        }
    }

    /**
     * 애니메이션 관리를 위한 AnimationQue 객체 설정
     * @param que AnimationQue객체
     */
    protected void setAnimationQue(AnimationQue que){
        taper.setAnimationQue(que);
    }

    /**
     * 모션의 상태를 설정하고
     * 그에 따른 이벤트를 받아오고 정의한다.
     * @param globalState 설정할 State
     */
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

    /**
     * 모션의 방향을 설정한다.
     * @param direction 설정할 방향
     */
    protected void setDirection(int direction){
        this.direction = direction;
    }

    /**
     * 터치 드래그 거리를 설정한다.
     * @param distance 거리값
     */
    protected void setCurrDistance(float distance){
        this.currDistance = distance;
        this.currDuration = getDurationToDistance(distance);
    }

    /**
     * 애니메이션이 play되는 시간을 설정한다.
     * @param duration 밀리세컨즈 시간값
     */
    protected void setCurrDuration(long duration){
        this.currDuration = duration;
        this.currDistance = getDistanceToDuration(duration);
    }

    /**
     * 애니메이션이 정방향 진행여부
     * @return 정방향 진행방향 여부
     */
    protected boolean isForward() {
        return isForward;
    }

    /**
     * 애니메이션 진행 방향을 설정한다.
     * @param isForward true일 경우 정방향
     */
    protected void setForward(boolean isForward) {
        this.isForward = isForward;
    }

    /**
     * 애니메이션 터치 포인트 정보를 설정한다.
     * @param pointEvent 터치 포인트 정보 객체
     */
    protected void setPointEvent(PointEvent pointEvent){
        this.pointEvent = pointEvent;
    }

    /**
     * 애니메이션의 동작 위치를 설정한다.
     * @param position Position 설정
     */
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
     * @param valueAnimator 추가할 ValueAnimator
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

    public MotionBuilder play(AnimationBuilder animationBuilder, int distance){
        return play(animationBuilder.getValueAnimator(), distance);
    }

    /**
     * 해당되는 시간으로 애니메이션을 이동 시킨다.
     * @param duration 이동할 시간값
     * @return 이동 성공 여부
     */
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

    /**
     * 애니메이션을 이동하고 이동된 정보를 저장한다.<br>
     * 시간, 상태, 터치 정보 등을 저장한다.
     * - 내부에서만 사용되는 함수
     * @param duration 이동할 시간값
     * @return 이동 성공 여부
     */
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

    /**
     * 해당되는 거리로 애니메이션을 이동 시킨다.
     * @param distance 이동할 거리값
     * @return 이동 성공 여부
     */
    public boolean moveDistance(float distance){
        long duration = getDurationToDistance(distance);
        return this.move(duration);
    }

    /**
     * 처음부터 끝까지 애니메이션을 play한다..<br>
     * @return 애니메이션 성공여부
     */
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

    /**
     * 지정된 시간 범위로 애니메이션을 play한다.
     * @param startDuration 시작 시점
     * @param endDuration 종료 시점
     * @return 애니메이션 성공 여부
     */
    public boolean animate(long startDuration, long endDuration){
        return this.animate(startDuration, endDuration, Math.abs(endDuration-startDuration));
    }



    /**
     * 지정된 시간 범위로 애니메이션을 play한다.
     * @param startDuration 시작 시점
     * @param endDuration 종료 시점
     * @param playDuration play되는 시간
     * @return 애니메이션 성공 여부
     */
    public boolean animate(long startDuration, long endDuration, long playDuration){
        return taper.startAnimation(this, startDuration, endDuration, playDuration) != null;
    }


    public void cancelAnimate(){
        taper.cancel();
    }

    /**
     * 루프 방법을 성정한다.
     * @param loop Loop 객체의 상수
     */
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
     * Motion 리스너를 등록한다.
     * @param motionListener 모션 리스너 객체
     */
    public void setMotionListener(MotionListener motionListener){
        this.motionListener = motionListener;
    }

    /**
     * direction 상수값을 연산하여 방향 상수값을 리턴한다.
     * @return 방향 상수값
     */
    public int getDirectionArg(){
        return direction/100;
    }

    /**
     * 애니메이션 진행 방향을 리턴한다.
     * @return 애니메이션 방향
     */
    public int getDirection(){
        return this.direction;
    }

    /**
     * 조합된 애니메이션들의 총 Play 시간값을 리턴한다.
     * @return 애니메이션 총 play 시간
     */
    public long getTotalDuration(){
        return this.totalDuration;
    }

    /**
     * 현재 모션의 Duration을 리턴한다.
     * @return
     */
    public long getCurrDuration(){
        return currDuration;
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
     * 모션의 드래그 거리값을 리턴한다.
     * @return
     */
    public float getMotionDistance(){
        return motionDistance;
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
     * 모션의 애니메이션 상태를 리턴해준다.
     * @return
     */
    public Position getPosition(){
        return this.position;
    }

    /**
     * 이니메이션이 진행이 반대 방향인지 리턴한다.
     * @return 반대 방향인지 여부
     */
    public boolean isReverse(){
        return !isForward;
    }

    /**
     * View에 TabUp이 일어날 경우 애니메이션을 실행 여부
     * @return 애니메이션 자동 실행 여부
     */
    public boolean isEnableSingleTabUp(){
        return enableSingleTabUp;
    }


    /**
     * View에 TabUp이 일어날 경우 애니메이션을 실행에 대한 옵션 설정
     * @param enable 실행 여부
     * @return Motion 체이닝 객체
     */
    public Motion enableSingleTabUp(boolean enable){
        this.enableSingleTabUp = enable;
        return this;
    }

    /**
     * Move시 애니메이션 사용할것인지 여부
     * @return Move시 애니메이션 사용할것인지 여부
     */
    public boolean isEnableMove() {
        return enableMove;
    }

    /**
     * Move시 애니메이션 사용하는 것에 대한 옵션 설정
     * @param enable move 옵션 여부
     * @return Motion 체이닝 객체
     */
    public Motion enableMove(boolean enable){
        this.enableMove = enable;
        return this;
    }
}
