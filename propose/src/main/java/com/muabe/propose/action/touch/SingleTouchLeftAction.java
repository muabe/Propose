package com.muabe.propose.action.touch;

import com.muabe.propose.action.ActionPlugin;
import com.muabe.propose.touch.detector.single.SingleTouchEvent;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong - Oh)
 * @email markjmind@gmail.com
 * @since 2018-11-01
 */
public class SingleTouchLeftAction extends ActionPlugin<SingleTouchEvent> {

    public SingleTouchLeftAction(float maxPoint) {
        super(maxPoint);
    }

    @Override
    public float compete(SingleTouchEvent event) {
        return increase(event);
    }

    @Override
    public float increase(SingleTouchEvent event) {
        return event.getPreX() - event.getX();
    }
}
