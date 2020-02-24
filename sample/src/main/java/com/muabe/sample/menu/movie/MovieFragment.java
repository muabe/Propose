package com.muabe.sample.menu.movie;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.markjmind.uni.UniFragment;
import com.markjmind.uni.common.Jwc;
import com.markjmind.uni.mapper.annotiation.GetView;
import com.markjmind.uni.mapper.annotiation.Layout;
import com.markjmind.uni.mapper.annotiation.Progress;
import com.markjmind.uni.thread.CancelAdapter;
import com.markjmind.uni.thread.LoadEvent;
import com.muabe.propose.Motion;
import com.muabe.propose.Propose;
import com.muabe.propose.combination.combiner.ActionPlugin;
import com.muabe.propose.action.SingleTouchLeftGesture;
import com.muabe.propose.action.SingleTouchRightGesture;
import com.muabe.propose.action.SingleTouchUpGesture;
import com.muabe.propose.action.ZoomInGesture;
import com.muabe.propose.combination.Combine;
import com.muabe.propose.player.animation.ObjectAnimatorPlugIn;
import com.muabe.sample.R;
import com.muabe.sample.menu.plugin.LottiePlugin;

import java.io.IOException;

import androidx.annotation.Nullable;

@Progress(res=R.layout.d_progress)
@Layout(R.layout.movie)
public class MovieFragment extends UniFragment {

    private PlayerView exoPlayerView;
    private SimpleExoPlayer exoPlayer;
    ConcatenatingMediaSource concatenatedSource;
    Handler handler = new Handler();

    @GetView
    LottieAnimationView book;//, hand, phone;

    @GetView
    View phone_touch, overlay, frame;

    @GetView
    ImageView book_image;
    float maxMove;

    boolean isStory = false;
    boolean isHand = false;
    @Override
    public void onPre() {
        maxMove = 380f* Jwc.getDensity(getContext());
        exoPlayerView = (PlayerView) findViewById(R.id.exoPlayerView);
        exoPlayerView.setUseController(false);

        if (exoPlayer == null) {

            exoPlayer = ExoPlayerFactory.newSimpleInstance(this.getContext());

            //플레이어 연결
            exoPlayerView.setPlayer(exoPlayer);

        }
        String[] playList = {
                "m02",
                "end"
//                "m03",
//                "m03_1",
//                "m05",
//                "m06",
//                "m07"
                            };
        book.addAnimatorUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if(!isStory){
                    isStory = true;
                    concatenatedSource.removeMediaSource(1);
                    concatenatedSource.addMediaSource(getMediaSource("m02_1"));
                    concatenatedSource.addMediaSource(getMediaSource("m03"));
                    concatenatedSource.addMediaSource(getMediaSource("m03_1"));
                    concatenatedSource.addMediaSource(getMediaSource("m05"));
                    concatenatedSource.addMediaSource(getMediaSource("m06"));
                    concatenatedSource.addMediaSource(getMediaSource("m07"));
                }

                if(isHand){
                    isHand = false;
                    book.playAnimation();
                }
            }
        });

        //prepare
        exoPlayer.prepare(iniyConcatSource(playList), true, false);
        //start,stop
        exoPlayer.setPlayWhenReady(true);
        exoPlayerView.setShutterBackgroundColor(Color.TRANSPARENT);
        initMotion(new SingleTouchRightGesture(maxMove));

        exoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {}

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
                Log.e("d","onTracksChanged");
            }

            @Override
            public void onLoadingChanged(boolean isLoading) {}

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {}

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {}

            @Override
            public void onPlayerError(ExoPlaybackException error) {}

            @Override
            public void onPositionDiscontinuity(int reason) {
//                Log.e("d","onPositionDiscontinuity");
                if(reason == Player.DISCONTINUITY_REASON_PERIOD_TRANSITION){
                    Log.e("d","PERIOD_TRANSITION");
                    if(exoPlayer.getCurrentTag().equals("m02_1")){
                        isZoom = false;
                        book.setVisibility(View.INVISIBLE);
                        book.cancelAnimation();
                        phone_touch.setOnTouchListener(null);
                    }else if(exoPlayer.getCurrentTag().equals("m03")){
                        book.setVisibility(View.VISIBLE);
                        book.setAnimation("hand.json");
                        book.setImageAssetsFolder("hand");
                        initMotion(new SingleTouchLeftGesture(maxMove*1.5f));
                    }else if(exoPlayer.getCurrentTag().equals("m05")){
                        book.setVisibility(View.INVISIBLE);
                        phone_touch.setOnTouchListener(null);
                        book.cancelAnimation();
                    }else if(exoPlayer.getCurrentTag().equals("m04")){
                        frame.setScaleX(1f);
                        frame.setScaleY(1f);
                        book.setVisibility(View.GONE);
                        phone_touch.setOnClickListener(null);
                        phone_touch.setOnTouchListener(null);
                    }else if(exoPlayer.getCurrentTag().equals("m06")){
                        book.setAnimation("phone.json");
                        book.setImageAssetsFolder("phone");
                        phone_touch.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                isZoom = true;
                                book.startAnimation(AnimationUtils.loadAnimation(v.getContext(), android.R.anim.fade_in));
                                book.setVisibility(View.VISIBLE);
                                initMotion(new SingleTouchUpGesture(maxMove/3f));
                                phone_touch.setOnClickListener(null);

                                concatenatedSource.removeMediaSource(6);
                                concatenatedSource.addMediaSource(getMediaSource("m04"));
                                concatenatedSource.addMediaSource(getMediaSource("m07"));
                            }
                        });
                    }
                    else if(exoPlayer.getCurrentTag().equals("m07") || exoPlayer.getCurrentTag().equals("m04") || exoPlayer.getCurrentTag().equals("end")){
                        isZoom = false;
                        frame.setScaleX(1f);
                        frame.setScaleY(1f);
                        book.setVisibility(View.GONE);
                        phone_touch.setOnClickListener(null);
                        phone_touch.setOnTouchListener(null);

                    }


                }
                if(reason == Player.DISCONTINUITY_REASON_SEEK){
                    Log.e("d","REASON_SEEK");
                }

                if(reason == Player.TIMELINE_CHANGE_REASON_DYNAMIC){
                    Log.e("d","DYNAMIC");
                }

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });
    }

    @Override
    public void onLoad(LoadEvent event, CancelAdapter cancelAdapter) throws Exception {
        Thread.sleep(3000);
    }

    @Override
    public void onPost() {
        overlay.setVisibility(View.GONE);
    }

    void initMotion(ActionPlugin<?> plugin){
        initMotion(0f, plugin);
    }

    boolean isZoom = false;
    void initMotion(float start, ActionPlugin<?> plugin){
        book.setProgress(start);
        Propose propose = new Propose(getContext());
        Motion motion = new Motion(plugin);
        com.muabe.propose.player.Player player = new com.muabe.propose.player.Player(new LottiePlugin(book));
        motion.setPlayer(player);
        if(isZoom){
            com.muabe.propose.player.Player scaleX_out = new com.muabe.propose.player.Player(new ObjectAnimatorPlugIn(ObjectAnimator.ofFloat(frame, "scaleX", 3f)));
            com.muabe.propose.player.Player scaleY_out = new com.muabe.propose.player.Player(new ObjectAnimatorPlugIn(ObjectAnimator.ofFloat(frame, "scaleY", 3f)));
            Motion scale_motion = new Motion(new ZoomInGesture());
            scale_motion.setPlayer(scaleX_out.with(scaleY_out));
            propose.setMotion(Combine.all(motion, scale_motion));
        }else{

            propose.setMotion(motion);
        }


        phone_touch.setOnTouchListener(propose);
    }


    private MediaSource getMediaSource(final String path) {
        String playerInfo = Util.getUserAgent(getContext(), "ExoPlayerInfo");
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(
                getContext(), playerInfo
        );

        MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .setExtractorsFactory(new DefaultExtractorsFactory())
                .setTag(path)
                .createMediaSource(Uri.parse("asset:///"+path+".mp4"));

        mediaSource.addEventListener(handler, new MediaSourceEventListener() {
            @Override
            public void onMediaPeriodCreated(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
                Log.e("dd", "onMediaPeriodCreated");
            }

            @Override
            public void onMediaPeriodReleased(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {

            }

            @Override
            public void onLoadStarted(int windowIndex, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
                Log.e("dd", "onLoadStarted");
            }

            @Override
            public void onLoadCompleted(int windowIndex, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {

            }

            @Override
            public void onLoadCanceled(int windowIndex, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {

            }

            @Override
            public void onLoadError(int windowIndex, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException error, boolean wasCanceled) {

            }

            @Override
            public void onReadingStarted(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
                Log.e("dd", "onReadingStarted");

            }

            @Override
            public void onUpstreamDiscarded(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {

            }

            @Override
            public void onDownstreamFormatChanged(int windowIndex, @Nullable MediaSource.MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {

            }
        });
        return mediaSource;

//        String userAgent = Util.getUserAgent(getContext(), "blackJin");
//        return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory(userAgent))
//                .createMediaSource(uri); //http 스트리밍 방
    }

    private ConcatenatingMediaSource iniyConcatSource(String... paths){
        concatenatedSource = new ConcatenatingMediaSource();
        MediaSource[] source = new MediaSource[paths.length];
        for(String path : paths){
            concatenatedSource.addMediaSource(getMediaSource(path));
        }

        return concatenatedSource;
    }

    private String getAsset(String path){
        return "asset:///"+path;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(exoPlayer != null) {
            exoPlayer.release();
        }
    }
}

