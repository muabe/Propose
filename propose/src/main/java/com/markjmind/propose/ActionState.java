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
        void onChangeState(int state, ArrayList<Motion> targetList);
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
        this.state = state;
        for(StateObserver observer : observers){
            observer.onChangeState(state, targetList);
        }
        if(this.state==STOP){
            targetList.clear();
        }
    }


}
