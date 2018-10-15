package com.muabe.propose.filter;

import android.view.MotionEvent;
import android.view.View;

import com.muabe.propose.TouchDetector;
import com.muabe.propose.touch.detector.OnTouchDetectListener;
import com.muabe.propose.touch.detector.multi.MultiTouchEvent;
import com.muabe.propose.touch.detector.single.SingleTouchEvent;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong - Oh)
 * @email markjmind@gmail.com
 * @since 2018-10-15
 */
public class TouchController implements View.OnTouchListener, OnTouchDetectListener {
    TouchDetector touchDetector;
    SingleTouchFilter singleTouchFilter;

    public TouchController(View view){
        touchDetector = new TouchDetector(view.getContext(), this);
        singleTouchFilter = new SingleTouchFilter();
        view.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return touchDetector.onTouchEvent(view, motionEvent);
    }

    @Override
    public boolean onDown(SingleTouchEvent event) {
        return false;
    }

    @Override
    public boolean onUp(SingleTouchEvent event) {
        return false;
    }

    @Override
    public boolean onDrag(SingleTouchEvent event) {
        return false;
    }

    @Override
    public boolean onMulitBegin(MultiTouchEvent event) {
        return false;
    }

    @Override
    public boolean onMultiEnd(MultiTouchEvent event) {
        return false;
    }

    @Override
    public boolean onMultiDrag(MultiTouchEvent event) {
        return false;
    }

    @Override
    public boolean onMultiUp(MultiTouchEvent multiEvent) {
        return false;
    }
}
