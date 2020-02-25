package com.muabe.sample.menu.lottie;

import android.animation.ObjectAnimator;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.markjmind.uni.UniFragment;
import com.markjmind.uni.common.Jwc;
import com.markjmind.uni.mapper.annotiation.GetView;
import com.markjmind.uni.mapper.annotiation.Layout;
import com.markjmind.uni.mapper.annotiation.OnClick;
import com.muabe.propose.Motion;
import com.muabe.propose.Propose;
import com.muabe.propose.action.touch.SingleTouchRightAction;
import com.muabe.propose.action.touch.ZoomOutAction;
import com.muabe.propose.combination.Combine;
import com.muabe.propose.Player;
import com.muabe.propose.player.animation.ObjectAnimatorPlugIn;
import com.muabe.sample.R;
import com.muabe.sample.menu.plugin.LottiePlugin;


@Layout(R.layout.lottie)
public class LottieFragment extends UniFragment {


    @GetView
    LottieAnimationView phone, road;

    @GetView
    View phone_touch,phone_view;

    Motion motion, move_motion, scale_motion;

    @Override
    public void onPost() {
        float maxMove = 350f* Jwc.getDensity(getContext());
        phone.setPivotX(0);
        phone.setPivotY(maxMove);

        move_motion = Motion.create(new SingleTouchRightAction(maxMove));
        Player player = Player.create(new LottiePlugin(phone));
        Player playMove = Player.create(new ObjectAnimatorPlugIn(ObjectAnimator.ofFloat(phone_view, "translationX", maxMove)));
        move_motion.setPlayer(player.with(playMove));

        Player scaleX_out = Player.create(new ObjectAnimatorPlugIn(ObjectAnimator.ofFloat(phone, "scaleX", 0.5f)));
        Player scaleY_out = Player.create(new ObjectAnimatorPlugIn(ObjectAnimator.ofFloat(phone, "scaleY", 0.5f)));
        scale_motion = Motion.create(new ZoomOutAction());
        scale_motion.setPlayer(scaleX_out.with(scaleY_out));

        motion = Combine.all(move_motion, scale_motion);
        Propose propose = new Propose(getContext());
        propose.setMotion(motion);

        phone_touch.setOnTouchListener(propose);

    }

    @OnClick
    public void ex1(View view){
        road.setVisibility(View.VISIBLE);
    }

    @OnClick
    public void ex2(View view){
        Player player = Player.create(new LottiePlugin(road, 0.5f));
        move_motion.setPlayer(move_motion.getPlayer().with(player));
    }


}

