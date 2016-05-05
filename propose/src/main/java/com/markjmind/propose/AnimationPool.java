package com.markjmind.propose;

import com.markjmind.propose.animation.TimeAnimation;

import java.util.Hashtable;
import java.util.Iterator;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2016-05-05
 */
abstract class AnimationPool extends Hashtable<Integer, TimeAnimation> {

    @Override
    public synchronized TimeAnimation put(Integer key, TimeAnimation value) {
        if(size()==0){
            animationStart();
        }
        return super.put(key, value);
    }

    @Override
    public synchronized TimeAnimation remove(Object key) {
        TimeAnimation listener = super.remove(key);
        if(size()==0){
            animationEnd();
        }
        return listener;
    }

    protected synchronized void cancelAll(){
        Iterator iterator = this.keySet().iterator();
        TimeAnimation[] timeAnimation = new TimeAnimation[size()];
        values().toArray(timeAnimation);
        clear();
        for(int i=0;i<timeAnimation.length;i++){
            timeAnimation[i].cancel();
        }
//        while(iterator.hasNext()){
//            int hashcode = (int)iterator.next();
//            TimeAnimation timeAnimation = get(hashcode);
//            timeAnimation.cancel();
//        }
    }

    protected synchronized void cancel(int hashcode){
        TimeAnimation timeAnimation = get(hashcode);
        timeAnimation.cancel();
    }

    protected abstract void animationStart();
    protected abstract void animationEnd();
}
