package com.markjmind.test.player;

import android.util.Log;

import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.video.VideoSize;

import androidx.annotation.NonNull;

public class VideoListenerImp implements Player.Listener {
    @Override
    public void onVideoSizeChanged(@NonNull VideoSize videoSize) {
        Log.e("dddd", "width:"+videoSize.width);
        Log.e("dddd", "height:"+videoSize.height);
        Log.e("dddd", "unappliedRotationDegrees:"+videoSize.unappliedRotationDegrees);
        Log.e("dddd", "pixelWidthHeightRatio:"+videoSize.pixelWidthHeightRatio);
    }

    @Override
    public void onSurfaceSizeChanged(int width, int height) {

    }

    @Override
    public void onRenderedFirstFrame() {
        Log.e("dddd", "!!!!!!!!!!!!!!onRenderedFirstFrame!!!!!!!!!!!!!!");
    }
}
