package com.muabe.propose.touch.coords.metrix;

import android.view.MotionEvent;
import android.view.View;

/**
 * <br>捲土重來<br>
 * 절대좌표로 MotionEvent를 변환한다.<br>
 * 싱크터치는 잘되지만 Native에서 멀치 터치에 대한<br>
 * MetrixCordinates가 지원되지 않기 때문에(정확히는 90도,270도만)<br>
 * 싱크터치만 사용 가능하다.<br>
 * 멀티 터치를 위해서는 WindowCoordinates를 사용하기 바란다.
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 */

public class MetrixCordinates {
    public static MotionEvent convertMotionEvent(MotionEvent event, View touchView, boolean isRaw) {
        if(isRaw) {
            event.offsetLocation(event.getRawX() - event.getX(0), event.getRawY() - event.getY(0));
        }
        MotionEvent motionEvent = MotionEvent.obtain(event);
        motionEvent.transform(touchView.getMatrix());
        return motionEvent;
    }

    public static MotionEvent convertMotionEvent(MotionEvent event, View touchView) {
        return MetrixCordinates.convertMotionEvent(event, touchView, false);
    }
}
