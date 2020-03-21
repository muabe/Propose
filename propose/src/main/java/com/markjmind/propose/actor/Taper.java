package com.markjmind.propose.actor;

import android.animation.Animator;
import android.util.Log;

import com.markjmind.propose.AnimationQue;
import com.markjmind.propose.Motion;
import com.markjmind.propose.animation.TimeAnimation;
import com.markjmind.propose.animation.TimeValue;

import java.util.HashMap;
import java.util.Hashtable;

/**
 * 탭(클릭) 발생시 화면에 view를 애니메이션에 따라 play 시키는 구현체 클래스 이다.<br>
 * 지정된 duration 만큼 애니메이션을 play한다.<br>
 * Taper 클래스는 View의 탭 대해서 많이 사용되고 있지만<br>
 * 애니메이션 표현이 필요한 어떤곳에서나 사용이 가능하다
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2016-04-12
 */
public class Taper {
    /** 조홥된 애니메이션에 대한 play 순서 큐 개체 */
    public AnimationQue que;

    private Integer animationHashcode = null;
    /**
     * 애니메이션큐 객체를 설정한다.
     * @param que 애니메이션큐 Hashtable 객체
     */
    public void setAnimationQue(AnimationQue que){
        this.que = que;
    }

    /**
     * 해당 duration에 따른 애니메이션을 play한다.
     * @param motion 모션 객체
     * @param startDuration 시작 duration
     * @param endDuration 종료 dration
     * @param playDuration 애니메이션 play시간
     * @return
     */
//    public boolean tap(Motion motion, long startDuration, long endDuration, long playDuration){
//        Integer value = startAnimation(motion, startDuration, endDuration, playDuration);
//        return value != null;
//    }

    public Integer startAnimation(Motion motion, long startDuration, long endDuration, long playDuration){
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
            return null;
        }

        AnimationTimeValue timeValue = new AnimationTimeValue(motion);
        timeValue.setValues(startDuration,endDuration);

        TimeAnimation timeAnimation = new TimeAnimation();

        timeAnimation.setDuration(playDuration);
        timeAnimation.addTimerValue(timeValue);
        TimeAnimationEvent tae = new TimeAnimationEvent(timeAnimation);
        timeAnimation.setAnimatorListener(tae);
        animationHashcode = tae.hashcode;
        timeAnimation.start();
        return animationHashcode;
    }

    public void cancel(Integer hashcode){
        if(hashcode != null) {
            que.cancel(hashcode);
        }
    }

    public void cancel(){
        if(animationHashcode != null) {
            que.cancel(animationHashcode);
        }
    }
    /**
     * TimeValue에서 필요한 애니메이션 구동에 관한 인터페이스 구현
     */
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

    /**
     * 애니메이션이 구동되는 동안의 상태를 감지하고 개발 인터페이스를 제공한다.
     */
    private class TimeAnimationEvent implements Animator.AnimatorListener {
        private int hashcode;
        private TimeAnimation timeAnimation;
        public TimeAnimationEvent(TimeAnimation timeAnimation){
            this.timeAnimation = timeAnimation;
            this.hashcode = timeAnimation.hashCode();
        }

        /**
         * 애니메이션이 시작될때 이벤트 감지
         * @param animation 해당 애니메이션 객체
         */
        @Override
        public void onAnimationStart(Animator animation) {
            if(que !=null) {
                que.put(hashcode, timeAnimation);
                animationHashcode = hashcode;
            }
        }

        /**
         * 애니메이션이 종료될때 이벤트 감지
         * @param animation 해당 애니메이션 객체
         */
        @Override
        public void onAnimationEnd(Animator animation) {
            if(que !=null) {
                que.remove(hashcode);
            }
            animationHashcode = null;

        }

        /**
         * 애니메이션이 취소될때 이벤트 감지
         * @param animation 해당 애니메이션 객체
         */
        @Override
        public void onAnimationCancel(Animator animation) {
            if(que !=null && que.containsKey(hashCode())){
                que.remove(hashcode);
                Log.e("Taper", "Taper : onAnimationCancel");
            }
            animationHashcode = null;
        }

        /**
         * 애니메이션이 재시작될때 이벤트 감지
         * @param animation 해당 애니메이션 객체
         */
        @Override
        public void onAnimationRepeat(Animator animation) {
            Log.e("Taper", "Taper : onAnimationRepeat");
        }

        int getHashcode(){
            return hashcode;
        }
    }
}
