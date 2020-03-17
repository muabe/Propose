package com.markjmind.test;

import android.os.Bundle;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.markjmind.propose.Motion;
import com.markjmind.propose.Propose;
import com.markjmind.propose.animation.AnimationBuilder;
import com.markjmind.test.player.MotionPlayer;
import com.markjmind.test.player.TimeAction;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 각 모션과 이벤트에 대해 실제 화면에 대한 테스트를 진행한다.
 */
public class MainActivity extends AppCompatActivity {
    Propose propose;
    LottieAnimationView left_book, cup;
    View left_page, right_page, cup_touch;

    MotionPlayer player;
    PlayerView exoPlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_movie);



        left_book = findViewById(R.id.left_book);
        left_page = findViewById(R.id.left_page);
        right_page = findViewById(R.id.right_page);
        cup = findViewById(R.id.cup);
        cup_touch = findViewById(R.id.cup_touch);


        cup.setVisibility(View.INVISIBLE);
        float maxMove = 100f * Propose.getDensity(this);
        Motion cupMotion = new Motion(Motion.LEFT);
        cupMotion.play(AnimationBuilder.create(cup,1000).lottie(), (int)maxMove)
                .with(AnimationBuilder.create(cup_touch,1000).trasX((int)-maxMove));

        Propose cupPropose = new Propose(this);
        cupPropose.addMotion(cupMotion);
        cup_touch.setOnTouchListener(cupPropose);
        cup_touch.setClickable(false);


        left_book.setVisibility(View.INVISIBLE);
        maxMove = 200f * Propose.getDensity(this);
        Motion motion = new Motion(Motion.RIGHT);
        motion.play(AnimationBuilder.create(left_book,1000).lottie(), (int)maxMove);

        Propose propose = new Propose(this);
        propose.addMotion(motion);
        left_page.setOnTouchListener(propose);
        left_page.setClickable(false);
        right_page.setClickable(false);





        exoPlayerView = findViewById(R.id.exoPlayerView);
        exoPlayerView.setUseController(false);
        player = new MotionPlayer(exoPlayerView);
        player.name = "시나리오";
        player.addAssetPlayList("scenario.mp4");
        player.start();

        player.addTimeAction(17*1000000, new TimeAction.OnTimeActionListener() {
            @Override
            public void onTimeAction() {
                left_book.setVisibility(View.VISIBLE);
                left_page.setClickable(true);
                right_page.setClickable(true);
            }
        });

        player.addTimeAction(28*1000000, new TimeAction.OnTimeActionListener() {
            @Override
            public void onTimeAction() {
                cup.setVisibility(View.VISIBLE);
                cup_touch.setClickable(true);
            }
        });




//        ObjectAnimator tranRight = ObjectAnimator.ofFloat(phone_view, View.TRANSLATION_X, maxMove);
//        tranRight.setInterpolator(null);
//
//        motion.play(tranRight, (int) maxMove)
//                .with(AnimationBuilder.create(phone).lottie());
//
//        Propose propose = new Propose(this);
//        propose.addMotion(motion);
//        phone_view.setOnTouchListener(propose);

//        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MotionPlayer linkMotionPlayer = new MotionPlayer(v.getContext());
//                linkMotionPlayer.addAssetPlayList(fileName);
//                linkMotionPlayer.name = fileName;
//                if(fileName.equals("control.mp4")) {
//                    fileName = "phone_scenario.mp4";
//                }
//                player.link(linkMotionPlayer);
//            }
//        });
    }

    MotionPlayer templinkMotionPlayer = null;

    @Override
    protected void onResume() {
        super.onResume();
        if(!player.isPlaying()){
            player.lastSeek();
            player.restart();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(player != null) {
            player.pause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(player != null) {
            player.release();
        }
    }




        /*
        ObjectAnimator tranRight = ObjectAnimator.ofFloat(hello, View.TRANSLATION_X,center,max);
        tranRight.setDuration(1000);
        tranRight.setInterpolator(null);
        ObjectAnimator rotRight = ObjectAnimator.ofFloat(hello,View.ROTATION,0,360);
        rotRight.setDuration(1000);
        rotRight.setInterpolator(null);

        ObjectAnimator tranLeft = ObjectAnimator.ofFloat(hello,View.TRANSLATION_X,0,-max);
        tranLeft.setDuration(700);
        tranLeft.setInterpolator(null);
        ObjectAnimator rotLeft = ObjectAnimator.ofFloat(hello,View.ROTATION,0,-360);
        rotLeft.setDuration(700);
        rotLeft.setInterpolator(null);
        ObjectAnimator rot = ObjectAnimator.ofFloat(hello,View.ROTATION_Y,0,-360);
        rot.setDuration(700);
        rot.setInterpolator(null);

        ObjectAnimator tranDown = ObjectAnimator.ofFloat(hello,View.TRANSLATION_Y,center,max);
        tranDown.setDuration(1000);
        tranDown.setInterpolator(null);
        ObjectAnimator rotUp = ObjectAnimator.ofFloat(hello,View.ROTATION_X,0,180);
        rotUp.setDuration(1000);
        rotUp.setInterpolator(null);

        ObjectAnimator tranUp = ObjectAnimator.ofFloat(hello,View.TRANSLATION_Y,0,-max);
        tranUp.setDuration(1000);
        tranUp.setInterpolator(null);
        ObjectAnimator rotDown = ObjectAnimator.ofFloat(hello,View.ROTATION_X,0,-180);
        rotDown.setDuration(1000);
        rotDown.setInterpolator(null);


        propose = new Propose(this);

        Motion left = new Motion(Motion.LEFT);
        tranLeft.addListener(new AnimatorAdapter() {
            @Override
            public void onStart(Animator animator, boolean isReverse) {
                Log.e("D","1스타트:"+isReverse);
            }

            @Override
            public void onScroll(Animator animator, boolean isReverse, long currentDuration, long totalDuration) {
//                Log.d("D","1스크롤:"+currentDuration+"/"+totalDuration);
            }

            @Override
            public void onEnd(Animator animator, boolean isReverse) {
                Log.e("D","1엔드:"+isReverse);
            }
        });
        rotLeft.addListener(new AnimatorAdapter() {
            @Override
            public void onStart(Animator animator, boolean isReverse) {
                Log.e("D","2스타트:"+isReverse);
            }

            @Override
            public void onScroll(Animator animator, boolean isReverse, long currentDuration, long totalDuration) {
//                Log.d("D","2스크롤:"+currentDuration+"/"+totalDuration);
            }

            @Override
            public void onEnd(Animator animator, boolean isReverse) {
                Log.e("D","2엔드:"+isReverse);
            }
        });
        rot.addListener(new AnimatorAdapter() {
            @Override
            public void onStart(Animator animator, boolean isReverse) {
                Log.e("D","3스타트:"+isReverse);
            }

            @Override
            public void onScroll(Animator animator, boolean isReverse, long currentDuration, long totalDuration) {
//                Log.d("D","3스크롤:"+currentDuration+"/"+totalDuration);
            }

            @Override
            public void onEnd(Animator animator, boolean isReverse) {
                Log.e("D","3엔드:"+isReverse);
            }
        });

        left.play(tranLeft, (int) (max)).next(rotLeft).next(rot);
        propose.addMotion(left);

        Motion right = new Motion(Motion.RIGHT);
        right.play(tranRight, (int) (max)).next(rotRight);
        propose.addMotion(right);


        Motion up = new Motion(Motion.UP);
        up.play(tranUp, (int) (max)).with(rotUp);
        propose.addMotion(up);

        right.setMotionListener(new MotionListener() {
            @Override
            public void onStart(Motion motion) {
//                Log.e("D","모션 시작");
            }

            @Override
            public void onScroll(Motion motion, long currDuration, long totalDuration) {
//                Log.d("D", ""+currDuration+"/"+totalDuration);
            }

            @Override
            public void onEnd(Motion motion) {
//                Log.e("D","모션 끝");
            }
        });


        findViewById(R.id.Cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                propose.cancel();
            }
        });

        hello.setOnTouchListener(propose);
*/





}
