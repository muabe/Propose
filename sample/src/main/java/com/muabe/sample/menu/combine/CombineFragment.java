package com.muabe.sample.menu.combine;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.markjmind.uni.UniFragment;
import com.markjmind.uni.mapper.annotiation.GetView;
import com.markjmind.uni.mapper.annotiation.Layout;
import com.markjmind.uni.mapper.annotiation.OnClick;
import com.muabe.propose.combination.Combine;
import com.muabe.sample.R;

@Layout(R.layout.combine)
public class CombineFragment extends UniFragment {
    @GetView
    ViewGroup input_layout;

    @GetView
    Button opt;

    TestCombination combination;

    @Override
    public void onPost() {
        init(false);
//        Combine.optimize(combination);

    }

    private void init(boolean optimize){
        input_layout.removeAllViews();

        TestCombination e1 = new TestCombination(0);
        TestCombination e2 = new TestCombination(0);
        TestCombination e3 = new TestCombination(0);
        TestCombination e4 = new TestCombination(0);
        TestCombination e5 = new TestCombination(0);
        TestCombination e6 = new TestCombination(0);
        TestCombination e7 = new TestCombination(0);
        TestCombination e8 = new TestCombination(0);
        TestCombination e9 = new TestCombination(0);
        TestCombination e10 = new TestCombination(0);

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
