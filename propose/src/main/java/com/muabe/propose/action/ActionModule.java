package com.muabe.propose.action;

import com.muabe.propose.Propose;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong - Oh)
 * @email markjmind@gmail.com
 * @since 2018-10-15
 */
public class ActionModule{
    private Propose propose;

    public void bind(Propose propose){
        this.propose = propose;
    }


    protected boolean callScan(Object event){
        if(propose!=null) {
            return propose.callScan(event);
        }else{
            return false;
        }
    }
}
