package com.muabe.propose.combination;

import java.util.ArrayList;

public abstract class CombinationBridge<T extends CombinationBridge> extends Combination {
//    public T selfAnd(T... combinations) {
//        if (combinations.length > 0) {
//            return (T) Combine.all(copyArray(combinations));
//        }
//        return (T) this;
//    }
//
//    public T selfOr(T... combinations) {
//        if (combinations.length > 0) {
//            return (T) Combine.one(copyArray(combinations));
//        }
//        return (T) this;
//    }

    protected Combination[] copyArray(T[] motions) {
        Combination[] newArray = new Combination[motions.length + 1];
        newArray[0] = this;
        System.arraycopy(motions, 0, newArray, 1, motions.length);
        return newArray;
    }

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

    public T getParents(){
        return (T)super.parents;
    }

    public ArrayList<T> getChild(){
        return (ArrayList<T>)getChild(this.getClass());
    }
}
