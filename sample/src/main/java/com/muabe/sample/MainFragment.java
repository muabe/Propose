package com.muabe.sample;

import android.util.Log;
import android.widget.Button;

import com.markjmind.uni.UniFragment;
import com.markjmind.uni.mapper.annotiation.GetView;
import com.markjmind.uni.mapper.annotiation.Layout;
import com.muabe.propose.Motion2;
import com.muabe.propose.OnPlayListener;
import com.muabe.propose.Propose;
import com.muabe.propose.guesture.RightGesture;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong - Oh)
 * @email markjmind@gmail.com
 * @since 2018-04-30
 */
@Layout(R.layout.main)
public class MainFragment extends UniFragment{
    @GetView
    Button button;

    @Override
    public void onPre() {
        Motion2 motion2 = new Motion2(new RightGesture());
        motion2.setOnPlayListener(new OnPlayListener() {
            @Override
            public boolean play(float distance) {
                Log.e("dd", "distance:"+distance);

                return true;
            }
        });

        Propose propose = new Propose();
        propose.addMotion(motion2);
        button.setOnTouchListener(propose);

    }
}
