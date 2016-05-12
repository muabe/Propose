package com.markjmind.propose.listener;

import android.animation.Animator;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2016-05-12
 */
public abstract class AnimatorAdapter implements Animator.AnimatorListener {


    private boolean isReverse = false;

    public abstract void onStart(Animator animator, boolean isReverse);
    public abstract void onScroll(Animator animator, boolean isReverse, long currentDuration, long totalDuration);
    public abstract void onEnd(Animator animator, boolean isReverse);


    @Override
    public void onAnimationStart(Animator animation) {
        onStart(animation, isReverse);
    }
    @Override
    public void onAnimationEnd(Animator animation) {
        onEnd(animation, isReverse);

    }
    @Override
    public void onAnimationCancel(Animator animation) {
        onEnd(animation, isReverse);
    }
    @Override
    public void onAnimationRepeat(Animator animation) {
    }

    public void setReverse(boolean isReverse) {
        this.isReverse = isReverse;
    }

}
