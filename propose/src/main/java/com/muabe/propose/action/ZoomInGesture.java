package com.muabe.propose.action;

import com.muabe.propose.combination.combiner.ActionPlugin;
import com.muabe.propose.touch.detector.multi.MultiTouchEvent;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong - Oh)
 * @email markjmind@gmail.com
 * @since 2018-10-18
 */
public class ZoomInGesture extends ActionPlugin<MultiTouchEvent> {

    public ZoomInGesture() {
        super(1f);
    }


    @Override
    public float compete(MultiTouchEvent event) {
        return event.getScale()-1f;
    }

    @Override
    public float increase(MultiTouchEvent event) {
        return event.getScale()-1f;
    }
}
