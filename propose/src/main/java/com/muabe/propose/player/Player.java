package com.muabe.propose.player;

import android.util.Log;

import com.muabe.propose.guesture.GesturePlugin;
import com.muabe.propose.guesture.Point;
import com.muabe.propose.motion.Motion;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong - Oh)
 * @email markjmind@gmail.com
 * @since 2018-11-07
 */
public class Player {

    public void play(Object event, GesturePlugin plugin, PlayListener playListener) {
        if (plugin !=null && playListener != null) {
            Point point = plugin.getPoint();
            if (point.updatePoint(event, plugin)) {
                playListener.play(point.value(), point.getMaxPoint());
                Log.e("GesturePlugin", getClass().getName() + ":" + point.value());
            }
        }
    }

    public void play(Object event, Motion motion){
        this.play(event, motion.getGesturePlugin(), motion.getPlayListener());
    }

}
