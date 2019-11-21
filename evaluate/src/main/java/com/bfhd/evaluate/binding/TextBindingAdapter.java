package com.bfhd.evaluate.binding;

import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.bfhd.evaluate.R;

public class TextBindingAdapter {
    @BindingAdapter("textIsChoose")
    public static void textIsChoose(View view, boolean show) {
        ((TextView) view).setTextColor(show ? Color.parseColor("#379DF2") : Color.parseColor("#121212"));
    }

    @BindingAdapter("textIsRed")
    public static void textIsRed(View view, int show) {
        ((TextView) view).setTextColor(show == 1 ? Color.WHITE : Color.parseColor("#666666"));
    }

    @BindingAdapter("textWarning")
    public static void textWarning(View view, String show) {
        if (TextUtils.isEmpty(show)) {
            view.setVisibility(View.GONE);
        } else {
            ((TextView) view).setText("警报次数:" + show);
            view.setVisibility(View.VISIBLE);
        }
    }

    @BindingAdapter("textPassColor")
    public static void textPassColor(View view, String num) {
        if (TextUtils.isEmpty(num))
            return;
        String str = ",已通过 " + num + " 个";
        SpannableStringBuilder style = new SpannableStringBuilder(str);
        style.setSpan(new ForegroundColorSpan(Color.parseColor("#379DF2")), 4, str.length() - 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        ((TextView) view).setText(style);
    }

    @BindingAdapter("textIsParse")
    public static void textIsParse(View view, String parse) {
        TextView view1 = (TextView) view;
        if (TextUtils.isEmpty(parse))
            return;
        Drawable drawable;
        if (parse.equals("1")) {
            drawable = view.getResources().getDrawable(R.mipmap.radio_right);
            view1.setText("已通过");
        } else {
            view1.setText("未通过");
            drawable = view.getResources().getDrawable(R.mipmap.radio_wrong);
        }
        view1.setCompoundDrawables(drawable, null, null, null);
    }

    @BindingAdapter("setTextForHtml")
    public static void setTextForHtml(View v, String s) {
        if (TextUtils.isEmpty(s))
            return;
        TextView view1 = (TextView) v;
        view1.setText(Html.fromHtml(s));
    }

    @BindingAdapter(value = {"setTxtFraction", "setPrice", "setType"}, requireAll = false)
    public static void setTxtFraction(View v, int setTxtFraction, int setPrice, String setType) {
        TextView view1 = (TextView) v;
        if ("1".equals(setType)) {
            if (setTxtFraction > 0) {
                view1.setTextColor(Color.parseColor("#57AB44"));
            } else {
                view1.setTextColor(Color.parseColor("#ff333333"));
           }
        } else {
            if (setPrice > 0 && setTxtFraction > 0) {
                view1.setTextColor(Color.parseColor("#57AB44"));
            } else {
                view1.setTextColor(Color.parseColor("#ff333333"));

            }
        }
    }
}

