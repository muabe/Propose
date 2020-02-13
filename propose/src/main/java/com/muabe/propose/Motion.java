package com.muabe.propose;

import com.muabe.propose.action.ActionPlugin;
import com.muabe.propose.combination.combiner.ActionCombiner;
import com.muabe.propose.combination.combiner.Point;
import com.muabe.propose.player.Player;

public class Motion extends ActionCombiner<Motion, ActionPlugin> {
    Player player;

    protected Motion(){
    }

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

    boolean actMotion(Object event){
        Point point = getActionPlugin().getPoint();
        float value = point.value() + getActionPlugin().increase(event);
        if(value <= point.getMinPoint() && !point.isMinPoint()){
            value = point.getMinPoint();
        }else if(value >= point.getMaxPoint() && !point.isMaxPoint()){
            value = point.getMaxPoint();
        }
        return playValue(value);
    }

    boolean delMotion(){
        return playValue(0f);
    }

    private boolean playValue(float value){
        if(player != null) {
            Point point = getActionPlugin().getPoint();
            if (point.updatePoint(value)) {
                return player.play(point.getRatio());
            }
        }
        return false;
    }

    public boolean play(float ratio){
        if(player != null){
            return playValue(getActionPlugin().getPoint().getValue(ratio));
        }
        return false;
    }

    public float getRatio(){
        return getActionPlugin().getPoint().getRatio();
    }

}
