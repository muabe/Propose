package com.muabe.propose;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import com.muabe.propose.action.ActionModule;
import com.muabe.propose.action.touch.module.TouchModuleController;
import com.muabe.propose.combination.Combine;
import com.muabe.propose.combination.ScanResult;

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
    public static final String ACTION_MULTI_TOUCH = "ACTION_MULTI_TOUCH";

    private Context context;
    private HashMap<String, ActionModule> actionModules = new HashMap<>();
    private Motion motion;

    public Propose(Context context){
        this.context = context;
        defaultActionModule();

    }

    private void defaultActionModule(){
        addActionMudle(Propose.ACTION_TOUCH, new TouchModuleController(context, false));
        addActionMudle(Propose.ACTION_MULTI_TOUCH, new TouchModuleController(context, true));
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
        ScanResult<Motion> scanResult = Combine.scan(motion, event);
        for(Motion deleteMotion : scanResult.getDeleteList()){
            deleteMotion.delMotion();
        }

        for(Motion scanMotion : scanResult.getScanList()){
            if(scanMotion.filter(event)){
                result = scanMotion.actMotion(event);
                if(result){

                }
            }
        }

        return result;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        TouchModuleController touchAction = (TouchModuleController) actionModules.get(Propose.ACTION_TOUCH);
        return touchAction.onTouch(view, motionEvent);
    }
}
