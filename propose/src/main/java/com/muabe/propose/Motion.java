package com.muabe.propose;

import com.muabe.propose.guesture.GesturePlugin;
import com.muabe.propose.guesture.Point;

/**
 * 모션에 따른 실제 움직임을 여기서 동기화 시켜주는 역할을한다.
 * BaseGesture에서 얻은 수치를 OnPlayListener에 전달 해준다.
 *
 */
public class Motion {
    public static final float INFINITY = 3.4E+38f;
    private Point point;
    private GesturePlugin gesturePlugin;
    private OnPlayListener playListener;


    public Motion(GesturePlugin gesturePlugin){
        this.point = new Point();
        this.gesturePlugin = gesturePlugin;
        this.gesturePlugin.init(point);
    }

    public void setMaxPoint(float maxPoint){
        point.setMax(maxPoint);
    }

    public GesturePlugin getGesture(){
        return gesturePlugin;
    }

    public void setOnPlayListener(OnPlayListener playListener){
        this.playListener = playListener;
    }

    boolean play(){
        if(playListener==null){
            return false;
        }
        return playListener.play(point.get());
    }

    public GesturePlugin getGesturePlugin(){
        return gesturePlugin;
    }

    public OnPlayListener getPlayListener() {
        return playListener;
    }
}
