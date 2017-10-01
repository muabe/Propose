package com.markjmind.propose;

/**
 *
 * 애니메이션 루프 상태<br>
 * 루프의 방향을 나태낸다.
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2016-05-09
 */
public class Loop {
    /** 루프가 진행되지 않을때 */
    public static int NONE = 0;
    /** 애니메이션이 반대방향으로  루프를 돌때 */
    public static int REVERSE = 1;
    /** 애니메이션이 정방향으로 루프를 돌때*/
    public static int RESTART= 2;
}
