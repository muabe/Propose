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


    public void link(MotionPlayer modalPlayer, MotionPlayer linkPlayer, LinkEndListener listener){
        this.listener = listener;
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
        // 핵심: 플레이어에 새로운 소스를 설정해줘야 함
        modalPlayer.player.setMediaSource(modalPlayer.playList);
        modalPlayer.player.prepare();
        modalPlayer.start();
    }

    public void restore(){
        state = LINK_END;
        modalPlayer.currentPosition = savePosition;
        modalPlayer.currentWindowIndex = saveWindowIndex;
        modalPlayer.playList = savePlayList;

        // 복구 시에도 소스를 다시 설정
        modalPlayer.player.setMediaSource(modalPlayer.playList);
        modalPlayer.player.prepare();
        
        listener.end();
        modalPlayer.start();
        modalPlayer.lastSeek();
    }

    LinkEndListener listener;
    public interface LinkEndListener{
        void end();
    }
}
