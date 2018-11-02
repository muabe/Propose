package com.muabe.propose;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import com.muabe.propose.touch.coords.metrix.MetrixCordinates;
import com.muabe.propose.touch.coords.window.WindowCoordinates;
import com.muabe.propose.touch.detector.OnTouchDetectListener;
import com.muabe.propose.touch.detector.TouchDetectAdapter;

/**
 * <br>捲土重來<br>
 * TouchEvent의 절대 좌표를 변환하고
 * 새로이 절대좌표에 대한 Event를 재생성하여
 * TouchDetectAdapter에 전달한다.
 * @author 오재웅(JaeWoong-Oh)
 */

public class TouchDetector{

    private TouchDetectAdapter touchDetectAdapter;
    private boolean isWindow;

    public TouchDetector(Context context, OnTouchDetectListener listener) {
        touchDetectAdapter = new TouchDetectAdapter(context, listener);
        isWindow = WindowCoordinates.isBindWindow();
    }

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





}
