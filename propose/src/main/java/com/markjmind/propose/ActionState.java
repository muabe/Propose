package com.markjmind.propose;

import java.util.ArrayList;

/**
 * 모션의 상태를 감지한다.<br>
 * 옵저버 패턴으로 등록된 모션에 대하여 상태변화가 있을때 이벤트 발생을 노티해준다.<br>
 * 상태<br>
 *  - STOP : 정지된 상태 <br>
 *  - SCROLL : 이동중인 상태 <br>
 *  - FlING : 터치후 가속도가 붙은 상태 <br>
 *  - ANIMATION : 모션 종료 후 애니메이션이 동작 중인 상태 <br>
 *  <br> * 외부에서 접근하거나 변경할수 없다(접근 지정가 public이 아님)<br>
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2016-03-28
 */
class ActionState {
    /** 정지된 상태 상수 */
    protected static final int STOP = 0;
    /** 이동중인 상태 상수 */
    protected static final int SCROLL = 1;
    /** 터치후 가속도가 붙은 상태 상수 */
    protected static final int FlING = 2;
    /** 모션 종료 후 애니메이션이 동작 중인 상태 상수 */
    protected static final int ANIMATION = 3;
    /** 현재 상태 상수*/
    private int state;

    /**
     * 적용될 모션 타겟 리스트 */
    private ArrayList<Motion> targetList = new ArrayList<>();
    /** 감태를 감지 옵저버 리스트 */
    private ArrayList<StateObserver> observers = new ArrayList<>();


    /**
     * 상태를 변화 이벤트가 필요할때 등록하는 인터페이스<br>
     */
    public interface StateObserver{
        /**
         * 상태 변화가 감지 되었을때 이벤트가 발생한다.
         * @param preState 이전 상태
         * @param currState 현재상태
         * @param targetList 타겟 모션 정보
         */
        void onChangeState(int preState, int currState, ArrayList<Motion> targetList);

        /**
         * 상태가 이동 중일 경우, 이동변화에 대해 이벤트가 발생한다.
         * @param motion 이동중인 모션
         */
        void scroll(Motion motion);
    }

    /** 기본생성자 */
    protected ActionState(){
        state = STOP;
    }

    /** 상태 옵저버 추가 */
    protected void addObserver(StateObserver observer){
        this.observers.add(observer);
    }

    /** 타겟 모션 추가 */
    protected void addTarget(Motion motion){
        if(!targetList.contains(motion)) {
            targetList.add(motion);
        }
    }

    /** 현재 상태를 가져온다 */
    public int getState() {
        return state;
    }

    /**
     * 현재 상태를 변경하고 상태에 대한 이벤트를 날려준다.
     * @param state 변경할 상태
     */
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

    /**
     * 모션을 이동시키고 이동변화에 대한 이벤트를 날려준다.
     * @param motion
     */
    public synchronized void scroll(Motion motion){
        for (StateObserver observer : observers) {
            observer.scroll(motion);
        }
    }


}
