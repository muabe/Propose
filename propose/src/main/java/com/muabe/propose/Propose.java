package com.muabe.propose;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import com.muabe.propose.action.ActionModule;
import com.muabe.propose.action.TouchActionController;
import com.muabe.propose.combine.Combine;
import com.muabe.propose.motion.Motion;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong - Oh)
 * @email markjmind@gmail.com
 * @since 2018-10-15
 */
public class Propose implements View.OnTouchListener {
    public static final String ACTION_TOUCH = "ACTION_TOUCH";

    private Context context;
    private HashMap<String, ActionModule> actionModules = new HashMap<>();
    private Motion motion;

    public Propose(Context context){
        this.context = context;
        defaultActionModule();
    }

    private void defaultActionModule(){
        addActionMudle(Propose.ACTION_TOUCH, new TouchActionController());
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

    public boolean callScan(ActionModule actionModule, Object event){
        ArrayList<Motion> motionList = Combine.scan(motion);
        for(Motion scanMotion : motionList){
            if(scanMotion.getGesturePlugin().equalsAction(actionModule, event)){

                //TODO search Priority and Play

            }
        }
        return true;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        TouchActionController touchAction = (TouchActionController)actionModules.get(Propose.ACTION_TOUCH);
        touchAction.bind(this);
        return touchAction.onTouch(view, motionEvent);
    }
}
