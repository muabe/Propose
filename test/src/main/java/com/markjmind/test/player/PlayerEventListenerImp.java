package com.markjmind.test.player;

import android.util.Log;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;

import java.util.ArrayList;

public class PlayerEventListenerImp implements Player.EventListener {
    LinkInfo linkInfo = null;
    boolean isPreparing = false;
    private ArrayList<PlayerEventListener> playerEndList = new ArrayList<>();

    PlayerEventListenerImp(LinkInfo linkInfo){
        this.linkInfo = linkInfo;
    }

    public void addPlayEndListener(PlayerEventListener listener){
        playerEndList.add(listener);
    }

    public void onIsPlayingChanged(boolean isPlaying) {
        //Called when the value of Player.isPlaying() changes.
//        Log.e("PlayerEventListenerImp", "------------------------------------------");
//        Log.e("PlayerEventListenerImp", "isPlaying:"+isPlaying);

    }

    public void onLoadingChanged(boolean isLoading) {
        //Called when the player starts or stops loading the source.
//        Log.e("PlayerEventListenerImp", "------------------------------------------");
//        Log.e("PlayerEventListenerImp", "isLoading:"+isLoading);
    }

    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        //Called when the current playback parameters change.
    }

    public void onPlaybackSuppressionReasonChanged(int playbackSuppressionReason) {
        //Called when the value returned from Player.getPlaybackSuppressionReason() changes.
//        Log.e("PlayerEventListenerImp", "onPlaybackSuppressionReasonChanged "+playbackSuppressionReason);
    }

    public void onPlayerError(ExoPlaybackException error){
        //Called when an error occurs.
        error.printStackTrace();
    }

    public void onPlayerStateChanged(boolean playWhenReady, int playbackState){
        //Called when the value returned from either Player.getPlayWhenReady() or Player.getPlaybackState() changes.
        if(playbackState == ExoPlayer.STATE_READY){
            Log.e("PlayerEventListenerImp", "------------------------------------------");
            Log.e("PlayerEventListenerImp", "STATE_READY");
            if(isPreparing){
                isPreparing = false;
            }
        }else if(playbackState == ExoPlayer.STATE_BUFFERING){

        }else if(playbackState == ExoPlayer.STATE_ENDED){
            if(linkInfo.state == LinkInfo.LINK_NONE) {
                PlayerUtils.actlistener(playerEndList);
            }else{
                Log.e("dd","linkInfo.state : "+linkInfo.state+" "+playWhenReady);
                if(linkInfo.state == LinkInfo.LINK_ON) {
                    PlayerUtils.actlistener(linkInfo.linkPlayer.playerEvent.playerEndList);
                    linkInfo.restore();
                    linkInfo = linkInfo.modalPlayer.linkInfo;
                }
            }
        }else if(playbackState == ExoPlayer.STATE_IDLE){
        }
    }

    public void onPositionDiscontinuity(int reason){
        //Called when a position discontinuity occurs without a change to the timeline.
//        Log.e("PlayerEventListenerImp", "onPositionDiscontinuity "+reason);
    }

    public void onRepeatModeChanged(int repeatMode){
        //Called when the value of Player.getRepeatMode() changes.
    }

    public void onSeekProcessed(){
        //Called when all pending seek requests have been processed by the player.
//        Log.e("PlayerEventListenerImp", "onSeekProcessed");
    }

    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled){
        //Called when the value of Player.getShuffleModeEnabled() changes.
    }

    public void onTimelineChanged(Timeline timeline, int reason){
        //Called when the timeline has been refreshed.
//        Log.e("PlayerEventListenerImp", "onTimelineChanged "+reason);
    }

    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections){
        //Called when the available or selected tracks change.
//        Log.e("PlayerEventListenerImp", "onTracksChanged");
    }
}
