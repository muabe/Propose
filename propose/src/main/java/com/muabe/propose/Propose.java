package com.muabe.propose;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import com.muabe.propose.action.ActionModule;
import com.muabe.propose.action.TouchActionController;
import com.muabe.propose.guesture.GesturePlugin;
import com.muabe.propose.guesture.SingleTouchGesture;
import com.muabe.propose.guesture.TestGesture;
import com.muabe.propose.motion.Motion;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong - Oh)
 * @email markjmind@gmail.com
 * @since 2018-10-15
 */
public class Propose implements View.OnTouchListener {

    private Context context;
    private TouchActionController touchAction;
    private Motion motion;

    public Propose(Context context){
        this.context = context;
    }

    public Context getContext(){
        return context;
    }

    public void addActionMudle(ActionModule actionModule){
        actionModule.bind(this);
    }

    public void setMotion(Motion motion){
        this.motion = motion;
    }

    GesturePlugin testTouchGesture = new SingleTouchGesture();
    GesturePlugin testGesture = new TestGesture();
    public boolean callScan(ActionModule actionModule, Object event){
//        for(int i=0; i<plugIns.length; i++) {
//            if (plugIns[i].getTypeName().equals(con.getTypeName())){
//                plugIns[i].plug(object);
//            }
//        }
        if(testTouchGesture.equalsAction(actionModule, event)){
            testTouchGesture.increase(event);
        }else if(testGesture.equalsAction(actionModule, event)){
            testGesture.increase(event);
        }
        return true;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(touchAction == null){
            touchAction = new TouchActionController();
            touchAction.bind(this);
        }
        return touchAction.onTouch(view, motionEvent);
    }
}
