package com.muabe.propose.touch.coords.window;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.Window;

/**
 * <br>捲土重來<br>
 * 윈도우 기반 좌표 변환을 담당한다.<br>
 *
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 */

public class WindowCoordinates {
    private static Window window;
    private static AbsolutenessCoordinates absolutenessCoordinates = new AbsolutenessCoordinates();

    private WindowCoordinates(){}

    public static void bindWindow(Window window){
        WindowTouchEventAdapter.dispatchTouchEvent(window, new AbsolutenessCoordinates.OnDispatchTouchListener() {
            @Override
            public void onDispatchTouchEvent(MotionEvent event) {
                WindowCoordinates.absolutenessCoordinates.dispatchTouchEvent(event);
            }
        });
        WindowCoordinates.window = window;
    }

    /**
     * TODO static 조심, 윈도우가 하나가 아닐수 있음
     * acitivity가 여러곳에 쓰일수 있으니 여러곳의 Window가 존재할수 있다는 얘기
     * 바인드를 한번하기 때문에 Activity가 이곳저곳으로 전환되게 되면 문제가 생길수 있다.
     * Window라는 것이 여러 Activity가 공유하지 않으면 큰 문제가 생길수 있다.
     * TODO Window가 공유되는지 확인이 필요함
     *
     * 한번만 바인드하고 Propose 객체를 여러곳에서 신경쓰지 않고 싶은데
     * Window가 Activity간 공유되는지 확인이 필요함
     *
     * @param activity
     */

    public static void bindWindow(Activity activity){
        WindowCoordinates.bindWindow(activity.getWindow());
    }

    public static MotionEvent convertMotionEvent(MotionEvent event) {
        return WindowCoordinates.absolutenessCoordinates.convertMotionEvent(event);
    }

    public static boolean isBindWindow(){
        if(WindowCoordinates.window==null){
            return false;
        }
        return WindowTouchEventAdapter.isRegistry(WindowCoordinates.window);
    }
}
