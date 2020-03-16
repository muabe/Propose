package com.markjmind.test.player;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

public class PlayerInfo {
    public PlayerView exoPlayerView;
    public SimpleExoPlayer player;

    public long currentPosition = 0;
    public int currentWindowIndex = -1;

    public PlayerInfo(PlayerView exoPlayerView, SimpleExoPlayer player){
        this.exoPlayerView = exoPlayerView;
        this.player = player;
    }
}
