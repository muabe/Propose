package com.markjmind.propose.listener;

import com.markjmind.propose.Motion;

/**
 * Created by MarkJ on 2016-05-12.
 */
public interface MotionListener {
    void onStart(Motion motion);
    void onScroll(Motion motion, long currDuration, long totalDuration);
    void onEnd(Motion motion) ;
}
