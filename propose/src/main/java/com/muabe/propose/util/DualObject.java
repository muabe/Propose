package com.muabe.propose.util;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 */

public class DualObject<T> {
    private T t1, t2;

    public DualObject(){
    }

    public T differentObject(T t){
        if(t.equals(t1)){
            return t2;
        }else{
            return t1;
        }
    }


}
