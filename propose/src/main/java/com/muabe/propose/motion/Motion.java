package com.muabe.propose.motion;

import android.animation.ValueAnimator;

import com.muabe.propose.combine.Combination;
import com.muabe.propose.combine.Combine;
import com.muabe.propose.guesture.GesturePlugin;
import com.muabe.propose.player.AnimationPlayer;
import com.muabe.propose.player.PlayListener;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong - Oh)
 * @email markjmind@gmail.com
 * @since 2018-10-18
 */
public class Motion extends Combination {
    private GesturePlugin gesturePlugin;
    private EventFilter eventFilter;
    private PlayListener playListener;

    public Motion() {

    }

    public Motion(GesturePlugin gesturePlugin) {
        setGesturePlugin(gesturePlugin);
    }

    public void setGesturePlugin(GesturePlugin gesturePlugin) {
        this.gesturePlugin = gesturePlugin;
        this.name = gesturePlugin.getClass().getSimpleName();
        eventFilter = new EventFilter(this.gesturePlugin);
    }

    public GesturePlugin getGesturePlugin() {
        return gesturePlugin;
    }

    @Override
    public int getPriority() {
        if (gesturePlugin == null) {
            return 0;
        }
        return gesturePlugin.getPriority();
    }

    @Override
    public float compare(Object event) {
        if (gesturePlugin == null) {
            return 0;
        }
        if (filter(event)) {
            return gesturePlugin.compare(event);
        } else {
            return gesturePlugin.getPoint().value();
        }
    }

    public Motion and(Motion... motions){
        if(motions.length > 0){
            return Combine.all(copyArray(motions));
        }
        return this;
    }

    public Motion or(Motion... motions){
        if(motions.length > 0){
            return Combine.one(copyArray(motions));
        }
        return this;
    }

    private Motion[] copyArray(Motion[] motions){
        Motion[] newArray = new Motion[motions.length+1];
        newArray[0] = this;
        System.arraycopy(motions, 0, newArray, 1, motions.length);
        return newArray;
    }

    public boolean filter(Object event) {
        return eventFilter.filter(event);
    }

    public void begin(ValueAnimator animator) {
        this.begin(new AnimationPlayer(animator));
    }

    public void begin(PlayListener playListener) {
        this.playListener = playListener;
    }

    public PlayListener getPlayListener() {
        return this.playListener;
    }
//    public void play(Object event) {
//        if (gesturePlugin != null && playListener != null) {
//            Point point = gesturePlugin.getPoint();
//            if (gesturePlugin.updatePoint(event)) {
//                playListener.play(point.value(), point.getMaxPoint());
//                Log.e("GesturePlugin", getClass().getName() + ":" + point.value());
//            }
//        }
//    }
}
