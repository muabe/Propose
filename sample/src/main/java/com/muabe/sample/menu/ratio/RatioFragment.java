package com.muabe.sample.menu.ratio;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.markjmind.uni.UniFragment;
import com.markjmind.uni.mapper.annotiation.GetView;
import com.markjmind.uni.mapper.annotiation.Layout;
import com.markjmind.uni.mapper.annotiation.OnClick;
import com.muabe.propose.combination.Combine;
import com.muabe.sample.R;
import com.muabe.sample.menu.combine.CombineViewer;

@Layout(R.layout.combine)
public class RatioFragment extends UniFragment {
    @GetView
    ViewGroup input_layout;

    @GetView
    Button opt;

    RatioCombination combination;

    @Override
    public void onPost() {
        init(false);
//        Combine.optimize(combination);

    }

    private void init(boolean optimize){
        input_layout.removeAllViews();

        RatioCombination e1 = new RatioCombination(null);
        RatioCombination e2 = new RatioCombination(null);
        RatioCombination e3 = new RatioCombination(null);
        RatioCombination e4 = new RatioCombination(null);
        RatioCombination e5 = new RatioCombination(null);
        RatioCombination e6 = new RatioCombination(null);
        RatioCombination e7 = new RatioCombination(null);
        RatioCombination e8 = new RatioCombination(null);
        RatioCombination e9 = new RatioCombination(null);
        RatioCombination e10 = new RatioCombination(null);

        combination = Combine.one(optimize,
                Combine.all(optimize, e1, e2),
                Combine.one(optimize,
                        e3,
                        Combine.one(optimize,
                                e4,
                                Combine.all(optimize, e5, e6)
                        ),
                        e7
                ),
                Combine.one(optimize,
                        Combine.all(optimize, e8, e9),
                        e10
                )
        );
        combination.setName("root");
        combination.setRawRatio();
        CombineViewer.attachCombinViewer(combination, input_layout);
    }

    @OnClick
    public void opt(View view){
        Combine.optimize(combination);
        input_layout.removeAllViews();
        combination.setName("root");
        CombineViewer.attachCombinViewer(combination, input_layout);
    }

    @OnClick
    public void rollback(View view){
        init(true);
    }
}
