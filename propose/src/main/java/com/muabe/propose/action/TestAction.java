package com.muabe.propose.action;

import com.muabe.propose.guesture.TestEvent;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong - Oh)
 * @email markjmind@gmail.com
 * @since 2018-10-18
 */
public class TestAction extends ActionModule<TestEvent> {

    public void go(TestEvent event){
        callScan(event);
    }
}
