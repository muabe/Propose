package com.muabe.propose.combine;

import java.lang.reflect.Type;
import java.util.ArrayList;

public abstract class Combination implements Priority {
    public String name="";
    protected Combination parents;
    protected int mode = Combine.ELEMENT;
    protected ArrayList<Combination> child = new ArrayList<>();
    protected ArrayList<Combination> cache = new ArrayList<>();
    protected boolean deletedCache = false;

    @Override
    public String toString() {
        String name1;
        if(mode == Combine.ELEMENT){
            name1 = "ELEMENT("+name+")";
        }else if(mode == Combine.OR){
            name1 = "OR:";
            for(int i=0;i<cache.size();i++){
                name1 += cache.get(i).toString();
            }
        }else{
            name1 = "AND:";
            for(int i=0;i<cache.size();i++){
                if(i!=0){
                    name1 += "+";
                }
                name1 += cache.get(i).toString();
            }
        }
        return name1;
    }

    public void clearCache(){
        Combine.clearCache(this);
    }

    public void getChildList(Type t){

    }
}
