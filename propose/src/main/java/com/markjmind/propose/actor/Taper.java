package com.markjmind.propose.actor;

import android.animation.Animator;
import android.util.Log;

import com.markjmind.propose.Motion;
import com.markjmind.propose.animation.TimeAnimation;
import com.markjmind.propose.animation.TimeValue;

import java.util.HashMap;
import java.util.Hashtable;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2016-04-12
 */
public class Taper {
    public Hashtable<Integer, TimeAnimation> pool;

    public void setAnimationPool(Hashtable<Integer, TimeAnimation> pool){
        this.pool = pool;
    }

    public boolean tap(Motion motion, long startDuration, long endDuration, long playDuration){
        if(startDuration>motion.getTotalDuration()){
            startDuration = motion.getTotalDuration();
        }else if(startDuration < 0){
            startDuration = 0;
        }
        if(endDuration>motion.getTotalDuration()){
            endDuration = motion.getTotalDuration();
        }else if(endDuration < 0){
            endDuration = 0;
        }
        if(startDuration==endDuration){
            return false;
        }

        AnimationTimeValue timeValue = new AnimationTimeValue(motion);
        timeValue.setValues(startDuration,endDuration);

        TimeAnimation timeAnimation = new TimeAnimation();

        timeAnimation.setDuration(playDuration);
        timeAnimation.addTimerValue(timeValue);
        timeAnimation.setAnimatorListener(new TimeAnimationEvent(timeAnimation));
        timeAnimation.start();
        return true;
    }

    private class AnimationTimeValue extends TimeValue {
        private Motion motion;
        protected AnimationTimeValue(Motion motion){
            this.motion = motion;
        }
        @Override
        public void onAnimationUpdate(long timeValue, HashMap<String, Object> params) {
            motion.move(timeValue);
        }
    }

    private class TimeAnimationEvent implements Animator.AnimatorListener {
        private int hashcode;
        private TimeAnimation timeAnimation;
        public TimeAnimationEvent(TimeAnimation timeAnimation){
            this.timeAnimation = timeAnimation;
            this.hashcode = timeAnimation.hashCode();
        }

        @Override
        public void onAnimationStart(Animator animation) {
            if(pool!=null) {
                pool.put(timeAnimation.hashCode(), timeAnimation);
            }
        }
        @Override
        public void onAnimationEnd(Animator animation) {
            if(pool!=null) {
                pool.remove(hashcode);
            }

        }
        @Override
        public void onAnimationCancel(Animator animation) {
            if(pool!=null && pool.containsKey(hashCode())){
                pool.remove(hashcode);
                Log.e("Taper", "Taper : onAnimationCancel");
            }
        }
        @Override
        public void onAnimationRepeat(Animator animation) {
            Log.e("Taper", "Taper : onAnimationRepeat");
        }

    }
}
