package com.markjmind.propose;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.markjmind.propose.actor.Motion;
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
    private DetectEvent detectEvent;
    private RubListener rubListener;

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
        detectEvent = new DetectEvent();
        detector = new Detector(density, detectEvent);

        gestureDetector = new GestureDetector(context, detector);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        v.setClickable(true);
        return gestureDetector.onTouchEvent(event);
    }


    public static float getDensity(Context context){
        return context.getResources().getDisplayMetrics().density;
    }

    public Propose addMotion(Motion motion){
        detector.addMotion(motion.getDirection(), motion);
        return this;
    }

    public Propose setRubListener(RubListener rubListener){
        this.rubListener = rubListener;
        return this;
    }

    private class DetectEvent implements Detector.DetectListener{
        @Override
        public boolean onScroll(ActionEvent actionEventX, ActionEvent actionEventY) {
            boolean result = false;
            if(rubListener!=null) {
                float diffX = actionEventX.getRaw()- actionEventX.getPreRaw();
                float diffY = actionEventY.getRaw()- actionEventY.getPreRaw();
                result = rubListener.rub(diffX, diffY) || result;
            }
            return result;
        }

        @Override
        public boolean onFling(ActionEvent actionEventX, ActionEvent actionEventY) {
            return false;
        }
    }
}
