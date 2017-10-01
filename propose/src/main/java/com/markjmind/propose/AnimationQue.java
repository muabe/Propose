package com.markjmind.propose;

import com.markjmind.propose.animation.TimeAnimation;

import java.util.Hashtable;

/**
 * <br>捲土重來<br>
 * 애니메이션 관리하는 Queue 클래스 이다.<br>
 * Map 형태로 이루어져 있으며 Key 로는 객체 해쉬코드를 받는다.<br>
 * 해쉬코드를 받는 이유는 객체의 메모리 중복을 방지하기 위함이다.<br>
 * value로는 TimeAnimation을 받는다.<br>
 *  <br> * 외부에서 접근하거나 변경할수 없다(접근 지정가 public이 아님)<br>
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2016-05-05
 */
abstract class AnimationQue extends Hashtable<Integer, TimeAnimation> {

    /**
     * 애니메이션을 큐에 추가 한다.
     * @param key 모션객체 해쉬코드
     * @param value TimeAnimation
     * @return 추가한 TimeAnimation
     */
    @Override
    public synchronized TimeAnimation put(Integer key, TimeAnimation value) {
        if(size()==0){
            animationStart();
        }
        return super.put(key, value);
    }

    /**
     * 큐에서 삭제한다.
     * @param key TimeAnimation 객체
     * @return 삭제된 TimeAnimation
     */
    @Override
    public synchronized TimeAnimation remove(Object key) {
        TimeAnimation listener = super.remove(key);
        if(size()==0){
            animationEnd();
        }
        return listener;
    }

    /**
     * 큐에 있는 모든 애니메이션을 중지한다.
     */
    protected synchronized void cancelAll(){
        TimeAnimation[] timeAnimation = new TimeAnimation[size()];
        values().toArray(timeAnimation);
        clear();
        for(int i=0;i<timeAnimation.length;i++){
            timeAnimation[i].cancel();
        }
    }

    /**
     * 큐에 있는 애니메이션을 중지한다.
     * @param hashcode 삭제할 객체 해쉬코드
     */
    protected synchronized void cancel(int hashcode){
        TimeAnimation timeAnimation = get(hashcode);
        remove(timeAnimation.hashCode());
        timeAnimation.cancel();
    }

    /**
     * 애니메이션이 실행될때 이벤트가 호출된다.
     */
    protected abstract void animationStart();

    /**
     * 애니메이션이 중지될때 이벤트가 호출된다.
     */
    protected abstract void animationEnd();
}
