package com.muabe.sample.menu.exo;

import android.animation.ObjectAnimator;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.markjmind.uni.UniFragment;
import com.markjmind.uni.common.Jwc;
import com.markjmind.uni.mapper.annotiation.GetView;
import com.markjmind.uni.mapper.annotiation.Layout;
import com.markjmind.uni.mapper.annotiation.OnClick;
import com.muabe.propose.Motion;
import com.muabe.propose.Propose;
import com.muabe.propose.action.SingleTouchRightGesture;
import com.muabe.propose.Player;
import com.muabe.propose.player.animation.ObjectAnimatorPlugIn;
import com.muabe.sample.R;


@Layout(R.layout.exo)
public class ExoFragment extends UniFragment {


    private PlayerView exoPlayerView;
    private SimpleExoPlayer player;

    private Boolean playWhenReady = true;
    private int currentWindow = 0;
    private Long playbackPosition = 0L;
    @GetView
    SeekBar seekBar;

    @GetView
    Button button4;

    Motion motionRight;
    Player combinationPlayer;

    long duration;

    @Override
    public void onPost() {

        exoPlayerView = (PlayerView) findViewById(R.id.exoPlayerView);
        exoPlayerView.setUseController(true);



        if (player == null) {

            player = ExoPlayerFactory.newSimpleInstance(this.getContext());

            //플레이어 연결
            exoPlayerView.setPlayer(player);

        }

        String sample = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";

        MediaSource mediaSource = buildMediaSource(Uri.parse(sample));


        //prepare
        player.prepare(mediaSource, true, false);

        //start,stop
        player.setPlayWhenReady(true);

        currentWindow = player.getCurrentWindowIndex();

        duration = player.getDuration();




        player.addListener(new com.google.android.exoplayer2.Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                Log.i("dd",  playbackState+":"+duration/1000);
//                SimpleExoPlayer.STATE_IDLE //1
//                SimpleExoPlayer.STATE_BUFFERING //2
//                SimpleExoPlayer.STATE_READY //3
//                SimpleExoPlayer.STATE_ENDED //4

            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.e("dd","progress:"+(long)(5000/(progress/100f)));
                player.seekTo(currentWindow, (long)(5000/(progress/100f)));
                player.prepare(mediaSource, true, false);

//

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
//                player.stop(false);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                player.setPlayWhenReady(true);
            }
        });



        float maxMove = 150f* Jwc.getDensity(button4);
        ObjectAnimator rotation = ObjectAnimator.ofFloat(button4, View.ROTATION_Y, 360);
        ObjectAnimator right = ObjectAnimator.ofFloat(button4, "translationX", maxMove);

        motionRight = Motion.create(new SingleTouchRightGesture(maxMove));
//        final Player player = AnimationPlayer.create(10, right).setName("left");
//        Player player2 = AnimationPlayer.create(10, rotation).setName("rotation");
//        player.selfAnd(player2);
//
//        combinationPlayer = player.selfAnd(player2);

        final Player player = Player.create(new ObjectAnimatorPlugIn(right));
        final Player player2 = Player.create(new ObjectAnimatorPlugIn(rotation));
        combinationPlayer = player.next(player2);
        motionRight.setPlayer(combinationPlayer);


        Propose p = new Propose(getContext());
        p.setMotion(motionRight);
        button4.setOnTouchListener(p);


    }

    private MediaSource buildMediaSource(Uri uri) {
        String playerInfo = Util.getUserAgent(getContext(), "ExoPlayerInfo");
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(
                getContext(), playerInfo
        );
        MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .setExtractorsFactory(new DefaultExtractorsFactory())
                .createMediaSource(Uri.parse("asset:///sample.mp4"));
        return mediaSource;


//        String userAgent = Util.getUserAgent(getContext(), "blackJin");
//
//        return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory(userAgent))
//                .createMediaSource(uri);
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();

            exoPlayerView.setPlayer(null);
            player.release();
            player = null;

        }
    }

    @OnClick
    public void button4(View vied){
        Toast.makeText(getContext(), "dd", Toast.LENGTH_SHORT).show();
    }
}
