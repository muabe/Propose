package com.markjmind.test.player;

import android.content.Context;
import android.media.MediaFormat;
import android.net.Uri;
import android.util.Log;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoFrameMetadataListener;

import androidx.annotation.Nullable;

public class MotionPlayer implements Player.Listener, VideoFrameMetadataListener
{
    public String name = "없음";

    public LinkInfo linkInfo = new LinkInfo();
    MotionPlayer modalPlayer;
    protected ConcatenatingMediaSource playList;

    private DataSource.Factory dataSourceFactory;

    public PlayerView exoPlayerView;
    public ExoPlayer player;
    public long currentPosition = 0;
    public int currentWindowIndex = -1;

    protected PlayerEventListenerImp playerEvent = new PlayerEventListenerImp(linkInfo);
    protected VideoListenerImp videoListener = new VideoListenerImp();
    private Context context;
    private TimeAction timeAction = new TimeAction();


    public MotionPlayer(Context context){
        this.context = context;
        this.playList = new ConcatenatingMediaSource();
        this.modalPlayer = this;
    }

    public MotionPlayer(PlayerView exoPlayerView){
        this(exoPlayerView, new ExoPlayer.Builder(exoPlayerView.getContext()).build());
    }

    protected MotionPlayer(PlayerView exoPlayerView, ExoPlayer player){
        init(exoPlayerView, player);
        resetListener();
        this.context = exoPlayerView.getContext();
        this.playList = new ConcatenatingMediaSource();
        this.modalPlayer = this;
    }

    public void init(PlayerView exoPlayerView, ExoPlayer player){
        this.exoPlayerView = exoPlayerView;
        this.player = player;
        exoPlayerView.setPlayer(player);
    }




    private void resetListener(){
        player.addListener(playerEvent);
        player.addListener(videoListener);
        player.setVideoFrameMetadataListener(this);
    }
    
    private void removeAllListener(){
        player.removeListener(playerEvent);
        player.removeListener(videoListener);
    }

    public void link(MotionPlayer motionPlayer, LinkInfo.LinkEndListener endListener) {
        playerEvent.linkInfo = motionPlayer.linkInfo;
        playerEvent.linkInfo.link(this, motionPlayer, endListener);
    }


    public ExoPlayer getPlayer(){
        return player;
    }

    public PlayerView getView(){
        return exoPlayerView;
    }

    public Context getContext(){
        return context;
    }

    public void start(){
        this.start(true);
    }

    public void restart(){
        start(false);
    }

    private void start(boolean reset){
        player.setPlayWhenReady(true);
        if(!isPlaying()) {
            playerEvent.isPreparing = true;
            player.setMediaSource(playList, reset);
            player.prepare();
        }
    }

    public void lastSeek(){
        if(currentPosition > 0) {
            player.seekTo(currentWindowIndex, currentPosition);
        }
    }

    public void preView(){
        player.setPlayWhenReady(false);
        player.setMediaSource(playList, false);
        player.prepare();
    }


    public boolean isPlaying(){
        return playerEvent.isPreparing || player.isPlaying();
    }


    public void stop(){
        currentPosition = 0;
        player.stop();
    }

    public void pause(){
        currentWindowIndex = player.getCurrentMediaItemIndex();
        currentPosition = player.getCurrentPosition();
        player.pause();
        Log.e("ddd","currentPosition:"+currentPosition);
        Log.e("ddd","currentWindowIndex:"+currentWindowIndex);
    }

    public void release(){
        player.stop();
        exoPlayerView.setPlayer(null);
        player.release();
        player = null;
    }

    public void addTimeAction(long time, TimeAction.OnTimeActionListener listener){
        timeAction.addAction(time, listener);
    }

    public void removeAction(long time){
        timeAction.removeAction(time);
    }

    final static int secDiv = 100000;
    long preTime = 0;
    @Override
    public void onVideoFrameAboutToBeRendered(long presentationTimeUs, long releaseTimeNs, @Nullable Format format, @Nullable MediaFormat mediaFormat) {
        timeAction.action(preTime, presentationTimeUs);
        preTime = presentationTimeUs;
    }

/**************************************Asset 관련**********************************************/
    public void addAssetPlayList(String... fileNames){
        for(String fileName : fileNames){
            MediaSource mediaSource = getAssetUriMediaSource(fileName, getAssetUri(fileName));
            playList.addMediaSource(mediaSource);
        }
    }

    public void removeAssetPlayList(int index){
        playList.removeMediaSource(index);
    }

    private MediaSource getAssetUriMediaSource(String tag, Uri uri){
        if(dataSourceFactory == null){
            dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "MotionPlayer"));
        }

        MediaItem mediaItem = new MediaItem.Builder()
                .setUri(uri)
                .setTag(tag)
                .build();

        return new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(mediaItem);
    }

    private Uri getAssetUri(String fieleName){
        return Uri.parse("asset:///"+fieleName);
    }



}
