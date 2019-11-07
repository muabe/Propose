package com.muabe.sample.menu.ratio;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.markjmind.uni.UniFragment;
import com.markjmind.uni.mapper.annotiation.GetView;
import com.markjmind.uni.mapper.annotiation.Layout;
import com.markjmind.uni.mapper.annotiation.OnClick;
import com.muabe.propose.combination.Combine;
import com.muabe.propose.player.Player;
import com.muabe.sample.R;
import com.muabe.sample.menu.combine.CombineViewer;

@Layout(R.layout.combine)
public class RatioFragment extends UniFragment {
    @GetView
    ViewGroup input_layout;

    @GetView
    Button opt;

    Player combination;
    RatioCombination e1;
    RatioCombination e2;
    RatioCombination e3;
    RatioCombination e4;
    RatioCombination e5;
    RatioCombination e6;
    RatioCombination e7;
    RatioCombination e8;
    RatioCombination e9;
    RatioCombination e10;
    RatioCombination center;
    RatioCombination last;

    @Override
    public void onPost() {
        init(false);
//        Combine.optimize(combination);

    }

    private void init(boolean optimize){
        input_layout.removeAllViews();
        Combine.setDefaultOptimize(false);
        e1 = (RatioCombination) new RatioCombination(new RatioPlugIn()).setName("e1");
        e2 = (RatioCombination) new RatioCombination(new RatioPlugIn()).setName("e2");
        e3 = (RatioCombination) new RatioCombination(new RatioPlugIn()).setName("e3");
        e4 = (RatioCombination) new RatioCombination(new RatioPlugIn()).setName("e4");
        e5 = (RatioCombination) new RatioCombination(new RatioPlugIn()).setName("e5");
        e6 = (RatioCombination) new RatioCombination(new RatioPlugIn()).setName("e6");
        e7 = (RatioCombination) new RatioCombination(new RatioPlugIn()).setName("e7");
        e8 = (RatioCombination) new RatioCombination(new RatioPlugIn()).setName("e8");
        e9 = (RatioCombination) new RatioCombination(new RatioPlugIn()).setName("e9");
        e10 = (RatioCombination) new RatioCombination(new RatioPlugIn()).setName("e10");



//        combination = Combine.one(optimize,
//                Combine.all(optimize, e1, e2),
//                Combine.one(optimize,
//                        e3,
//                        center = Combine.one(optimize,
//                                e4,
//                                last = Combine.all(optimize, e5, e6)
//                        ),
//                        e7
//                ),
//                Combine.one(optimize,
//                        Combine.all(optimize, e8, e9),
//                        e10
//                )
//        );

        combination =
                        e1.with(e2).next(
                            e3.next(
                                    e4.next(e5.with(e6)),
                                    e7
                                    ),
                            e8.with(e9).next(e10)
                        );




        combination.setName("root");
        CombineViewer.attachCombinViewer(combination, input_layout);
    }

    @OnClick
    public void opt(View view){
//        Combine.optimize(combination);
//        input_layout.removeAllViews();
//        combination.setName("root");
//        CombineViewer.attachCombinViewer(combination, input_layout);
        float ratio = e6.getRatio();
        float start = e6.getStartRatio();
        float end = e6.getEndRatio();
        combination.play(0.5f);
        Log.i("dd","----------------------------");
        e6.play(0.5f);
        Log.i("dd","----------------------------");
        e7.play(0.5f);
    }

    @OnClick
    public void rollback(View view){
        init(true);
        Combine.optimize(combination);
        input_layout.removeAllViews();
        combination.setName("root");
        CombineViewer.attachCombinViewer(combination, input_layout);
    }
}
