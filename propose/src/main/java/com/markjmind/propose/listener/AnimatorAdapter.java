package com.markjmind.propose.listener;

import android.animation.Animator;

/**
 * 하나의 애니메이션의 시작, 진행, 끝이 감지 되었을때 이벤트를 제공하는 리스너 인터페이스이다.<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2016-05-12
 */
public abstract class AnimatorAdapter implements Animator.AnimatorListener {

    private boolean isReverse = false;

    /**
     * 애니메이션의 시작시 발생하는 리스너 함수
     * @param animator 해당 애니메이션
     * @param isReverse 애니메이션의 방향(true일 경우 반대방향)
     */
    public abstract void onStart(Animator animator, boolean isReverse);


    /**
     * 애니메이션의 진행시 발생하는 리스너 함수
     * @param animator 해당 애니메이션
     * @param isReverse 애니메이션의 방향(true일 경우 반대방향)
     * @param currentDuration
     * @param totalDuration
     */
    public abstract void onScroll(Animator animator, boolean isReverse, long currentDuration, long totalDuration);


    /**
     * 애니메이션의 종료시 발생하는 리스너 함수
     * @param animator 해당 애니메이션
     * @param isReverse 애니메이션의 방향(true일 경우 반대방향)
     */
    public abstract void onEnd(Animator animator, boolean isReverse);


    /**
     * 애니메이션 시작시 이벤트를 받아오고 onStart로 이벤트를 넘긴다.
     * @param animation 해당 애니메이션
     */
    @Override
    public void onAnimationStart(Animator animation) {
        onStart(animation, isReverse);
    }


    /**
     * 애니메이션 종료시 이벤트를 받아오고 onEnd로 이벤트를 넘긴다.
     * @param animation 해당 애니메이션
     */
    @Override
    public void onAnimationEnd(Animator animation) {
        onEnd(animation, isReverse);

    }

    /**
     * 애니메이션 취소시 이벤트를 받아오고 onEnd로 이벤트를 넘긴다.
     * @param animation 해당 애니메이션
     */
    @Override
    public void onAnimationCancel(Animator animation) {
        onEnd(animation, isReverse);
    }

    /**
     * 여기서 애니메이션 반복에 대한 이벤트는 사용하지 않는다.
     * @param animation 해당 애니메이션
     */
    @Override
    public void onAnimationRepeat(Animator animation) {}

    /**
     * 애니메이션의 방향을 설정한다.
     * @param isReverse 애니메이션의 방향(true일 경우 반대방향)
     */
    public void setReverse(boolean isReverse) {
        this.isReverse = isReverse;
    }

}
