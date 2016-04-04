package com.markjmind.propose3;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2016-03-28
 */
class ActionState {
    protected static final int STOP = 0;
    protected static final int SCROLL = 1;
    protected static final int FlING = 2;
    protected static final int ANIMATION = 3;

      private int action;

    protected ActionState(){
        action = STOP;
    }


    public int getAction() {
        return action;
    }

    public void setAction(int action){
        this.action = action;
    }
}
