package com.markjmind.propose.extension.binder;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.databinding.BindingAdapter;

public class EditTextBindingAdapter {
    @BindingAdapter(value = {"edit:right_icon_check", "edit:right_icon"}, requireAll = false)
    public static void right_icon_empty_gone(EditText editText, String right_icon_check, Drawable right_icon) {
        if (!TextUtils.isEmpty(right_icon_check)) {
            Drawable[] drawables = editText.getCompoundDrawables();
            editText.setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], right_icon, drawables[3]);
        } else {
            Drawable[] drawables = editText.getCompoundDrawables();
            editText.setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], null, drawables[3]);
        }
    }
}
