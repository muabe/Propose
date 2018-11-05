package com.muabe.propose.player;

import android.animation.ValueAnimator;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong - Oh)
 * @email markjmind@gmail.com
 * @since 2018-11-05
 */
public class AnimationPlayer implements PlayListener{
    private ValueAnimator valueAnimator;

    public AnimationPlayer(ValueAnimator valueAnimator){
        this.valueAnimator = valueAnimator;
        this.valueAnimator.setDuration(1000);
        this.valueAnimator.setInterpolator(null);
    }

    @Override
    public void play(float point, float maxPoint) {
        long duration = valueAnimator.getDuration();
        long playTime = (long)(duration*point/maxPoint);
        valueAnimator.setCurrentPlayTime(playTime);
    }
}
