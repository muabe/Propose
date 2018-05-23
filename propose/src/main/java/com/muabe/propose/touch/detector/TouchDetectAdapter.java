package com.muabe.propose.touch.detector;

import android.content.Context;
import android.view.MotionEvent;

import com.muabe.propose.touch.detector.multi.MultiDetector;
import com.muabe.propose.touch.detector.single.SingleDetector;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 */

public class TouchDetectAdapter {
    private MultiDetector multiAdater;
    private SingleDetector singleAdapter;

    public TouchDetectAdapter(Context context, OnTouchDetectListener listener){
        this.multiAdater = new MultiDetector(context, listener);
        this.singleAdapter = new SingleDetector(context, listener);
    }

    public boolean onTouchEvent(MotionEvent event){
        return multiAdater.onTouchEvent(event) || singleAdapter.onTouchEvent(event);
    }


}
