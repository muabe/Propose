package com.markjmind.propose;

import java.util.ArrayList;

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
    private int state;

    private ArrayList<Motion> targetList = new ArrayList<>();
    private ArrayList<StateObserver> observers = new ArrayList<>();

    public interface StateObserver{
        void onChangeState(int preState, int currState, ArrayList<Motion> targetList);
        void scroll(Motion motion);
    }

    protected ActionState(){
        state = STOP;
    }

    protected void addObserver(StateObserver observer){
        this.observers.add(observer);
    }

    protected void addTarget(Motion motion){
        if(!targetList.contains(motion)) {
            targetList.add(motion);
        }
    }

    public int getState() {
        return state;
    }

    public synchronized void setState(int state){
        if(this.state != state) {
            for (StateObserver observer : observers) {
                observer.onChangeState(this.state, state, targetList);
            }
            this.state = state;
            if (this.state == STOP) {
                targetList.clear();
            }
        }
    }
    public synchronized void scroll(Motion motion){
        for (StateObserver observer : observers) {
            observer.scroll(motion);
        }
    }


}
