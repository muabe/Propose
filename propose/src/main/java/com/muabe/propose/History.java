package com.muabe.propose;

import java.util.ArrayList;

public class History {
    ArrayList<ArrayList<Motion>> scanList = new ArrayList<>();
    ArrayList<Player> prePlayList = new ArrayList<>();
    ArrayList<Player> newPlayList = new ArrayList<>();
    ArrayList<Player> playList = new ArrayList<>();


    public History init(){
        prePlayList = playList;
        playList = new ArrayList<>();
        newPlayList.clear();
        return this;
    }

    public History addPlayList(Player player){
        playList.add(player);
        if(!prePlayList.contains(player)){
            newPlayList.add(player);
        }
        return this;
    }

    public ArrayList<Player> newPlayList(){
        return newPlayList;
    }

    public ArrayList<Player> delPlayList(){
        ArrayList<Player> delList = new ArrayList<>();
        for(Player player : prePlayList){
            if(!playList.contains(player)){
                delList.add(player);
            }
        }
        return  delList;
    }
}
