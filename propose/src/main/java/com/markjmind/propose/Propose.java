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
 * <br>捲土重來<br>
 *
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
    public AnimationPool animationPool;
    public ActionState state;

    protected Context context;
    protected float density;

    private GestureDetector gestureDetector;
    private MotionEngine motionEngine;

    private MotionInitor motionInit;
    private RubListener rubListener;

    private boolean isTouchDown;
    private boolean enableMotion;

    private ProposeListener proposeListener;

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
        animationPool = new AnimationPool() {
            @Override
            protected void animationStart() {
                Log.e("AnimationPool","AnimationPool start");
                state.setState(ActionState.ANIMATION);
            }

            @Override
            protected void animationEnd() {
                Log.e("AnimationPool","AnimationPool End");
                state.setState(ActionState.STOP);
            }
        };
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

    public void cancel(){
        if(state.getState() == ActionState.ANIMATION){
            animationPool.cancelAll();
        }
    }

    public void setProposeListener(ProposeListener proposeListener){
        this.proposeListener = proposeListener;
    }

    public static float getDensity(Context context){
        return context.getResources().getDisplayMetrics().density;
    }

    public Propose addMotion(Motion motion){
        motion.setAnimationPool(animationPool);
        motion.setActionState(state);
        motionEngine.addMotion(motion.getDirection(), motion);

        return this;
    }

    /**
     * MotionInitor 등록
     * @param initor MotionInitor
     */
    public void setOnMotionInitor(MotionInitor initor){
        this.motionInit = initor;
    }
    /**
     * MotionInitor를 리턴받는다.
     * @return MotionInitor
     */
    public MotionInitor getMotionInitor(){
        return this.motionInit;
    }

    public Propose setRubListener(RubListener rubListener){
        this.rubListener = rubListener;
        return this;
    }

    public Propose setIsLongpressEnabled(boolean enable){
        gestureDetector.setIsLongpressEnabled(enable);
        return this;
    }

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
