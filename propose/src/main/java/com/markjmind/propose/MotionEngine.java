package com.markjmind.propose;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * 조합된 애니메이션을 모션에 맞게 변환해주고 화면에 Touch Animation을 Play한다.<br>
 * MotionEngine 클래스의 원리는 애니메이션의 시간을 거리로 연산한다.<br>
 * 연산된 거리는 모션에 따라 Touch 좌표로 대입로 대입된다.<br>
 * onDown,onUp 등 기본적인 Touch의 패턴을 정의해 주고<br>
 * Actor, animation에서 Touch 패턴을 조합하여 구체적인 움직임을 표현한다.<br>
 * MotionEngine은 Actor, Anomation 등의 구현체들을 모아 관리하며 이들에게 일을 시키기 위한 연료를 제공한다.
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2016-03-28
 */
    public class MotionEngine implements GestureDetector.OnGestureListener{

    /** 터치 정보를 저작한 객체*/
    protected PointEvent pointEventX, pointEventY;

    /** 조합된 모션들의 맵 */
    private Hashtable<Integer, MotionsInfo> motionMap = new Hashtable<>();

    /** 모션 상태 이벤트*/
    private ActionState state;

    /** 모션의 최초 시작 여부*/
    private boolean isFirstTouch;

    /** 화면 해상도 압축율 */
    private float density;

    /** 제스쳐가 발생 리스너 객체*/
    private DetectListener detectListener;

    /** 좌우 수평 모션일때 이벤트 발생 우선순위 */
    protected int horizontalPriority = Motion.RIGHT;

    /** 상하 수직 모션일때 이벤트 발생 우선순위 */
    protected int verticalPriority = Motion.UP;

    /** 최대 가속도*/
    private float maxVelocity = 5f;

    /** 최소 가속도*/
    private float minVelocity = 0.5f;

    /**
     * 모션 종료시 애니메이션이 향하는 방향을 나타낸다<br>
     * 0일 경우 애니메이션이 시작 방향으로 흘러간다.<br>
     * 1일 경우 애니메이션이 종료 방향으로 흘러간다.
     */
    private float gravity = 0.5f;

    /** 터치 중 모션끼리 만났을때 모션이 전환되는 버퍼*/
    private float maxEndBuffer = 3f;

    /**
     * 기본 생성자
     * @param state ActionState
     * @param density 화면 해상도 압출율
     * @param detectListener DetectListener
     */
    protected MotionEngine(ActionState state, float density, DetectListener detectListener){
        this.density = density;
        maxEndBuffer = maxEndBuffer*density;

        pointEventX = new PointEvent(Motion.LEFT, Motion.RIGHT, density);
        pointEventY = new PointEvent(Motion.UP, Motion.DOWN, density);

        this.state = state;
        this.detectListener = detectListener;
        reset();
    }

    /**
     * 초기화로 재설정 한다.
     */
    protected void reset(){
        isFirstTouch = true;
        state.setState(ActionState.STOP);
    }

    /**
     * 모션을 추가한다.
     * @param direction 모션의 방향을 선택(Motion.LEFT, Motion.ROTATION 등등)
     * @param motion Motion 객체
     */
    protected void addMotion(int direction, Motion motion){
        MotionsInfo info = new MotionsInfo();
        if (motionMap.containsKey(direction)) {
            info = motionMap.get(direction);
        }
        if(direction == Motion.LEFT || direction == Motion.RIGHT) {
            motion.setPointEvent(pointEventX);
        }else if(direction == Motion.UP || direction == Motion.DOWN){
            motion.setPointEvent(pointEventY);
        }
        info.add(motion);
        motionMap.put(direction, info);

    }

    /**
     * 현재 모션 상태를 가져온다.
     * @return ActionState의 상태
     */
    protected int getActionState() {
        return this.state.getState();
    }

    /**
     * 손가락이 화면에 터치가 되었을때 구현체
     * @param event MotionEvent
     * @return 이벤트 성공여부
     */
    @Override
    public boolean onDown(MotionEvent event) {
        isFirstTouch = true;
        pointEventX.setEvent(event.getRawX(), event.getEventTime());
        pointEventX.setVelocity(0f);
        pointEventY.setEvent(event.getRawY(), event.getEventTime());
        pointEventY.setVelocity(0f);
        pointEventX.endBuffer = 0f;
        pointEventY.endBuffer = 0f;
        if (motionMap.containsKey(Motion.PRESS)) {
            MotionsInfo info = motionMap.get(Motion.PRESS);
            for(Motion pressMotion : info.motions){
                pressMotion.animate();
            }
        }
        return false;
    }


    /**
     * 손가락이 화면에 터치 후 떼었을때 구현체<br>
     * @param event MotionEvent
     * @return 이벤트 성공여부
     */
    public boolean onUp(MotionEvent event){
        boolean result = false;
        if(state.getState() == ActionState.SCROLL){
            result =  moveUp(pointEventX);
            result =  moveUp(pointEventY) || result;
            if(!result){
                state.setState(ActionState.STOP);
            }
        }

        if (motionMap.containsKey(Motion.PRESS)) {
            MotionsInfo info = motionMap.get(Motion.PRESS);
            for(Motion pressMotion : info.motions){
                pressMotion.cancelAnimate();
            }
        }
        return result;
    }


    /**
     * 손가락이 화면에 터치한 상태에서 이동후 떼었을때 구현체
     * @param pointEvent PointEvent 정보
     * @return 이벤트 성공여부
     */
    private boolean moveUp(PointEvent pointEvent){
        Log.i("Detector", "moveUp");
        boolean result = false;
        int direction = pointEvent.getDirection();
        if(direction==0){
            return false;
        }
        MotionsInfo info;
        if(direction != Motion.NONE && (info = motionMap.get(direction)) != null) {
            for (Motion motion : info.motions) {
                boolean forward = motion.getCurrDuration() >= motion.getTotalDuration()*gravity;
                if(forward == motion.isForward()){
                    result = motion.animate() || result;
                }else{
                    if(motion.isForward()) {
                        result = motion.animate(motion.getCurrDuration(), 0) || result;
                    }else{
                        result = motion.animate(motion.getCurrDuration(), motion.getTotalDuration()) || result;
                    }
                }
            }
        }
        return result;
    }


    /**
     * 화면을 SingleTapUp(클릭) 했을때 구현체<br>
     * onSingleTapUp은 이동없이 짧은 시간에 클릭한 것을 말한다.
     * @param event MotionEvent
     * @return 이벤트 성공여부
     */
    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        Log.i("Detector", "onSingleTapUp");
        boolean result;
        result = tapUp(pointEventX, horizontalPriority);
        result = tapUp(pointEventY, verticalPriority) || result;
        return result;
    }


    /**
     * 화면을 TapUp(클릭) 했을때 구현체<br>
     * tapUp은 이동없이 손가락을 떼었을때를 말한다.<br>
     * (시간과 관계없음)
     * @param pointEvent PointEvent
     * @param priority 모션 우선순위(모션이 여러개로 조합되었을 경우)
     * @return 이벤트 성공여부
     */
    private boolean tapUp(PointEvent pointEvent, int priority){
        boolean result = false;
        int direction = pointEvent.getDirection();
        if(direction==0){
            direction = priority;
        }
        MotionsInfo info;
        if(direction != Motion.NONE && (info = motionMap.get(direction)) != null) {
            for (Motion motion : info.motions) {
                if(motion.isEnableSingleTabUp()) {
                    result = motion.animate() || result;
                }
            }
        }
        return result;
    }

    /**
     * Scroll은 드래그를 말하며 움직임이 감지 된때마다 주기적으로 발생한다.<br>
     * @param e1 이전 기록 MotionEvent
     * @param e2 현재 MotionEvent
     * @param distanceX X좌표 이동거리
     * @param distanceY Y좌표 이동거리
     * @return 이벤트 성공여부
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        boolean result = false;
        if(state.getState() != ActionState.ANIMATION){
            state.setState(ActionState.SCROLL);
            if(isFirstTouch){ //최초 스크롤시
                isFirstTouch = false;
                pointEventX.setEvent(e2.getRawX(), e2.getEventTime());
                pointEventY.setEvent(e2.getRawY(), e2.getEventTime());
                pointEventX.setPreRaw(e2.getRawX());
                pointEventY.setPreRaw(e2.getRawY());
                result = true;
            } else {
                result = scroll(e2.getRawX(), pointEventX, e2.getEventTime());
                result = scroll(e2.getRawY(), pointEventY, e2.getEventTime()) || result;
            }

        }
        return result;
    }


    /**
     * onScroll 함수의 구현체<br>
     * 내부에서만 사용한다.
     * @param raw 절대 좌표값
     * @param pointEvent PointEvent
     * @param time 이동 시간
     * @return 이벤트 성공여부
     */
    private boolean scroll(float raw, PointEvent pointEvent, long time){
        float diff = raw - pointEvent.getRaw();
        pointEvent.setEvent(raw, time);
        int direction = pointEvent.getDirection(diff);

        boolean result = false;

        if(direction == Motion.NONE ){
            result = move(pointEvent, pointEvent.plus, 0f);
            result = move(pointEvent, pointEvent.minus, 0f) || result;
        }else{

            int changeDirection = pointEvent.getChangeDirection(diff);
            if(changeDirection!=Motion.NONE){
                result = move(pointEvent, changeDirection, 0f);
            }
            result = move(pointEvent, direction, diff) || result;
        }
        return result;
    }


    /**
     * View 이동 연산의 구현체<br>
     * 내부에서만 사용한다.
     * @param pointEvent PointEvent
     * @param direction 방향
     * @param diff 이동 거리
     * @return 이벤트 성공여부
     */
    private boolean move(PointEvent pointEvent, int direction, float diff){
        MotionsInfo info;
        boolean result = false;
        if((info = motionMap.get(direction)) != null){
            for (Motion motion : info.motions) {
                if(motion.isEnableMove()) {
                    if (Motion.Position.end.equals(motion.getPosition())) {
                        pointEvent.endBuffer = pointEvent.endBuffer + diff;
                        if (pointEvent.endBuffer * motion.getDirectionArg() >= maxEndBuffer / 2) {
                            pointEvent.endBuffer = maxEndBuffer * motion.getDirectionArg() / 2;
                        } else if (pointEvent.endBuffer * motion.getDirectionArg() < -maxEndBuffer) {
                            diff = pointEvent.endBuffer;
                            pointEvent.endBuffer = 0f;
                            result = motion.moveDistance(Math.abs(pointEvent.getPoint() + diff)) || result;
                        }
                    } else {
                        result = motion.moveDistance(Math.abs(pointEvent.getPoint() + diff)) || result;
                    }
                }
            }
        }
        return result;
    }


    /**
     * 화면에서 손가락을 튕겼을 경우 발생한다.<br>
     * Fling은 화면에서 빠르게 손가락은 떼면서 발생하며 슬라이딩 등 가속도 효과가 필요할때 사용된다.
     * @param e1 이전 기록 MotionEvent
     * @param e2 현재 MotionEvent
     * @param velocityX X좌표 가속도
     * @param velocityY Y좌표 가속도
     * @return 이벤트 성공여부
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.i("Detector", "flingX:"+pointEventX.getVelocity()+ " Y:"+pointEventY.getVelocity()) ;
        boolean result = false;
        if(state.getState() == ActionState.SCROLL){
            state.setState(ActionState.FlING);
            float velocity = Math.abs(pointEventX.getVelocity());

            if(velocity < Math.abs(pointEventY.getVelocity())){
                velocity = Math.abs(pointEventY.getVelocity());
            }

            result = fling(pointEventX, velocity);
            result = fling(pointEventY, velocity) || result;
            if(!result){
                state.setState(ActionState.STOP);
            }
        }
        return result;
    }


    /**
     * onFling 함수의 구현체<br>
     * 내부에서만 사용한다.
     * @param pointEvent
     * @param velocity
     * @return
     */
    private boolean fling(PointEvent pointEvent, float velocity){
        boolean result = false;
        int direction = pointEvent.getDirection();
        if(velocity == 0f || direction == Motion.NONE){
            return false;
        }else if(velocity > maxVelocity){
            velocity = maxVelocity;
        }else if(velocity < minVelocity){
            velocity = minVelocity;
        }
        MotionsInfo info = motionMap.get(direction);
        if(info != null){
            for (Motion motion : info.motions) {
                long start = motion.getCurrDuration();
                long end = motion.getTotalDuration();
                if((pointEvent.plus == direction && pointEvent.getVelocity() < 0) ||
                        (pointEvent.minus == direction && pointEvent.getVelocity() > 0)){
                    end = 0;
                }
                long playTime = (long)(motion.getDistanceToDuration(Math.abs((end-start)))/velocity/density);

                result = motion.animate(start, end, playTime) || result;
            }
        }
        return result;
    }


    /**
     * TapUp과 중복되어 현재 사용하지 않음
     * @param e MotionEvent
     */
    @Override
    public void onShowPress(MotionEvent e) {
    }


    /**
     * Drag에서도 발생되어 현재 사용하지 않음
     * @param e MotionEvent
     */
    @Override
    public void onLongPress(MotionEvent e) {
        Log.i("Detector", "onLongPress");
    }


    /**
     * 이벤트가 발생 리스너 인터페이스
     */
    interface DetectListener {
        boolean detectScroll(Motion motion, PointEvent pointEventX, PointEvent pointEventY);
        boolean detectFling(PointEvent pointEventX, PointEvent pointEventY);
    }


    /**
     * 모션 정보를 저장하여 담아 놓는 내부 클래스
     */
    class MotionsInfo {
        protected long maxDuration=0;
        protected ArrayList<Motion> motions;
        protected MotionsInfo(){
            motions = new ArrayList<>();
        }

        protected void add(Motion motion){
            if(maxDuration < motion.getTotalDuration()){
                maxDuration = motion.getTotalDuration();
            }
            motions.add(motion);
        }
    }
}
