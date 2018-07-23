package com.muabe.propose;

import com.muabe.propose.guesture.BaseGesture;
import com.muabe.propose.guesture.Point;

/**
 * 모션에 따른 실제 움직임을 여기서 동기화 시켜주는 역할을한다.
 * BaseGesture에서 얻은 수치를 OnPlayListener에 전달 해준다.
 *
 */
public class Motion2 {
    public static final float INFINITY = 3.4E+38f;
    private Point point;
    private BaseGesture baseGesture;
    private OnPlayListener playListener;


    public Motion2(BaseGesture baseGesture){
        this.point = new Point();
        this.baseGesture = baseGesture;
        this.baseGesture.init(point);
    }

    public void setMaxDistance(float maxDistance){
        point.setMax(maxDistance);
    }

    public BaseGesture getGesture(){
        return baseGesture;
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
}
