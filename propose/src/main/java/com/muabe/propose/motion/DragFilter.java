package com.muabe.propose.motion;

import com.muabe.propose.touch.detector.multi.MultiTouchEvent;
import com.muabe.propose.touch.detector.single.SingleTouchEvent;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 */

public interface DragFilter {
    void addMotion(Motion motion);
    boolean onDrag(SingleTouchEvent event);
    boolean onMultiDrag(MultiTouchEvent event);
}
