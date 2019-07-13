package com.muabe.sample.menu.combine;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.markjmind.uni.UniFragment;
import com.markjmind.uni.common.Jwc;
import com.markjmind.uni.mapper.annotiation.GetView;
import com.markjmind.uni.mapper.annotiation.Layout;
import com.muabe.combination.Combination;
import com.muabe.combination.Combine;
import com.muabe.sample.R;

import java.util.ArrayList;

@Layout(R.layout.combine)
public class CombineFragment extends UniFragment {
    @GetView
    ViewGroup input_layout;

    @Override
    public void onPost() {
        TestCombination e1 = new TestCombination("E1",0);
        TestCombination e2 = new TestCombination("E2",0);
        TestCombination e3 = new TestCombination("E3",0);
        TestCombination e4 = new TestCombination("E4",0);
        TestCombination e5 = new TestCombination("E5",0);
        TestCombination e6 = new TestCombination("E6",0);
        TestCombination e7 = new TestCombination("E7",0);
        TestCombination e8 = new TestCombination("E8",0);
        TestCombination e9 = new TestCombination("E9",0);
        TestCombination e10 = new TestCombination("E10",0);

        TestCombination combination = Combine.one(
                                                    Combine.all(e1, e2),
                                                    Combine.one(
                                                            e3,
                                                                          Combine.one(
                                                                                        e4,
                                                                                        Combine.all(e5, e6)
                                                                                  ),
                                                                          e7
                                                                ),
                                                    Combine.one(
                                                                    Combine.all(e8, e9),
                                                                    e10
                                                                )
                                                 );

        CombineViewer.attachCombinViewer(combination, input_layout);
    }



}
