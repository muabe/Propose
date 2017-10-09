package com.markjmind.propose.listener;

import com.markjmind.propose.Motion;

/**
 * 전체 조합된 모션을 총괄하여 하나의 라이프 사이클로 이벤트를 받고 싶을때 사용하는 인터페이스이다.<br>
 * 전체적인 모션의 시작, 드래그, 종료가 감지 되었을때 이벤트를 제공하는 리스너를 제공한다.<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2016-05-11
 */
public interface ProposeListener {
    /**
     * 최초 모션의 시작시 이벤트가 발생한다.
     */
    void onStart();

    /**
     * 터치 모션의 드래그가 발생했을때 이벤트가 발생한다.
     * @param motion 해당 시점의 모션 객체
     * @param currDuration 진행되고있는 모션의 대한 현재 duration 값
     * @param totalDuration 진행되고있는 모션의 총 duration 값
     */
    void onScroll(Motion motion, long currDuration, long totalDuration);

    /**
     * 모든 모션이 종료되었을때 이벤트가 발생한다.
     */
    void onEnd();
}
