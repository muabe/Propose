package com.muabe.propose.touch.detector;

import com.muabe.propose.touch.detector.multi.MultiTouchEvent;
import com.muabe.propose.touch.detector.single.SingleTouchEvent;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 */

public interface OnTouchDetectListener {
    boolean onDown(SingleTouchEvent event);
    boolean onUp(SingleTouchEvent event);
    boolean onDrag(SingleTouchEvent event);

    boolean onMulitBegin(MultiTouchEvent event);
    boolean onMultiEnd(MultiTouchEvent event);
    boolean onMultiDrag(MultiTouchEvent event);
    boolean onMultiUp(MultiTouchEvent multiEvent);
}
