package com.muabe.sample;

import android.view.View;

import com.markjmind.uni.UniFragment;
import com.markjmind.uni.mapper.annotiation.Layout;
import com.markjmind.uni.mapper.annotiation.OnClick;
import com.muabe.sample.menu.MoveTestFragment;
import com.muabe.sample.menu.combine.CombineFragment;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong - Oh)
 * @since 2018-04-30
 */
@Layout(R.layout.main)
public class MainFragment extends UniFragment {

    @OnClick
    public void  combine(View view){
        getBuilder().replace(new CombineFragment());
    }

    @OnClick
    public void  move_test(View view){
        getBuilder().replace(new MoveTestFragment());
    }
}
