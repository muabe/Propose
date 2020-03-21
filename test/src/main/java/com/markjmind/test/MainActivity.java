package com.markjmind.test;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.markjmind.propose.Motion;
import com.markjmind.propose.Propose;
import com.markjmind.propose.animation.AnimationBuilder;
import com.markjmind.propose.listener.MotionInitor;
import com.markjmind.propose.listener.MotionListener;
import com.markjmind.test.player.LinkInfo;
import com.markjmind.test.player.MotionPlayer;
import com.markjmind.test.player.TimeAction;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 각 모션과 이벤트에 대해 실제 화면에 대한 테스트를 진행한다.
 */
public class MainActivity extends AppCompatActivity {
    LottieAnimationView phone, book, book2, book3, cup;
    View left_page, left_page2, left_page3, cup_touch, phone_link, phone_touch;

    MotionPlayer player;
    PlayerView exoPlayerView;

    boolean isInitBook = false;
    boolean isInitBook2 = false;
    boolean isInitBook3 = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_movie);


        phone_link = findViewById(R.id.phone_link);
        phone_touch = findViewById(R.id.phone_touch);
        phone = findViewById(R.id.phone);
        book = findViewById(R.id.book);
        book2 = findViewById(R.id.book2);
        book3 = findViewById(R.id.book3);
        left_page = findViewById(R.id.left_page);
        left_page2 = findViewById(R.id.left_page2);
        left_page3 = findViewById(R.id.left_page3);
        cup = findViewById(R.id.cup);
        cup_touch = findViewById(R.id.cup_touch);



        float maxMove = 200f * Propose.getDensity(this);

        Motion cupMotion = new Motion(Motion.LEFT);
        cupMotion.play(AnimationBuilder.create(cup,1000).lottie(), (int)maxMove);
//                .with(AnimationBuilder.create(cup_touch,1000).trasX((int)-maxMove));

        Propose cupPropose = new Propose(this);
        cupPropose.addMotion(cupMotion);
        cup_touch.setOnTouchListener(cupPropose);
        cup_touch.setVisibility(View.GONE);
        cup.setVisibility(View.GONE);

        book.setVisibility(View.GONE);
        left_page.setVisibility(View.GONE);
        final Motion left_motion = new Motion(Motion.RIGHT);
        Propose left_propose = new Propose(this);
        left_propose.addMotion(left_motion);
        left_propose.setOnMotionInitor(new MotionInitor() {
            @Override
            public void touchDown(Propose propose) {
                int distance = left_page.getWidth();
                book.setVisibility(View.VISIBLE);
                book2.setVisibility(View.GONE);
                if(!isInitBook) {
                    isInitBook = true;
                    left_motion.play(AnimationBuilder.create(book, 1000).lottie(), distance)
                            .with(AnimationBuilder.create(left_page, 1000).trasX(distance));
                }
            }

            @Override
            public void touchUp(Propose propose) {

            }
        });
        left_page.setOnTouchListener(left_propose);
        left_motion.setMotionListener(new MotionListener() {
            @Override
            public void onStart(Motion motion) {

            }

            @Override
            public void onScroll(Motion motion, long currDuration, long totalDuration) {

            }

            @Override
            public void onEnd(Motion motion) {
                if(motion.isReverse()){
                    book2.setVisibility(View.VISIBLE);
                    left_page2.setVisibility(View.VISIBLE);
                }else{
                    book2.setVisibility(View.GONE);
                    left_page2.setVisibility(View.GONE);
                }
            }
        });



        book2.setVisibility(View.GONE);
        left_page2.setVisibility(View.GONE);
        final Motion left_motion2 = new Motion(Motion.RIGHT);
        Propose left_propose2 = new Propose(this);
        left_propose2.addMotion(left_motion2);
        left_propose2.setOnMotionInitor(new MotionInitor() {
            @Override
            public void touchDown(Propose propose) {
                int distance = left_page2.getWidth();
                book2.setVisibility(View.VISIBLE);
                book3.setVisibility(View.GONE);
                if(!isInitBook2) {
                    isInitBook2 = true;
                    left_motion2.play(AnimationBuilder.create(book2, 1000).lottie(), distance)
                            .with(AnimationBuilder.create(left_page2, 1000).trasX(distance));
                }
            }

            @Override
            public void touchUp(Propose propose) {

            }
        });
        left_page2.setOnTouchListener(left_propose2);
        left_motion2.setMotionListener(new MotionListener() {
            @Override
            public void onStart(Motion motion) {

            }

            @Override
            public void onScroll(Motion motion, long currDuration, long totalDuration) {

            }

            @Override
            public void onEnd(Motion motion) {
                if(motion.isReverse()){
                    book3.setVisibility(View.VISIBLE);
                    left_page3.setVisibility(View.VISIBLE);
                }else{
                    book3.setVisibility(View.GONE);
                    left_page3.setVisibility(View.GONE);
                }
            }
        });

        book3.setVisibility(View.GONE);
        left_page3.setVisibility(View.GONE);
        final Motion left_motion3 = new Motion(Motion.RIGHT);
        Propose left_propose3 = new Propose(this);
        left_propose3.addMotion(left_motion3);
        left_propose3.setOnMotionInitor(new MotionInitor() {
            @Override
            public void touchDown(Propose propose) {
                int distance = left_page3.getWidth();
                book3.setVisibility(View.VISIBLE);
                if(!isInitBook3) {
                    isInitBook3 = true;
                    left_motion3.play(AnimationBuilder.create(book3, 1000).lottie(), distance)
                            .with(AnimationBuilder.create(left_page3, 1000).trasX(distance));
                }
            }

            @Override
            public void touchUp(Propose propose) {

            }
        });
        left_page3.setOnTouchListener(left_propose3);
        left_motion3.setMotionListener(new MotionListener() {
            @Override
            public void onStart(Motion motion) {

            }

            @Override
            public void onScroll(Motion motion, long currDuration, long totalDuration) {

            }

            @Override
            public void onEnd(Motion motion) {

            }
        });


        exoPlayerView = findViewById(R.id.exoPlayerView);
        exoPlayerView.setUseController(false);
        player = new MotionPlayer(exoPlayerView);
        player.name = "시나리오";
        player.addAssetPlayList("scenario.mp4");
        player.start();


        player.addTimeAction(9*1000000, new TimeAction.OnTimeActionListener() {
            @Override
            public void onTimeAction() {
                Toast.makeText(MainActivity.this, "핸드폰을 클릭해 보세요", Toast.LENGTH_SHORT).show();
                phone_link.setVisibility(View.VISIBLE);
            }
        });

        player.addTimeAction(16*1000000, new TimeAction.OnTimeActionListener() {
            @Override
            public void onTimeAction() {
                phone_link.setVisibility(View.GONE);
            }
        });

        player.addTimeAction(17*1000000, new TimeAction.OnTimeActionListener() {
            @Override
            public void onTimeAction() {
                Toast.makeText(MainActivity.this, "책을 넘겨보세요", Toast.LENGTH_SHORT).show();
                book.setVisibility(View.VISIBLE);
                left_page.setVisibility(View.VISIBLE);
            }
        });

        player.addTimeAction(28*1000000, new TimeAction.OnTimeActionListener() {
            @Override
            public void onTimeAction() {
                Toast.makeText(MainActivity.this, "컵을 움직여 보새요", Toast.LENGTH_SHORT).show();
                cup.setVisibility(View.VISIBLE);
                cup_touch.setVisibility(View.VISIBLE);
                cup_touch.setClickable(true);
            }
        });


        phone_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.removeAction(9*1000000);
                phone_link.setVisibility(View.GONE);
                MotionPlayer linkMotionPlayer = new MotionPlayer(v.getContext());
                linkMotionPlayer.addAssetPlayList("phone_scenario.mp4");
                player.link(linkMotionPlayer, new LinkInfo.LinkEndListener() {
                    @Override
                    public void end() {
                        phoneClick.motionBreak();
                        phone_link.setVisibility(View.VISIBLE);
                        phone.setVisibility(View.GONE);
                        phone.setAlpha(0f);
                        phone_touch.setVisibility(View.GONE);
                        phone_touch.setOnClickListener(phoneClick);
                        phone_touch.setOnTouchListener(null);
                    }
                });
                phone_touch.setVisibility(View.VISIBLE);
                phone_touch.setOnClickListener(phoneClick);
            }
        });



//        ObjectAnimator tranRight = ObjectAnimator.ofFloat(phone_view, View.TRANSLATION_X, maxMove);
//        tranRight.setInterpolator(null);
//
//        left_motion.play(tranRight, (int) maxMove)
//                .with(AnimationBuilder.create(phone).lottie());
//
//        Propose propose = new Propose(this);
//        propose.addMotion(left_motion);
//        phone_view.setOnTouchListener(propose);


    }
    PhoneClick phoneClick = new PhoneClick();

    class PhoneClick implements View.OnClickListener{
        Propose phonePropose;
        Motion phoneMotion;

        public void motionBreak(){
            if(phonePropose!=null) {
                phoneMotion.enableMove(false);
            }
        }

        @Override
        public void onClick(View v) {
            if(phone.getVisibility() != View.VISIBLE){
                phone.setVisibility(View.VISIBLE);
                phone.setAlpha(1f);
                float maxMove = phone_touch.getHeight();

                Motion phoneMotion = new Motion(Motion.UP);
                phoneMotion.play(AnimationBuilder.create(phone,1000).lottie(), (int)maxMove);
                Propose phonePropose = new Propose(v.getContext());
                phonePropose.addMotion(phoneMotion);
                phone_touch.setOnTouchListener(phonePropose);
                phone_touch.setOnClickListener(null);
                phoneMotion.enableSingleTabUp(false);
                phoneMotion.enableMove(true);
            }
        }
    }

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
