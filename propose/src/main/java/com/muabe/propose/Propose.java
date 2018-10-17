package com.muabe.propose;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import com.muabe.propose.action.ActionModule;
import com.muabe.propose.action.TouchActionController;
import com.muabe.propose.guesture.GesturePlugin;
import com.muabe.propose.guesture.SingleTouchGesture;
import com.muabe.propose.guesture.TestGesture;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong - Oh)
 * @email markjmind@gmail.com
 * @since 2018-10-15
 */
public class Propose implements View.OnTouchListener {

    private Context context;
    private TouchActionController touchMudule;

    public Propose(Context context){
        this.context = context;
    }

    public Context getContext(){
        return context;
    }

    public void addActionMudle(ActionModule actionModule){
        actionModule.bind(this);
    }

    GesturePlugin testTouchGesture = new SingleTouchGesture();
    GesturePlugin testGesture = new TestGesture();
    public boolean callScan(ActionModule actionModule, Object event){
//        for(int i=0; i<plugIns.length; i++) {
//            if (plugIns[i].getTypeName().equals(con.getTypeName())){
//                plugIns[i].plug(object);
//            }
//        }
        if(testTouchGesture.getTypeName().equals(actionModule.getTypeName())){
            testTouchGesture.get(event);
        }else if(testGesture.getTypeName().equals(actionModule.getTypeName())){
            testGesture.get(event);
        }
        return true;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(touchMudule == null){
            touchMudule = new TouchActionController();
            touchMudule.bind(this);
        }
        return touchMudule.onTouch(view, motionEvent);
    }
}
