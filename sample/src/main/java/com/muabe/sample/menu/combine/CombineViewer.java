package com.muabe.sample.menu.combine;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.markjmind.uni.common.Jwc;
import com.muabe.combination.Combination;
import com.muabe.combination.Combine;
import com.muabe.sample.R;

import java.util.ArrayList;

public class CombineViewer{
    interface FindElement{
        void onFind(TextView textView);
    }

   public static void attachCombinViewer(Combination combination, ViewGroup attachView, FindElement findElement){
        ViewGroup root = addLine(1, attachView)[0];
        root.findViewById(R.id.t_line).setVisibility(View.INVISIBLE);
        root.findViewById(R.id.c_line).setVisibility(View.INVISIBLE);
        loop(combination, attachView, findElement);
    }

    public static void attachCombinViewer(Combination combination, ViewGroup attachView){
        attachCombinViewer(combination, attachView, null);
    }

    private static void loop(Combination combination, ViewGroup input, FindElement findElement){
        if(combination.getMode() == Combine.ELEMENT){
            input.findViewById(R.id.b_line).setVisibility(View.INVISIBLE);
            TextView contents = input.findViewById(R.id.contents);
            contents.setBackgroundColor(Color.GRAY);
            contents.setText(combination.name);
            if(findElement != null){
                findElement.onFind(contents);
            }
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
                loop(list.get(i), cells[i], findElement);
                setLine(cells[i], i, list.size());
            }
        }
    }

    private static LinearLayout[] addLine(int sum, ViewGroup input){
        LinearLayout line = (LinearLayout) Jwc.getInfalterView(input.getContext(), R.layout.combine_line);
        line.setWeightSum(sum);
        LinearLayout[] cells = new LinearLayout[sum];
        for(int i=0; i<cells.length; i++){
            cells[i] = (LinearLayout)Jwc.getInfalterView(input.getContext(), R.layout.combine_cell);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            cells[i].setLayoutParams(params);
            line.addView(cells[i]);
        }
        input.addView(line);
        return cells;
    }

    private static void setLine(LinearLayout cell, int index, int max){
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
