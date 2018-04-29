package com.muabe.propose.touch.detector;

import com.muabe.propose.touch.detector.multi.MultiMotionEvent;
import com.muabe.propose.touch.detector.single.SingleMotionEvent;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 */

public interface OnTouchDetectListener {
    boolean onDown(SingleMotionEvent event);
    boolean onUp(SingleMotionEvent event);
    boolean onDrag(SingleMotionEvent event);

    boolean onMulitBegin(MultiMotionEvent event);
    boolean onMultiEnd(MultiMotionEvent event);
    boolean onMultiDrag(MultiMotionEvent event);
    boolean onMultiUp(MultiMotionEvent multiEvent);
}
