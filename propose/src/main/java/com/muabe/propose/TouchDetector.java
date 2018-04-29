package com.muabe.propose;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import com.muabe.propose.motion.DragFilter;
import com.muabe.propose.motion.filter.Filter;
import com.muabe.propose.touch.coords.metrix.MetrixCordinates;
import com.muabe.propose.touch.coords.window.WindowCoordinates;
import com.muabe.propose.touch.detector.multi.MultiMotionEvent;
import com.muabe.propose.touch.detector.OnTouchDetectListener;
import com.muabe.propose.touch.detector.single.SingleMotionEvent;
import com.muabe.propose.touch.detector.TouchDetectAdapter;
import com.muabe.propose.util.Mlog;

import java.util.List;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 */

public class TouchDetector implements OnTouchDetectListener {

    private TouchDetectAdapter touchDetectAdapter;
    private boolean isWindow = false;

    public TouchDetector(Context context) {
        touchDetectAdapter = new TouchDetectAdapter(context, this);
        isWindow = WindowCoordinates.isBindWindow();
        Mlog.e(this, "WindowCoordinates:"+isWindow);
    }

//
//    //TODO REMOVE
//    private void test(){
//        Filter.addMotion(new Motion(State.MotionState.LEFT));
//        Filter.addMotion(new Motion(State.MotionState.RIGHT));
////        Filter.addMotion(new Motion(State.MotionState.UP));
////        Filter.addMotion(new Motion(State.MotionState.DOWN));
//        Filter.addMotion(new Motion(State.MotionState.MULTI_LEFT));
//        Filter.addMotion(new Motion(State.MotionState.MULTI_RIGHT));
//    }


    public boolean onTouchEvent(View touchView, MotionEvent originEvent) {
        //좌표로 변환
        touchView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

            }
        });
        MotionEvent event;
        if(isWindow) {
            event = WindowCoordinates.convertMotionEvent(originEvent);
        }else{
            event =MetrixCordinates.convertMotionEvent(originEvent, touchView, true);
        }

        return touchDetectAdapter.onTouchEvent(event);
    }

    @Override
    public boolean onDown(SingleMotionEvent event) {
        Mlog.i(this, "onDown");
        return true;
    }

    @Override
    public boolean onUp(SingleMotionEvent event) {
        return true;
    }

    @Override
    public boolean onDrag(SingleMotionEvent event) {
        List<DragFilter> dragFilterList = Filter.getSingleValues();
        for(DragFilter filter : dragFilterList){
            filter.onDrag(event);
        }
        return true;
    }

    @Override
    public boolean onMulitBegin(MultiMotionEvent event) {
        Mlog.e(this, "onMulitBegin");
        return true;
    }

    @Override
    public boolean onMultiEnd(MultiMotionEvent event) {
        Mlog.e(this, "onMultiEnd");
        return true;
    }

    @Override
    public boolean onMultiDrag(MultiMotionEvent event) {

        return true;
    }

    @Override
    public boolean onMultiUp(MultiMotionEvent multiEvent) {
        Mlog.e(this, "onMultiUp");
        return true;
    }




}
