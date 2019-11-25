package com.muabe.sample.menu.lottie;

import android.animation.ObjectAnimator;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.markjmind.uni.UniFragment;
import com.markjmind.uni.common.Jwc;
import com.markjmind.uni.mapper.annotiation.GetView;
import com.markjmind.uni.mapper.annotiation.Layout;
import com.muabe.propose.Motion;
import com.muabe.propose.Propose;
import com.muabe.propose.action.SingleTouchRightGesture;
import com.muabe.propose.player.Player;
import com.muabe.propose.player.animation.ObjectAnimatorPlugIn;
import com.muabe.sample.R;
import com.muabe.sample.menu.plugin.LottiePlugin;


@Layout(R.layout.lottie)
public class LottieFragment extends UniFragment {


    @GetView
    LottieAnimationView phone;

    @GetView
    View phone_touch,phone_view;

    @Override
    public void onPost() {
        ex2();



    }



    private void ex2(){
        float maxMove = 360f* Jwc.getDensity(getContext());
        Motion motion = new Motion(new SingleTouchRightGesture(maxMove));

        Player player = new Player(new LottiePlugin(phone));
        Player playMove = new Player(new ObjectAnimatorPlugIn(ObjectAnimator.ofFloat(phone_view, "translationX", maxMove)));

        motion.setPlayer(player.with(playMove));

        Propose propose = new Propose(getContext());
        propose.setMotion(motion);


        phone_touch.setOnTouchListener(propose);
    }

}

