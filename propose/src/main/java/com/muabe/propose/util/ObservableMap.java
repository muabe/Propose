package com.muabe.propose.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 */

public class ObservableMap<K,V> extends LinkedHashMap<K,V>{

    public List<K> getKeys(){
        return new ArrayList<K>(keySet());
    }

    public V get(int index){
        return super.get(getKeys().get(index));
    }

    public List<V> getValues(){
        return new ArrayList<V>(values());
    }
}
