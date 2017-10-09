package com.markjmind.propose.listener;

import com.markjmind.propose.Motion;

/**
 * 하나의 터치 모션에 대해 시작, 드래그, 종료가 감지 되었을때 이벤트를 제공하는 리스너 인터페이스이다.
 * Created by MarkJ on 2016-05-12.
 */
public interface MotionListener {

    /**
     * 모션의 시작시 이벤트가 발생한다.
     * @param motion 해당 모션 객체
     */
    void onStart(Motion motion);

    /**
     * 터치 모션의 드래그가 발생했을때 이벤트가 발생한다.
     * @param motion 해당 모션 객체
     * @param currDuration 진행되고있는 모션의 대한 현재 duration 값
     * @param totalDuration 진행되고있는 모션의 총 duration 값
     */
    void onScroll(Motion motion, long currDuration, long totalDuration);


    /**
     * 모션의 종료시 이벤트가 발생한다.
     * @param motion 해당 모션 객체
     */
    void onEnd(Motion motion) ;
}
