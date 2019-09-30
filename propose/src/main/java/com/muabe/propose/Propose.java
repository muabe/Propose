package com.muabe.propose;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import com.muabe.propose.action.TouchActionController;
import com.muabe.propose.combination.Combine;
import com.muabe.propose.combination.combiner.ActionModule;
import com.muabe.propose.combination.combiner.Point;
import com.muabe.propose.motion.Motion;
import com.muabe.propose.player.Player;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong - Oh)
 * @email markjmind@gmail.com
 * @since 2018-10-15
 */
public class Propose implements ActionModule.OnActionListener, View.OnTouchListener {
    public static final String ACTION_TOUCH = "ACTION_TOUCH";

    private Context context;
    private HashMap<String, ActionModule> actionModules = new HashMap<>();
    private Motion motion;

    public Propose(Context context){
        this.context = context;
        defaultActionModule();

    }

    private void defaultActionModule(){
        addActionMudle(Propose.ACTION_TOUCH, new TouchActionController(context));
    }

    public Context getContext(){
        return context;
    }

    public void addActionMudle(String name, ActionModule actionModule){
        actionModule.bind(this);
        actionModules.put(name, actionModule);
    }

    public void setMotion(Motion motion){
        this.motion = motion;
    }

    @Override
    public boolean onAction(Object event) {
        boolean result = false;
        ArrayList<Motion> motionList = Combine.scan(motion, event);

        for(Motion scanMotion : motionList){
            if(scanMotion.filter(event)){
                Player player = scanMotion.getPlayer();
                if(player != null) {
                    Point point = scanMotion.getActionPlugin().getPoint();
                    if (point.updatePoint(scanMotion.getActionPlugin().increase(event))) {
                        ArrayList<Player> scanPlayerList = Combine.scan(player, point.getRatio());
                        if(scanPlayerList.size() > 0) {
                            for(Player scanPlayer : scanPlayerList) {
                                scanPlayer.play(point.getRatio());
                            }
                        }
                    }

                }
            }
        }
        return result;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        TouchActionController touchAction = (TouchActionController) actionModules.get(Propose.ACTION_TOUCH);
        return touchAction.onTouch(view, motionEvent);
    }
}
