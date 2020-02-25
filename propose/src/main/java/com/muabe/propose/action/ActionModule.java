package com.muabe.propose.action;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong - Oh)
 * @email markjmind@gmail.com
 * @since 2018-10-15
 */
public class ActionModule {

    public interface OnActionListener{
        boolean onAction(Object object);
    }

    private OnActionListener listener;

    public void bind(OnActionListener listener){
        this.listener = listener;
    }


    protected boolean onAction(Object event){
        if(listener != null) {
            return listener.onAction(event);
        }else{
            return false;
        }
    }


}
