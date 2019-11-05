package com.muabe.propose.action;

import com.muabe.propose.touch.detector.single.SingleTouchEvent;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong - Oh)
 * @email markjmind@gmail.com
 * @since 2018-11-01
 */
public class SingleRightLotationGesture extends ActionPlugin<SingleTouchEvent> {

    public SingleRightLotationGesture(float maxPoint) {
        super(maxPoint);
    }

    @Override
    public float compete(SingleTouchEvent singleTouchEvent) {
        return 0;
    }

    @Override
    public float increase(SingleTouchEvent event) {
        return 0;
    }
}
