package com.muabe.propose.guesture;

import java.util.ArrayList;

public class GesturePriority {
    private ArrayList<GesturePlugin> pluginList = new ArrayList<>();
    private ArrayList<GesturePlugin> playList = new ArrayList<>();

    public GesturePriority(){
        init();
    }

    public GesturePriority init(){
        pluginList.clear();
        return this;
    }

    public GesturePriority add(GesturePlugin gesturePlugin){
        pluginList.add(gesturePlugin);
        return this;
    }

    public GesturePriority add(ArrayList<GesturePlugin> list){
        pluginList.addAll(list);
        return this;
    }


}
