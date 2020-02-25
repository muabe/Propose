package com.muabe.propose.action.touch.module;

import com.muabe.propose.action.ActionModule;
import com.muabe.propose.touch.detector.OnTouchDetectListener;
import com.muabe.propose.touch.detector.multi.MultiTouchEvent;
import com.muabe.propose.touch.detector.single.SingleTouchEvent;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong - Oh)
 * @email markjmind@gmail.com
 * @since 2018-10-15
 */
class SingleTouchModule extends ActionModule implements OnTouchDetectListener {

    @Override
    public boolean onDown(SingleTouchEvent event) {
        return false;
    }

    @Override
    public boolean onUp(SingleTouchEvent event) {
        return false;
    }

    @Override
    public boolean onDrag(SingleTouchEvent event) {
        return onAction(event);
    }

    @Override
    public boolean onMulitBegin(MultiTouchEvent event) {
        return false;
    }

    @Override
    public boolean onMultiEnd(MultiTouchEvent event) {
        return false;
    }

    @Override
    public boolean onMultiDrag(MultiTouchEvent event) {
        return false;
    }

    @Override
    public boolean onMultiUp(MultiTouchEvent multiEvent) {
        return false;
    }
}
