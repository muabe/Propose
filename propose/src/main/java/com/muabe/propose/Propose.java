package com.muabe.propose;

import android.view.MotionEvent;
import android.view.View;

import com.muabe.propose.touch.detector.OnTouchDetectListener;
import com.muabe.propose.touch.detector.multi.MultiTouchEvent;
import com.muabe.propose.touch.detector.single.SingleTouchEvent;
import com.muabe.propose.util.Mlog;

import java.util.ArrayList;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 */

public class Propose implements View.OnTouchListener{
    ArrayList<Motion2> motionList = new ArrayList<>();
    TouchDetector touchDetector;
    TouchDetectListener touchDetectListener;

    public Propose(){
        touchDetectListener = new TouchDetectListener();
    }

    public Propose addMotion(Motion2 motion2){
        motionList.add(motion2);
        return this;
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(touchDetector==null){
            touchDetector = new TouchDetector(view.getContext(), touchDetectListener);
        }
        return touchDetector.onTouchEvent(view, motionEvent);
    }


    private class TouchDetectListener implements OnTouchDetectListener{


        @Override
        public boolean onDown(SingleTouchEvent event) {
            Mlog.i(this, "onDown");
            return true;
        }

        @Override
        public boolean onUp(SingleTouchEvent event) {
            return true;
        }

        @Override
        public boolean onDrag(SingleTouchEvent event) {
//        List<DragFilter> dragFilterList = Filter.getSingleValues();
//        for(DragFilter filter : dragFilterList){
//            filter.onDrag(event);
//        }
            for(Motion2 motion : motionList){
                boolean result = motion.getGesture().gesture(event);
                if(result) {
                    result = motion.play();
                }
            }
            return true;
        }

        @Override
        public boolean onMulitBegin(MultiTouchEvent event) {
            Mlog.e(this, "onMulitBegin");
            return true;
        }

        @Override
        public boolean onMultiEnd(MultiTouchEvent event) {
            Mlog.e(this, "onMultiEnd");
            return true;
        }

        @Override
        public boolean onMultiDrag(MultiTouchEvent event) {

            return true;
        }

        @Override
        public boolean onMultiUp(MultiTouchEvent multiEvent) {
            Mlog.e(this, "onMultiUp");
            return true;
        }
    }
}
