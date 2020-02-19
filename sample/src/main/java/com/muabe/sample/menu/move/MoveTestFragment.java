package com.muabe.sample.menu.move;

import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.Button;

import com.markjmind.uni.UniFragment;
import com.markjmind.uni.common.Jwc;
import com.markjmind.uni.mapper.annotiation.GetView;
import com.markjmind.uni.mapper.annotiation.Layout;
import com.muabe.propose.Motion;
import com.muabe.propose.Propose;
import com.muabe.propose.action.SingleTouchLeftGesture;
import com.muabe.propose.action.SingleTouchRightGesture;
import com.muabe.propose.action.ZoomInGesture;
import com.muabe.propose.action.ZoomOutGesture;
import com.muabe.propose.combination.Combine;
import com.muabe.propose.player.Player;
import com.muabe.propose.player.animation.ObjectAnimatorPlugIn;
import com.muabe.sample.R;

@Layout(R.layout.move_test)
public class MoveTestFragment extends UniFragment{
    @GetView
    Button button, button2, button3;

    Motion motionRight, motionLeft;
    Player combinationPlayer;

    float ratio = 0f;
    @Override
    public void onPost() {
        float maxMove = 150f* Jwc.getDensity(button);
        ObjectAnimator rotation = ObjectAnimator.ofFloat(button, View.ROTATION_Y, 360);
        ObjectAnimator right = ObjectAnimator.ofFloat(button, "translationX", maxMove);
        ObjectAnimator left = ObjectAnimator.ofFloat(button, "translationX", -maxMove);

        motionRight = new Motion(new SingleTouchRightGesture(maxMove));
        motionLeft = new Motion(new SingleTouchLeftGesture(maxMove));
//        final Player player = AnimationPlayer.create(10, right).setName("left");
//        Player player2 = AnimationPlayer.create(10, rotation).setName("rotation");
//        player.selfAnd(player2);
//
//        combinationPlayer = player.selfAnd(player2);

        final Player player = new Player(new ObjectAnimatorPlugIn(right));
        final Player player2 = new Player(new ObjectAnimatorPlugIn(rotation));
        final Player player3 = new Player(new ObjectAnimatorPlugIn(left));
        combinationPlayer = player.with(player2);
        motionRight.setPlayer(combinationPlayer);
        motionLeft.setPlayer(player3);

        Motion motion = Combine.oneof(motionRight, motionLeft);


        Propose p = new Propose(getContext());
//        p.setMotion(motion);

        ObjectAnimator scaleX_in = ObjectAnimator.ofFloat(button, "scaleX", 10f);
        ObjectAnimator scaleY_in = ObjectAnimator.ofFloat(button, "scaleY", 10f);
        ObjectAnimator scaleX_out = ObjectAnimator.ofFloat(button, "scaleX", 0.1f);
        ObjectAnimator scaleY_out = ObjectAnimator.ofFloat(button, "scaleY", 0.1f);

        Motion scaleMotion_in = new Motion(new ZoomInGesture());
        scaleMotion_in.setPlayer(
                new Player(new ObjectAnimatorPlugIn(scaleX_in))
                    .with(
                        new Player(new ObjectAnimatorPlugIn(scaleY_in))
                )
            );

        Motion scaleMotion_out = new Motion(new ZoomOutGesture());
        scaleMotion_out.setPlayer(
                new Player(new ObjectAnimatorPlugIn(scaleX_out))
                        .with(
                                new Player(new ObjectAnimatorPlugIn(scaleY_out))
                        )
        );
        p.setMotion(Combine.oneof(scaleMotion_in, scaleMotion_out));

        button.setOnTouchListener(p);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                motionRight.play(motionRight.getRatio()+0.1f);
                ratio = ratio+ 0.2f;
                player2.play(ratio);
            }
        });
    }

}
