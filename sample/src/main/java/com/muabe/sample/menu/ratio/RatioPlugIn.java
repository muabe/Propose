package com.muabe.sample.menu.ratio;

import android.util.Log;

import com.muabe.propose.player.PlayPlugin;
import com.muabe.propose.Player;

public class RatioPlugIn extends PlayPlugin {
    @Override
    public boolean play(Player player, float ratio) {
        Log.e("dd", ((RatioCombination)player).getRealName()+":"+ratio);
        return true;
    }
}
