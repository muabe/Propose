package com.markjmind.propose.listener;

import com.markjmind.propose.Motion;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2016-05-11
 */
public interface ProposeListener {
    void onStart();
    void onScroll(Motion motion, long currDuration, long totalDuration);
    void onEnd();
}
