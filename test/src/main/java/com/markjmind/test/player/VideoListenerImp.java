package com.markjmind.test.player;

import android.util.Log;

import com.google.android.exoplayer2.video.VideoListener;

public class VideoListenerImp implements VideoListener {
    @Override
    public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
        Log.e("dddd", "width:"+width);
        Log.e("dddd", "height:"+height);
        Log.e("dddd", "unappliedRotationDegrees:"+unappliedRotationDegrees);
        Log.e("dddd", "pixelWidthHeightRatio:"+pixelWidthHeightRatio);
    }

    @Override
    public void onSurfaceSizeChanged(int width, int height) {

    }

    @Override
    public void onRenderedFirstFrame() {
        Log.e("dddd", "!!!!!!!!!!!!!!onRenderedFirstFrame!!!!!!!!!!!!!!");
    }
}
