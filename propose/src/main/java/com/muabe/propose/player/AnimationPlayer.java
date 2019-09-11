package com.muabe.propose.player;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong - Oh)
 * @email markjmind@gmail.com
 * @since 2018-11-05
 */
public class AnimationPlayer{

//    public Player create(float ratio, ValueAnimator animator){
//        ValuAnimatorPlugin plugin = new ValuAnimatorPlugin(animator);
//        return new Player(ratio, plugin);
//    }

    public static Player create(float ratio, ObjectAnimator animator){
        ObjectAnimatorPlugin plugin = new ObjectAnimatorPlugin(animator);
        return new Player(ratio, plugin);
    }

    class ValuAnimatorPlugin implements PlayPlugin {
        long defaultDuration = 1000;
        ValueAnimator animator;

        ValuAnimatorPlugin(ValueAnimator animator){
            this.animator = animator;
            this.animator
                    .setDuration(defaultDuration)
                    .setInterpolator(null);
        }
        @Override
        public boolean play(Player player, float ratio) {
            long playDuration = (long)(defaultDuration*ratio);
            animator.setCurrentPlayTime(playDuration);
            return false;
        }
    }

    static class ObjectAnimatorPlugin implements PlayPlugin {
        long defaultDuration = 1000;
        ObjectAnimator animator;

        ObjectAnimatorPlugin(ObjectAnimator animator){
            this.animator = animator;
            this.animator
                    .setDuration(defaultDuration)
                    .setInterpolator(null);
        }
        @Override
        public boolean play(Player player, float ratio) {
            long playDuration = (long)(defaultDuration * ratio);
            animator.setCurrentPlayTime(playDuration);
            return false;
        }
    }

//    private ValueAnimator valueAnimator;
//
//    public AnimationPlayer(ValueAnimator valueAnimator){
//        this.valueAnimator = valueAnimator;
//        this.valueAnimator.setDuration(1000);
//        this.valueAnimator.setInterpolator(null);
//    }
//
//
//    @Override
//    public void play(float ratio, Class<? extends MotionPriority> motionPriorityClass, float point, float maxPoint) {
//        long duration = valueAnimator.getDuration();
//        long playTime = (long)(duration*point/maxPoint);
//        valueAnimator.setCurrentPlayTime(playTime);
//    }
}
