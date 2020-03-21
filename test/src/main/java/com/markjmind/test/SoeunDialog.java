package com.markjmind.test;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;
import com.markjmind.propose.Motion;
import com.markjmind.propose.Propose;
import com.markjmind.propose.animation.AnimationBuilder;
import com.markjmind.propose.listener.MotionListener;

public class SoeunDialog extends Dialog {
    View flower_touch;
    TextView per;
    Button send;
    LottieAnimationView flower;
    Propose propose;

    public SoeunDialog(@NonNull Context context) {
        super(context);
    }

    public SoeunDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected SoeunDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams wl = new WindowManager.LayoutParams();
        wl.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        wl.dimAmount = 0.8f;
        getWindow().setAttributes(wl);

        setContentView(R.layout.soeun_dialog);

        per = findViewById(R.id.per);
        send = findViewById(R.id.send);
        flower = findViewById(R.id.flower);
        flower_touch = findViewById(R.id.flower);

        Motion motion = new Motion(Motion.PRESS);
        motion.play(AnimationBuilder.create(flower, 2500).lottie(),0);
        motion.enableSingleTabUp(false);
        propose = new Propose(this.getContext());
        propose.addMotion(motion);

        motion.setMotionListener(new MotionListener() {
            @Override
            public void onStart(Motion motion) {

            }

            @Override
            public void onScroll(Motion motion, long currDuration, long totalDuration) {
                int perInt = (int)(currDuration*100/totalDuration);
                per.setText(perInt+"%");
                if(perInt == 100){
                    send.setVisibility(View.VISIBLE);
                }else{
                    if(send.getVisibility() == View.VISIBLE){
                        send.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onEnd(Motion motion) {

            }
        });
        flower_touch.setOnTouchListener(propose);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

}
