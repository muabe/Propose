package com.markjmind.test.player;

import java.util.ArrayList;

public class PlayerUtils {
    public static void actlistener(ArrayList<PlayerEventListener> listeners){
        for(PlayerEventListener listener : listeners){
            listener.on();
        }
    }
}
