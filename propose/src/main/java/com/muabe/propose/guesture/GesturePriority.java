package com.muabe.propose.guesture;

import com.muabe.propose.touch.detector.single.SingleTouchEvent;

import java.util.ArrayList;

public interface GesturePriority {
    public abstract float increaseDistance(SingleTouchEvent event);
}
