package com.muabe.sample.menu.combine;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.markjmind.uni.UniFragment;
import com.markjmind.uni.UniLayout;
import com.markjmind.uni.common.Jwc;
import com.markjmind.uni.mapper.annotiation.GetView;
import com.markjmind.uni.mapper.annotiation.Layout;
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

        ViewGroup root = addLine(1, input_layout)[0];
        root.findViewById(R.id.t_line).setVisibility(View.INVISIBLE);
        root.findViewById(R.id.c_line).setVisibility(View.INVISIBLE);
        loop(combination, root);
    }

    void loop(TestCombination combination, ViewGroup input){
        if(combination.getMode() == Combine.ELEMENT){
            input.findViewById(R.id.b_line).setVisibility(View.INVISIBLE);
            TextView contents = input.findViewById(R.id.contents);
            contents.setBackgroundColor(Color.GRAY);
            contents.setText(combination.name);
        }else{
            ArrayList<TestCombination> list = combination.getChild(TestCombination.class);
            TextView contents = input.findViewById(R.id.contents);
            if(contents != null){
                if(combination.getMode() == Combine.AND){
                    contents.setBackgroundColor(Color.RED);
                    contents.setText("AND");
                }else if(combination.getMode() == Combine.OR){
                    contents.setBackgroundColor(Color.BLUE);
                    contents.setText("OR");
                }
            }
            LinearLayout[] cells = addLine(list.size(), input);
            for(int i=0; i<list.size(); i++){
                loop(list.get(i), cells[i]);
                setLine(cells[i], i, list.size());
            }
        }
    }

    public LinearLayout[] addLine(int sum, ViewGroup input){
        LinearLayout line = (LinearLayout)Jwc.getInfalterView(getContext(), R.layout.combine_line);
        line.setWeightSum(sum);
        LinearLayout[] cells = new LinearLayout[sum];
        for(int i=0; i<cells.length; i++){
            cells[i] = (LinearLayout)Jwc.getInfalterView(getContext(), R.layout.combine_cell);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            cells[i].setLayoutParams(params);
            line.addView(cells[i]);
        }
        input.addView(line);
        return cells;
    }

    void setLine(LinearLayout cell, int index, int max){
        if(index == 0){
            cell.findViewById(R.id.r_line).setVisibility(View.VISIBLE);
        }else if(index == max-1){
            cell.findViewById(R.id.l_line).setVisibility(View.VISIBLE);
        }else{
            cell.findViewById(R.id.r_line).setVisibility(View.VISIBLE);
            cell.findViewById(R.id.l_line).setVisibility(View.VISIBLE);
        }
    }

}
