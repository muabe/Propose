package com.muabe.propose.action;

import com.muabe.propose.motion.ActionPlugin;
import com.muabe.propose.touch.detector.single.SingleTouchEvent;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong - Oh)
 * @email markjmind@gmail.com
 * @since 2018-10-18
 */
public class SingleTouchRightGesture extends ActionPlugin<SingleTouchEvent> {

    public SingleTouchRightGesture(float maxPoint) {
        super(maxPoint);
    }

    @Override
    public float compete(SingleTouchEvent event) {
        return increase(event);
    }

    @Override
    public float increase(SingleTouchEvent event) {
        return event.getX() - event.getPreX();
    }
}
