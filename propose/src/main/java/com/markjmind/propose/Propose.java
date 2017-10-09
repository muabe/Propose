package com.markjmind.propose;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.markjmind.propose.listener.MotionInitor;
import com.markjmind.propose.listener.ProposeListener;
import com.markjmind.propose.listener.RubListener;

import java.util.ArrayList;

/**
 * 모션을 관리하는 사용자(개발자) 인터페이스 클래스<br>
 * 애니메이션을 Motion 단위로 1개 이상을 등록하여 사용할수 있다.<br>
 * 등록된 애니메이션은 내부 물리로직으로 터치좌표가 애니메이션으로 자동변환되어 쉽게 모현을 표현할수 있다.
 * 또한 모션에 필요한 제스쳐, 리스너, 상태 등의 옵션을 등록 및 선택 할수있다.<br>
 * 이처럼 터치에 관련된 모든 사항을 Propose 객체 내부에서 처리하며 사용자에게 전반적인 인터페이스를 제공한다.<br>
 * 초기 Propose는 많은 내용들을 내포하고있어 복잡했으나<br>
 * 기능별로 그룹화된 체이닝 패턴을 적용하여 개발 직관성을 높혔다.
 * ex)poropse.paly().with().next();<br>
 * 예를들어 모션을 시작한다면 play() 함수를 호출하고 그 이후 선택할수 있는 함수는 with(),next()만 보이게된다.<br>
 * <br>
 *
 * 대표적인 체이닝 그룹은 아래와 같다.<br>
 * Play : 애니메이션 등록 및 실행 순서<br>
 * Motion : 모션의 설정<br>
 * Enable : 제스쳐 옵션<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2016-03-28
 */
public class Propose implements View.OnTouchListener{
    /** 애니메이션 큐 객체*/
    public AnimationQue animationQue;
    /** 상태 이벤트를 감지하는 객체*/
    public ActionState state;
    /** 안드로이드 컨텍스트 객체 */
    protected Context context;
    /** 화면 해상도 압출율*/
    protected float density;
    /** 제스쳐 감지 이벤트*/
    private GestureDetector gestureDetector;
    /** 모션연산 객체*/
    private MotionEngine motionEngine;
    /** 모션 초기화 리스너*/
    private MotionInitor motionInit;
    /** 문지르기 리스너*/
    private RubListener rubListener;
    /** 터치가 되었는지 여부*/
    private boolean isTouchDown;
    /** 모션을 사용하는지에 대한 여부*/
    private boolean enableMotion;
    /** 전체 모션 사이클에 대한 리스너*/
    private ProposeListener proposeListener;

    /**
     * 기본 생성자
     * @param context 안드로이드 컨텍스트 객체
     */
    public Propose(Context context){
        this.context = context;
        init();
    }

    /**
     * 초기화 함수
     */
    private void init(){
        density = context.getResources().getDisplayMetrics().density;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        state = new ActionState();
        motionEngine = new MotionEngine(state, density, new DetectEvent());
        gestureDetector = new GestureDetector(context, motionEngine);
        this.setIsLongpressEnabled(false);
        isTouchDown = false;
        enableMotion = true;

        /** 애니메이션 큐 객체 생성*/
        animationQue = new AnimationQue() {
            /**
             * 애니메이션 시작시 시작 상태로 변경
             */
            @Override
            protected void animationStart() {
                Log.e("AnimationQue","AnimationQue start");
                state.setState(ActionState.ANIMATION);
            }

            /**
             * 애니메이션 종료시 종료 상태로 변경
             */
            @Override
            protected void animationEnd() {
                Log.e("AnimationQue","AnimationQue End");
                state.setState(ActionState.STOP);
            }
        };

        /** 상태 변화를 감지하기 위한 옵저버 등록*/
        state.addObserver(new ActionState.StateObserver() {
            @Override
            public void onChangeState(int preState, int currState, ArrayList<Motion> targetList) {

                if(currState != ActionState.STOP){
                    if(preState == ActionState.STOP){
                        if(proposeListener != null){
                            proposeListener.onStart();
                        }
                    }
                }else{
                    for(Motion motion : targetList){
                        if(motion.getPosition().equals(Motion.Position.start)){
                            motion.setForward(true);
                        }else if(motion.getPosition().equals(Motion.Position.end)){
                            motion.setForward(false);
                        }
                    }
                    if(proposeListener != null){
                        proposeListener.onEnd();
                    }
                }
            }

            @Override
            public void scroll(Motion motion) {
                if(proposeListener != null){
                    proposeListener.onScroll(motion, motion.getCurrDuration(), motion.getTotalDuration());
                }
            }
        });
    }


    /**
     * 터치가 발생 되었을때 터치 좌표에 대해 이벤트를 받아온다.
     * @param v 터치 이벤트가 발생된 VIew 객체
     * @param event 이벤트 정보
     * @return 이벤트 실행 여부
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        v.setClickable(true);
        boolean result = false;

        int action = event.getAction();
        if (enableMotion) {
            switch (action & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN: {
                    cancel();
                    if (motionInit != null) {
                        if(!isTouchDown && motionEngine.getActionState() == ActionState.STOP){
                            motionInit.touchDown(this);
                        }
                    }
                    isTouchDown = true;
                    break;
                }
                case MotionEvent.ACTION_UP: {
                   if (motionInit != null) {
                        motionInit.touchUp(this);
                    }
                    break;
                }
                case MotionEvent.ACTION_MOVE: {
                    break;
                }
                case MotionEvent.ACTION_POINTER_DOWN: {
                    break;
                }
                case MotionEvent.ACTION_CANCEL: {
                    break;
                }
                case MotionEvent.ACTION_POINTER_UP: {
                    break;
                }
            }

            result = gestureDetector.onTouchEvent(event);

            switch (action & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_UP: {
                    result = motionEngine.onUp(event) || result;
                    isTouchDown = false;
                    break;
                }
            }
        }else{
            isTouchDown = false;
        }

        return result;
    }


    /**
     * 모든 모션과 애니메이션을 정지한다.
     */
    public void cancel(){
        if(state.getState() == ActionState.ANIMATION){
            animationQue.cancelAll();
        }
    }


    /**
     * ProposeListener를 등록한다.<br>
     * 전체 모션 사이클에 대한 이벤트를 받을수 있다.
     * @param proposeListener ProposeListener 객체
     */
    public void setProposeListener(ProposeListener proposeListener){
        this.proposeListener = proposeListener;
    }

    /**
     * 화면 압축유를 가져온다.
      * @param context 안드로이드 context 객체
     * @return 압축율
     */
    public static float getDensity(Context context){
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * 모션을 추가한다.
     * @param motion 추가할 모션 객체
     * @return 체이닝 Propose 객체
     */
    public Propose addMotion(Motion motion){
        motion.setAnimationQue(animationQue);
        motion.setActionState(state);
        motionEngine.addMotion(motion.getDirection(), motion);

        return this;
    }

    /**
     * MotionInitor 등록한다<br>
     * 모션 초기화시 이벤트를 받을수 있으며 필요한 내용을 추가할수 있다.
     * @param initor MotionInitor 객체
     */
    public void setOnMotionInitor(MotionInitor initor){
        this.motionInit = initor;
    }


    /**
     * MotionInitor 객체를 리턴받는다.
     * @return MotionInitor 객체
     */
    public MotionInitor getMotionInitor(){
        return this.motionInit;
    }

    /**
     * RubListener를 등록한다.<br>
     * 문지르기에 대한 이벤트를 받을수 있다.
     * @param rubListener RubListener 객체
     * @return 체이닝 Propose 객체
     */
    public Propose setRubListener(RubListener rubListener){
        this.rubListener = rubListener;
        return this;
    }

    /**
     * Long Press의 옵션 사용 설정
     * @param enable true 경우 옵션 사용
     * @return 체이닝 Propose 객체
     */
    public Propose setIsLongpressEnabled(boolean enable){
        gestureDetector.setIsLongpressEnabled(enable);
        return this;
    }

    /**
     * MotionEngine에서 필요한 터치의 드래그와 Fling(튕기기)를 감지하는 인터페이스 구현
     */
    private class DetectEvent implements MotionEngine.DetectListener{
        @Override
        public boolean detectScroll(Motion motion, PointEvent pointEventX, PointEvent pointEventY) {
            boolean result = false;
            if(rubListener!=null) {
                float diffX = pointEventX.getRaw()- pointEventX.getPreRaw();
                float diffY = pointEventY.getRaw()- pointEventY.getPreRaw();
                result = rubListener.rub(diffX, diffY);
            }
            return result;
        }

        @Override
        public boolean detectFling(PointEvent pointEventX, PointEvent pointEventY) {
            return false;
        }
    }
}
