package com.markjmind.propose.extension.binder;

import android.text.Html;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TextBindingAdapter {
    @BindingAdapter(value = {"text:date", "text:dateformat"}, requireAll = false)
    public static void text_text(TextView textView, Long date, String dateformat){
        if(dateformat != null && date !=null){
            textView.setText(new SimpleDateFormat(dateformat).format(new Date(date)));
        }else{
            textView.setText(""+date);
        }
    }

    @BindingAdapter(value = {"text:html"})
    public static void text_html(TextView textView, String html){
        if(html != null){
            textView.setText(Html.fromHtml(html));
        }
    }
}