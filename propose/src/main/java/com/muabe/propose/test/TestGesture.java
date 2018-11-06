package com.muabe.propose.test;

import com.muabe.propose.guesture.GesturePlugin;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong - Oh)
 * @email markjmind@gmail.com
 * @since 2018-10-18
 */
public class TestGesture extends GesturePlugin<TestEvent> {

    public TestGesture(float maxPoint) {
        super(maxPoint);
    }

    @Override
    public float preemp(TestEvent event) {
        return 5;
    }

    @Override
    public float increase(TestEvent event) {
        return 5;
    }
}
