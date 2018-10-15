package com.muabe.propose.filter;

import com.muabe.propose.touch.detector.single.SingleTouchEvent;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong - Oh)
 * @email markjmind@gmail.com
 * @since 2018-10-15
 */
public class SingleTouchFilter extends MotionFilter<SingleTouchEvent> {

    @Override
    public String getTypeName() {
        return "SingleTouch";
    }
}
