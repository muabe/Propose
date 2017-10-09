package com.markjmind.propose.listener;

/**
 * 터치 모션중 문지르기가 감지 되었을때 이벤트를 제공하는 리스너 인터페이스이다.
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2016-04-08
 */
public interface RubListener {

    /**
     * 문지르기 감지시 이벤트가 발생한다.
     * @param moveX 문지르기로 이동된 x좌표
     * @param moveY 문지르기로 이동된 y좌표
     * @return
     */
    boolean rub(float moveX, float moveY);
}
