package com.muabe.propose.combination;

import java.util.ArrayList;

public abstract class CombinationBridge<T extends Combination> extends Combination {

    @Override
    public T getRoot() {
        return (T) super.getRoot();
    }

    @Override
    public T setName(String name) {
        return (T) super.setName(name);
    }

    @Override
    public RootManager<T> getRootManager() {
        return super.getRootManager();
    }

    @Override
    public T getParents(){
        return (T)super.getParents();
    }

    public ArrayList<T> getChild(){
        return (ArrayList<T>)super.getChild(this.getClass());
    }
}
