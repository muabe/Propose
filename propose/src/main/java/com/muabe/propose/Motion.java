package com.muabe.propose;

import com.muabe.propose.action.ActionPlugin;
import com.muabe.propose.combination.Combine;
import com.muabe.propose.combination.combiner.ActionCombiner;
import com.muabe.propose.combination.combiner.Point;
import com.muabe.propose.player.Player;

import java.util.ArrayList;

public class Motion extends ActionCombiner<Motion, ActionPlugin> {
    Player player;

    public ActionCombiner setPlayer(Player player){
        this.player = player;
        return this;
    }

    public Player getPlayer(){
        return player;
    }

    public Motion(ActionPlugin gesturePlugin) {
        super(gesturePlugin);
    }

    public boolean filter(Object event) {
        return super.filter(event);
    }

    void playMotion(Object event){
        ArrayList<Motion> motionList = Combine.scan(this, event);

        for(Motion scanMotion : motionList){
            if(scanMotion.filter(event)){
                float value = getActionPlugin().getPoint().value() + getActionPlugin().increase(event);
                boolean isPlay = scanMotion.playValue(value);
                if(isPlay){

                }
            }
        }
    }

    private boolean playValue(float value){
        if(player != null) {
            Point point = getActionPlugin().getPoint();
            if (point.setPoint(value)) {
                return player.play(point.getRatio());
            }
        }
        return false;
    }

    public boolean play(float ratio){
        if(player != null){
            Point point = getActionPlugin().getPoint();
            return playValue(point.getValue(ratio));
        }
        return false;
    }

    public float getRatio(){
        return getActionPlugin().getPoint().getRatio();
    }
}
