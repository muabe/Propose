package com.markjmind.test;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ClothesDialog extends Dialog {
    public ClothesDialog(@NonNull Context context) {
        super(context);
    }

    public ClothesDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected ClothesDialog(@NonNull Context context, boolean cancelable, @Nullable DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams wl = new WindowManager.LayoutParams();
        wl.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        wl.dimAmount = 0.8f;
        getWindow().setAttributes(wl);
        setContentView(R.layout.clothes_dialog);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
