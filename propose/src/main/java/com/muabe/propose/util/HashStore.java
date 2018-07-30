package com.muabe.propose.util;

import java.util.LinkedHashMap;

public class HashStore<T> extends LinkedHashMap<String, T> {

    public String[] getKeys(){
        if(this.size()==0)
        {
            return new String[0];
        }

        String[] keys = new String[this.size()];
        this.keySet().toArray(keys);
        return keys;
    }

    public T[] getValues(){
        if(this.size()==0)
            return null;
        T[] values = (T[])this.values().toArray();
        return values;
    }
}
