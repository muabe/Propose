package com.muabe.propose.combination;

import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class Combination implements Priority {
    private String name = null;
    private RootManager rootManager = null;
    protected Combination parents;
    private int mode = Combine.ELEMENT;
    protected ArrayList<Combination> child = new ArrayList<>();
    protected ArrayList<Combination> cache = new ArrayList<>();
    protected boolean deletedCache = false;
    private int index = 0;


    void setMode(int mode){
        this.mode = mode;
    }
    public int getMode(){
        return mode;
    }

    public Combination getRoot(){
        if(parents == null){
            return this;
        }else{
            return parents.getRoot();
        }
    }

    public Combination getParents(){
        return parents;
    }

    void disableRoot(){
        this.rootManager = null;
    }

    public RootManager getRootManager(){
        Combination root = getRoot();
        if(root == this && rootManager == null){
            rootManager = new RootManager<>();
            if(root.mode == Combine.ELEMENT) {
                rootManager.addElement(this);
            }
        }
        return root.rootManager;
    }

    void setIndex(int index){
        this.index = index;
    }

    public int getIndex(){
        return index;
    }

    void clearCache(){
        Combine.clearCache(this);
    }

    @Override
    public String toString() {
        String name1;
        if(mode == Combine.ELEMENT){
            name1 = "ELEMENT("+name+")";
        }else if(mode == Combine.ONEOF){
            name1 = "ONE_OF:";
            for(int i=0;i<cache.size();i++){
                name1 += cache.get(i).toString();
            }
        }else{
            name1 = "WITH:";
            for(int i=0;i<cache.size();i++){
                if(i!=0){
                    name1 += "+";
                }
                name1 += cache.get(i).toString();
            }
        }
        return name1;
    }

    public Combination setName(String name){
        if(mode == Combine.ELEMENT && parents != null){ //parents == null 은 root가 없는 경우
            getRootManager().resetName(this, name);
        }else {
            this.name = name;
        }
        return this;
    }

    public String getName(){
        if(name == null && mode == Combine.ELEMENT) {
            return String.valueOf(index);
        }
        return name;
    }

    public <T>ArrayList<T> getChild(Class<T> t){
        return (ArrayList<T>)child;
    }

    void resetChild(ArrayList<Combination> list){
        child.clear();
        child.addAll(list);
    }

    protected <T extends Combination>T[] copyArray(T[] combinations) {
        T[] newArray = (T[])Array.newInstance(combinations.getClass().getComponentType(), combinations.length + 1);
        newArray[0] = (T)this;
        System.arraycopy(combinations, 0, newArray, 1, combinations.length);
        return newArray;
    }
}