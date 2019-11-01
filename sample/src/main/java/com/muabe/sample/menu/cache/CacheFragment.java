package com.muabe.sample.menu.cache;

import android.util.Log;
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
public class CacheFragment extends UniFragment {
    @GetView
    ViewGroup input_layout;

    @GetView
    Button opt;

    CacheCombination combination;

    @Override
    public void onPost() {
        init(false);
//        Combine.optimize(combination);

    }

    private void init(boolean optimize){
        input_layout.removeAllViews();

        CacheCombination e1 = new CacheCombination("a",0);
        CacheCombination e2 = new CacheCombination("b",0);
        CacheCombination e3 = new CacheCombination("c",0);
        CacheCombination e4 = new CacheCombination("d",0);
        CacheCombination e5 = new CacheCombination("e",0);
        CacheCombination e6 = new CacheCombination("f",0);
        CacheCombination e7 = new CacheCombination("g",0);
        CacheCombination e8 = new CacheCombination("h",0);
        CacheCombination e9 = new CacheCombination("i",0);
        CacheCombination e10 = new CacheCombination("j",0);

        combination = Combine.all(optimize,
                Combine.all(optimize, e1, e2).setName("1-1"),
                Combine.all(optimize,
                        e3,
                        Combine.all(optimize,
                                e4,
                                Combine.all(optimize, e5, e6).setName("last")
                        ).setName("1-2-2"),
                        e7
                ).setName("1-2"),
                Combine.all(optimize,
                        Combine.all(optimize, e8, e9),
                        e10
                ).setName("1-3")
        );
        combination.setName("root");
        e8.setValue(1);
        e9.setValue(2);
        Combine.scan(combination, 1);
        Combine.scan(combination, 1);
        Combine.scan(combination, 1);
        Log.e("dd","-----------------------------");

        CombineViewer.attachCombinViewer(combination, input_layout);

        e8.setValue(0);
        e9.setValue(0);
        e4.setValue(1);
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
        input_layout.removeAllViews();
        combination.setName("root");
        Combine.scan(combination, 1);
        CombineViewer.attachCombinViewer(combination, input_layout);
        Log.e("dd", combination.getChild().get(1).getName());
    }
}
