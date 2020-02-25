package com.muabe.propose;

import com.muabe.propose.action.ActionPlugin;
import com.muabe.propose.action.ActionCombiner;
import com.muabe.propose.action.Point;

public class Motion extends ActionCombiner<Motion> {
    private ActionPlugin actionPlugin;
    Player player;

    protected Motion(){
        super();
    }

    public static Motion create(ActionPlugin gesturePlugin){
        Motion motion = new Motion();
        motion.setPlugin(gesturePlugin);
        return motion;
    }

    public Motion setPlugin(ActionPlugin actionPlugin){
        this.actionPlugin = actionPlugin;
        setName(actionPlugin.getClass().getSimpleName());
        return this;
    }

    @Override
    public ActionPlugin getPlugin() {
        return actionPlugin;
    }

    public Motion setPlayer(Player player){
        this.player = player;
        return this;
    }

    public Player getPlayer(){
        return player;
    }



    public boolean filter(Object event) {
        return super.filter(event);
    }

    boolean actMotion(Object event){
        Point point = getPlugin().getPoint();
        float value = point.value() + getPlugin().increase(event);
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
            Point point = getPlugin().getPoint();
            if (point.updatePoint(value)) {
                return player.play(point.getRatio());
            }
        }
        return false;
    }

    public boolean play(float ratio){
        if(player != null){
            return playValue(getPlugin().getPoint().getValue(ratio));
        }
        return false;
    }

    public float getRatio(){
        return getPlugin().getPoint().getRatio();
    }


}
