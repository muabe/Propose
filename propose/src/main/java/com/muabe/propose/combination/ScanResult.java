package com.muabe.propose.combination;

import java.util.ArrayList;

public class ScanResult<T extends Combination>{
    private ArrayList<T> scanList = new ArrayList<>();
    private ArrayList<T> deleteList = new ArrayList<>();
    private ArrayList<T> insertList = new ArrayList<>();

    public ArrayList<T> getScanList(){
        return this.scanList;
    }

    public ArrayList<T> getDeleteList(){
        return this.deleteList;
    }

    public ArrayList<T> getInsertList(){
        return this.insertList;
    }

    public void add(T combination){
        scanList.add(combination);
    }

    public void addAll(ScanResult result){
        scanList.addAll(result.scanList);
        deleteList.addAll(result.deleteList);
    }

    public void addDelete(T combination){
        deleteList.add(combination);
    }
}
