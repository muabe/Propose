package com.markjmind.test.player;

import com.google.android.exoplayer2.source.ConcatenatingMediaSource;

public class LinkInfo {
    static final int LINK_NONE = 0;
    static final int LINK_ON = 1;
    static final int LINK_END = 2;

    int state = LINK_NONE;
    MotionPlayer modalPlayer;
    public MotionPlayer linkPlayer;
    public MotionPlayer prelinkPlayer;

    long savePosition = 0;
    int saveWindowIndex = -1;
    ConcatenatingMediaSource savePlayList;


    public void link(MotionPlayer modalPlayer, MotionPlayer linkPlayer){
        state = LINK_ON;
        this.modalPlayer = modalPlayer;
        if(prelinkPlayer != null){
            prelinkPlayer = linkPlayer;
        }
        this.linkPlayer = linkPlayer;
        modalPlayer.pause();

        savePosition = modalPlayer.currentPosition;
        saveWindowIndex = modalPlayer.currentWindowIndex;
        savePlayList = modalPlayer.playList;

        modalPlayer.playList = linkPlayer.playList;
        modalPlayer.start();
    }

    public void restore(){
        state = LINK_END;
        modalPlayer.currentPosition = savePosition;
        modalPlayer.currentWindowIndex = saveWindowIndex;
        modalPlayer.playList = savePlayList;

        modalPlayer.start();
        modalPlayer.lastSeek();
//        modalPlayer.player.setPlayWhenReady(true);
//        modalPlayer.player.prepare(modalPlayer.playList, true, true);
//        modalPlayer.player.seekTo(saveWindowIndex, savePosition);
    }
}
