package com.muabe.propose.history;

import com.muabe.propose.Motion;
import com.muabe.propose.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class History {
    HashMap<Motion, ArrayList<Player>> history = new HashMap<>();

    public void addMotion(Motion motion, float ratio){
        if(history.containsKey(motion)){

        }else{
            history.put(motion, null);
            newMotion(motion, ratio);

        }
    }




    private void newMotion(Motion motion, float ratio){

    }
}
