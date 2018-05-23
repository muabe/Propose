package com.muabe.propose.motion;

import com.muabe.propose.touch.detector.multi.MultiMotionEvent;
import com.muabe.propose.touch.detector.single.SingleMotionEvent;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 */

public interface DragFilter {
    void addMotion(Motion motion);
    boolean onDrag(SingleMotionEvent event);
    boolean onMultiDrag(MultiMotionEvent event);
}
