package com.muabe.propose.guesture;

import android.util.Log;

import com.muabe.propose.touch.detector.single.SingleTouchEvent;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong - Oh)
 * @email markjmind@gmail.com
 * @since 2018-10-18
 */
public class SingleTouchGesture extends GesturePlugin<SingleTouchEvent> {

    @Override
    public void get(SingleTouchEvent singleTouchEvent) {
        Log.d("SingleTouchGesture", "SingleTouchGesture go");
    }
}
