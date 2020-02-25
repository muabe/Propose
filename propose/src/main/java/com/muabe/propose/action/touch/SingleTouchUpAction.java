package com.muabe.propose.action.touch;

import com.muabe.propose.action.ActionPlugin;
import com.muabe.propose.touch.detector.single.SingleTouchEvent;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong - Oh)
 * @email markjmind@gmail.com
 * @since 2018-10-18
 */
public class SingleTouchUpAction extends ActionPlugin<SingleTouchEvent> {

    public SingleTouchUpAction(float maxPoint) {
        super(maxPoint);
    }

    @Override
    public float compete(SingleTouchEvent event) {
        return increase(event);
    }

    @Override
    public float increase(SingleTouchEvent event) {
        return event.getPreY() - event.getY();
    }
}
