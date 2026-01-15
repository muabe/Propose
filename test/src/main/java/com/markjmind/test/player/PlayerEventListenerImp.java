package com.markjmind.test.player;

import android.util.Log;

import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.Tracks;
import com.google.android.exoplayer2.PlaybackException;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class PlayerEventListenerImp implements Player.Listener {
    LinkInfo linkInfo = null;
    boolean isPreparing = false;
    public ArrayList<PlayerEventListener> playerEndList = new ArrayList<>();

    PlayerEventListenerImp(LinkInfo linkInfo){
        this.linkInfo = linkInfo;
    }

    public void addPlayEndListener(PlayerEventListener listener){
        playerEndList.add(listener);
    }

    @Override
    public void onIsPlayingChanged(boolean isPlaying) {
    }

    @Override
    public void onPlaybackParametersChanged(@NonNull PlaybackParameters playbackParameters) {
        Log.e("PlayerEventListenerImp", "onPlaybackParametersChanged");
    }

    @Override
    public void onPlaybackSuppressionReasonChanged(int playbackSuppressionReason) {
    }

    @Override
    public void onPlayerError(@NonNull PlaybackException error){
        error.printStackTrace();
    }

    @Override
    public void onPlaybackStateChanged(int playbackState) {
        if(playbackState == Player.STATE_READY){
            Log.e("PlayerEventListenerImp", "------------------------------------------");
            Log.e("PlayerEventListenerImp", "STATE_READY");
            if(isPreparing){
                isPreparing = false;
            }
        }else if(playbackState == Player.STATE_BUFFERING){

        }else if(playbackState == Player.STATE_ENDED){
            if(linkInfo.state == LinkInfo.LINK_NONE) {
                PlayerUtils.actlistener(playerEndList);
            }else{
                Log.e("dd","linkInfo.state : "+linkInfo.state);
                if(linkInfo.state == LinkInfo.LINK_ON) {
                    PlayerUtils.actlistener(linkInfo.linkPlayer.playerEvent.playerEndList);
                    linkInfo.restore();
                    linkInfo = linkInfo.modalPlayer.linkInfo;
                }
            }
        }else if(playbackState == Player.STATE_IDLE){
        }
    }

    @Override
    public void onPositionDiscontinuity(@NonNull Player.PositionInfo oldPosition, @NonNull Player.PositionInfo newPosition, int reason) {
        Log.e("PlayerEventListenerImp", "onPositionDiscontinuity " + reason);
    }

    @Override
    public void onRepeatModeChanged(int repeatMode){
    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled){
    }

    @Override
    public void onTimelineChanged(@NonNull Timeline timeline, int reason){
        Log.e("PlayerEventListenerImp", "onTimelineChanged "+reason);
    }

    @Override
    public void onTracksChanged(@NonNull Tracks tracks){
        Log.e("PlayerEventListenerImp", "onTracksChanged");
    }
}
