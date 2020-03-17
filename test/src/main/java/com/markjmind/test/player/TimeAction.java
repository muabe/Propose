package com.markjmind.test.player;

import android.os.Handler;

import java.util.ArrayList;
import java.util.Hashtable;


public class TimeAction {
    private Hashtable<Long, ArrayList<ActionInfo>> actions = new Hashtable<>();
    final static int secDiv = 1000000;
    private Handler handler= new Handler();

    void addAction(long time, OnTimeActionListener listener){
        long timeKey = time/secDiv;
        ArrayList<ActionInfo> infos = null;
        if((infos = actions.get(timeKey)) == null){
            infos = new ArrayList<>();
//            infos.add(new ActionInfo(time, listener));
            actions.put(timeKey, infos);
        }
        infos.add(new ActionInfo(time, listener));


    }

    void action(long preTime, long currTime){
        long currSec = currTime/secDiv;
        long selectSec = preTime/secDiv;
        while(selectSec <= currSec){
            ArrayList<ActionInfo> infos = actions.get(selectSec);
            if(infos != null) {
                for (ActionInfo info : infos) {
                    if(preTime < info.time && info.time <= currTime){
                        handler.post(info);
                    }
                }
            }
            selectSec++;
        }
    }

    public interface OnTimeActionListener{
        void onTimeAction();
    }

    private class ActionInfo implements Runnable{
        long time;
        OnTimeActionListener listener;
        ActionInfo(long time, OnTimeActionListener listener){
            this.time = time;
            this.listener = listener;
        }

        public void run(){
            listener.onTimeAction();
        }
    }
}
