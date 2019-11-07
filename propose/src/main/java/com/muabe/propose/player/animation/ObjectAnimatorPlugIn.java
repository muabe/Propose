package com.muabe.propose.player.animation;

import android.animation.ObjectAnimator;

import com.muabe.propose.player.PlayPlugin;
import com.muabe.propose.player.Player;

public class ObjectAnimatorPlugIn extends PlayPlugin {

    long defaultDuration = 1000;
    ObjectAnimator animator;

    public ObjectAnimatorPlugIn(ObjectAnimator animator) {
        this.animator = animator;
        this.animator
                .setDuration(defaultDuration)
                .setInterpolator(null);
    }

    @Override
    public boolean play(Player player, float ratio) {
        long playDuration = (long) (defaultDuration * ratio);
        animator.setCurrentPlayTime(playDuration);
        return true;
    }
}
