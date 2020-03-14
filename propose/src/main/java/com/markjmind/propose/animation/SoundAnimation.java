package com.markjmind.propose.animation;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

public class SoundAnimation extends ValueAnimator {
    MediaPlayer player;
    public SoundAnimation(Context context, Uri uri){
        player = MediaPlayer.create(context, uri);
        this.setDuration(player.getDuration());
        setLooping(false);
        player.setVolume(100,100);
//        SoundPool
        addListener(new AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                start();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                stop();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                stop();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void start(){
        player.start();
    }

    public void stop(){
        player.stop();
    }

    public void pause(){
        player.pause();
    }

    public void setLooping(boolean looping){
        player.setLooping(looping);
    }
}
