package com.markjmind.propose;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.markjmind.propose.actor.Motion;
import com.markjmind.propose.actor.MotionInitor;
import com.markjmind.propose.listener.RubListener;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2016-03-28
 */
public class Propose implements View.OnTouchListener{
    protected Context context;
    protected float density;

    private GestureDetector gestureDetector;
    private Detector detector;

    private MotionInitor motionInit;
    private RubListener rubListener;

    private boolean isTouchDown;
    private boolean enableMotion;

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
        detector = new Detector(density, new DetectEvent());
        gestureDetector = new GestureDetector(context, detector);
        this.setIsLongpressEnabled(false);
        isTouchDown = false;
        enableMotion = true;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        v.setClickable(true);
        boolean result = false;

        int action = event.getAction();
        if (enableMotion) {
            switch (action & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN: {
                    if (motionInit != null) {
                        if(!isTouchDown && detector.getActionState() == ActionState.STOP){
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
                    result = detector.onUp(event) || result;
                    isTouchDown = false;
                    break;
                }
            }
        }else{
            isTouchDown = false;
        }

        return result;
    }


    public static float getDensity(Context context){
        return context.getResources().getDisplayMetrics().density;
    }

    public Propose addMotion(Motion motion){
        detector.addMotion(motion.getDirection(), motion);
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

    private class DetectEvent implements Detector.DetectListener{
        @Override
        public boolean onScroll(PointEvent pointEventX, PointEvent pointEventY) {
            boolean result = false;
            if(rubListener!=null) {
                float diffX = pointEventX.getRaw()- pointEventX.getPreRaw();
                float diffY = pointEventY.getRaw()- pointEventY.getPreRaw();
                result = rubListener.rub(diffX, diffY) || result;
            }
            return result;
        }

        @Override
        public boolean onFling(PointEvent pointEventX, PointEvent pointEventY) {
            return false;
        }
    }
}
