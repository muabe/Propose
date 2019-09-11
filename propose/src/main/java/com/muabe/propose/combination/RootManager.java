package com.muabe.propose.combination;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RootManager<T extends Combination> {
    private ArrayList<T> elementList = new ArrayList<>();
    private Map<String, Integer> elementMap = new HashMap<>();

    synchronized void addElement(T combination){
        int index = elementMap.size();
        combination.setIndex(index);
        elementMap.put(combination.getName(), index);
        elementList.add(combination);
    }

    synchronized void addElementAll(ArrayList<T> eList){
        for (int i=0; i<eList.size(); i++) {
            addElement(eList.get(i));
        }
    }

    synchronized void resetName(Combination combination, String name){
        elementMap.remove(combination.getName());
        combination.setName(name);
        elementMap.put(name, combination.getIndex());
    }

    synchronized void clear(){
        elementMap.clear();
        elementList.clear();
    }

    public T getElement(int index){
        return elementList.get(index);
    }

    public T getElement(String name){
        return elementList.get(elementMap.get(name));
    }

    public int getElementSize(){
        return elementList.size();
    }

    ArrayList<T> getElementList() {
        return elementList;
    }

    Map<String, Integer> getElementMap(){
        return elementMap;
    }
}
