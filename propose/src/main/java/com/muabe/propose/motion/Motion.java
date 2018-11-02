package com.muabe.propose.motion;

import com.muabe.propose.combine.Combination;
import com.muabe.propose.guesture.GesturePlugin;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong - Oh)
 * @email markjmind@gmail.com
 * @since 2018-10-18
 */
public class Motion extends Combination {
    private GesturePlugin gesturePlugin;

    public Motion(){

    }

    public Motion(GesturePlugin gesturePlugin){
        this.gesturePlugin = gesturePlugin;
        this.name = gesturePlugin.getClass().getSimpleName();
    }

    public GesturePlugin getGesturePlugin(){
        return gesturePlugin;
    }

    @Override
    public int getPriority() {
        if(gesturePlugin==null){
            return 0;
        }
        return gesturePlugin.getPriority();
    }

    @Override
    public float compare(Object event) {
        if(gesturePlugin==null){
            return 0;
        }

        if(gesturePlugin.getTypeName().equals(event.getClass().getName())) {// actionInfo.typeName
            return gesturePlugin.compare(event);
        }else {
            return gesturePlugin.getPoint();
        }

    }
}
