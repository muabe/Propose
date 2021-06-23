package com.muabe.sample.menu.lottie;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.markjmind.uni.UniFragment;
import com.markjmind.uni.common.Jwc;
import com.markjmind.uni.mapper.annotiation.GetView;
import com.markjmind.uni.mapper.annotiation.Layout;
import com.markjmind.uni.util.JwGroup;
import com.markjmind.uni.util.JwOnGroupSelect;
import com.muabe.propose.Motion;
import com.muabe.propose.Propose;
import com.muabe.propose.action.touch.SingleTouchRightAction;
import com.muabe.propose.Player;
import com.muabe.propose.player.animation.ObjectAnimatorPlugIn;
import com.muabe.sample.R;
import com.muabe.sample.menu.plugin.LottiePlugin;


@Layout(R.layout.lottie)
public class Lottie2Fragment extends UniFragment {
    @GetView
    Button ex1, ex2;

    @GetView
    ViewGroup ex1_layout, ex2_layout;

    @GetView
    LottieAnimationView walk, road, phone;

    @GetView
    View touch, moving_view
            ,phone_touch,phone_view;

    JwGroup menuGroup = new JwGroup();

    @Override
    public void onPost() {
        initMenu();
        ex1();
        ex2();



    }

    private void initMenu(){
        menuGroup.add("ex1", ex1);
        menuGroup.add("ex2", ex2);

        menuGroup.setOnGroupSelect(new JwOnGroupSelect() {
            ViewGroup[] group = {ex1_layout, ex2_layout};
            @Override
            public void selected(View v, String name, int index, Object param) {
                group[index].setVisibility(View.VISIBLE);
            }

            @Override
            public void deselected(View v, String name, int index, Object param) {
                group[index].setVisibility(View.GONE);
            }
        });
        menuGroup.select(1);
    }

    private void ex1(){
        float maxMove = 360f* Jwc.getDensity(getContext());
        Motion motion = Motion.create(new SingleTouchRightAction(maxMove));

        Player player = Player.create(new LottiePlugin(walk));
        Player playMove = Player.create(new ObjectAnimatorPlugIn(ObjectAnimator.ofFloat(moving_view, "translationX", maxMove)));
        Player playRoad = Player.create(new LottiePlugin(road, 0.5f));

        motion.setPlayer(player.with(playMove, playRoad));

        Propose propose = new Propose(getContext());
        propose.setMotion(motion);


        touch.setOnTouchListener(propose);
    }

    private void ex2(){
        float maxMove = 360f* Jwc.getDensity(getContext());
        Motion motion = Motion.create(new SingleTouchRightAction(maxMove));

        Player player = Player.create(new LottiePlugin(phone));
        Player playMove = Player.create(new ObjectAnimatorPlugIn(ObjectAnimator.ofFloat(phone_view, "translationX", maxMove)));

        motion.setPlayer(player.with(playMove));

        Propose propose = new Propose(getContext());
        propose.setMotion(motion);


        phone_touch.setOnTouchListener(propose);
    }

}
