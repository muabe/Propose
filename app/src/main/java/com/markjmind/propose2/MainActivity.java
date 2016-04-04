package com.markjmind.propose2;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.markjmind.propose3.Propose;

public class MainActivity extends AppCompatActivity {
    Propose propose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView hello = (TextView)findViewById(R.id.hello);
        float center = 0*Propose.getDensity(this);
        float max = 135*Propose.getDensity(this);

        ObjectAnimator tranRight = ObjectAnimator.ofFloat(hello,View.TRANSLATION_X,center,max);
        tranRight.setDuration(1000);
        tranRight.setInterpolator(null);
        ObjectAnimator rotRight = ObjectAnimator.ofFloat(hello,View.ROTATION,0,360);
        rotRight.setDuration(1000);
        rotRight.setInterpolator(null);

        ObjectAnimator tranLeft = ObjectAnimator.ofFloat(hello,View.TRANSLATION_X,0,-max);
        tranLeft.setDuration(1000);
        tranLeft.setInterpolator(null);
        ObjectAnimator rotLeft = ObjectAnimator.ofFloat(hello,View.ROTATION,0,-360);
        rotLeft.setDuration(1000);
        rotLeft.setInterpolator(null);

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
        propose.right.play(tranRight, (int) (max)).with(rotRight);
        propose.left.play(tranLeft, (int) (max)).with(rotLeft);
        propose.up.play(tranUp, (int) (max)).with(rotUp);
        propose.down.play(tranDown, (int) (max)).with(rotDown);


        hello.setOnTouchListener(propose);
    }



}
