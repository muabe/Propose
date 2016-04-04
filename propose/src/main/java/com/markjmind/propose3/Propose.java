package com.markjmind.propose3;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2016-03-28
 */
public class Propose implements View.OnTouchListener{
    private GestureDetector gestureDetector;

    protected Context context;
    protected float density;

    private Detector detector;

    public Motion left, right, up, down;

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

        detector = new Detector(density);
        gestureDetector = new GestureDetector(context, detector);

        left = detector.left;
        right = detector.right;
        up = detector.up;
        down = detector.down;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        v.setClickable(true);
        return gestureDetector.onTouchEvent(event);
    }


    public static float getDensity(Context context){
        return context.getResources().getDisplayMetrics().density;
    }

}
