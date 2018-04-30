package com.muabe.sample;

import android.app.Activity;
import android.os.Bundle;

import com.markjmind.uni.boot.SimpleBoot;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SimpleBoot.putContentView(this)
                .setHomeFragment(new IntroFragment());
    }

    @Override
    public void onBackPressed() {
        if(!SimpleBoot.onBackPressed(this)) {
            super.onBackPressed();
        }
    }
}
