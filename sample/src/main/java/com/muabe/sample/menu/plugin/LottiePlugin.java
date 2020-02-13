package com.muabe.sample.menu.plugin;

import com.airbnb.lottie.LottieAnimationView;
import com.muabe.propose.player.PlayPlugin;
import com.muabe.propose.player.Player;

public class LottiePlugin extends PlayPlugin {

    LottieAnimationView lottieAnimView;
    float maxProgress = 1f;

    public LottiePlugin(LottieAnimationView lottieAnimView, float maxProgress){
        this.lottieAnimView =lottieAnimView;
        this.maxProgress = maxProgress;
        lottieAnimView.setMaxProgress(maxProgress);
    }

    public LottiePlugin(LottieAnimationView lottieAnimView){
        this(lottieAnimView, 1f);
    }

    @Override
    public boolean play(Player player, float ratio) {
        float roop = ratio*lottieAnimView.getRepeatCount()*maxProgress%1f;
        if(ratio >= 1f){
            roop = maxProgress;
        }
        lottieAnimView.setProgress(roop);
        return true;
    }
}
