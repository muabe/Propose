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

    public Motion(GesturePlugin gesturePlugin){
        this.gesturePlugin = gesturePlugin;
    }



    @Override
    public float priority() {
        return 0;
    }
}
