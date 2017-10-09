package com.markjmind.test;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.markjmind.propose.Motion;
import com.markjmind.propose.Propose;
import com.markjmind.propose.listener.AnimatorAdapter;
import com.markjmind.propose.listener.MotionListener;

/**
 * 각 모션과 이벤트에 대해 실제 화면에 대한 테스트를 진행한다.
 */
public class MainActivity extends Activity {
    Propose propose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView hello = (TextView)findViewById(R.id.hello);
        float center = 0*Propose.getDensity(this);
        float max = 135*Propose.getDensity(this);

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


    }



}
