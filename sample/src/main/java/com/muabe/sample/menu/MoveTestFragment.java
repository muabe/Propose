package com.muabe.sample.menu;

import android.animation.ObjectAnimator;
import android.widget.Button;

import com.markjmind.uni.UniFragment;
import com.markjmind.uni.common.Jwc;
import com.markjmind.uni.mapper.annotiation.GetView;
import com.markjmind.uni.mapper.annotiation.Layout;
import com.muabe.propose.Propose;
import com.muabe.propose.action.SingleTouchRightGesture;
import com.muabe.propose.motion.Motion;
import com.muabe.propose.player.AnimationPlayer;
import com.muabe.propose.player.Player;
import com.muabe.sample.R;

@Layout(R.layout.move_test)
public class MoveTestFragment extends UniFragment{
    @GetView
    Button button, button2, button3;

    @Override
    public void onPost() {
        float maxMove = 300f* Jwc.getDensity(button);
        ObjectAnimator left = ObjectAnimator.ofFloat(button, "translationX", -maxMove);
        ObjectAnimator right = ObjectAnimator.ofFloat(button, "translationX", maxMove);

        Motion motionRight = new Motion(new SingleTouchRightGesture(maxMove));
        Player player = AnimationPlayer.create(right);
        motionRight.setPlayer(player);

        Propose p = new Propose(getContext());
        p.setMotion(motionRight);
        button.setOnTouchListener(p);
    }

}
