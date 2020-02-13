package com.muabe.sample;

import android.app.Activity;
import android.os.Bundle;

import com.markjmind.uni.boot.SimpleBoot;

public class MainActivity extends Activity {
    final String version = "2.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IntroFragment introFragment = new IntroFragment();
        introFragment.param.add("version", version);

        SimpleBoot.putContentView(this)
                .setHomeFragment(introFragment);
    }

    @Override
    public void onBackPressed() {
        if(!SimpleBoot.onBackPressed(this)) {
            super.onBackPressed();
        }
    }
}
