package com.muabe.propose.combine;

import java.util.ArrayList;

public abstract class Combination<Param> implements ScanPriority<Param>{
    public String name="";
    protected Combination parents;
    protected int mode = Combine.ELEMENT;
    protected ArrayList<Combination<Param>> child = new ArrayList<>();
    protected ArrayList<Combination<Param>> cache = new ArrayList<>();
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
}
