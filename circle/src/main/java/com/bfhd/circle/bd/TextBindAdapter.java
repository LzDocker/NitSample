package com.bfhd.circle.bd;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.widget.TextView;

import com.bfhd.circle.R;
import com.bfhd.circle.vo.ServiceDataBean;

public class TextBindAdapter {

    @BindingAdapter("android:text")
    public static void setSpannelText(TextView textView, ServiceDataBean serviceDataBean) {
        boolean isSelfShop = false;
        boolean isStoreShop = false;
        boolean isIdleShop = false;
        boolean isClose = true; // 沙雕

        int index = 0;
        StringBuilder title = new StringBuilder();
        if (CheckServerData(serviceDataBean)) {
            if ("1".equals(serviceDataBean.autotrophy)) {
                title.append("   ");
                isSelfShop = true;
            }
            if (CheckServerExtData(serviceDataBean.getExtData())) {

                if (!isClose) {
                    if ("2".equals(serviceDataBean.getExtData().getType())) { // 商品
                        title.append("    ");
                        isStoreShop = true;

                    } else if ("1".equals(serviceDataBean.getExtData().getType())) { // 闲置
                        title.append("    ");
                        isIdleShop = true;
                    }
                }

                if (TextUtils.isEmpty(serviceDataBean.getExtData().getName())) {
                    title.append(serviceDataBean.getExtData().getContent());
                } else {
                    title.append(serviceDataBean.getExtData().getName()).append(serviceDataBean.getExtData().getContent());
                }
            }
        }
        SpannableString spannableString = new SpannableString(title);
        if (isSelfShop) {
            Drawable d = textView.getContext().getResources().getDrawable(R.mipmap.circle_card_zy);
            d.setBounds(0, 15, d.getIntrinsicWidth(), d.getIntrinsicHeight() + 15);
            ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
            spannableString.setSpan(span, index, index += 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        if (!isClose) {
            if (isStoreShop) {
                Drawable d1 = textView.getContext().getResources().getDrawable(R.mipmap.circle_card_zy);
                d1.setBounds(0, 15, d1.getIntrinsicWidth(), d1.getIntrinsicHeight() + 15);
                ImageSpan span1 = new ImageSpan(d1, ImageSpan.ALIGN_BASELINE);
                spannableString.setSpan(span1, index + 1, index += 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            if (isIdleShop) {
                Drawable d1 = textView.getContext().getResources().getDrawable(R.mipmap.circle_card_zy);
                d1.setBounds(0, 15, d1.getIntrinsicWidth(), d1.getIntrinsicHeight() + 15);
                ImageSpan span1 = new ImageSpan(d1, ImageSpan.ALIGN_BASELINE);
                spannableString.setSpan(span1, index + 1, index += 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        textView.setText(spannableString);
    }

    public static boolean CheckServerData(ServiceDataBean serviceDataBean) {
        if (serviceDataBean == null) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean CheckServerExtData(ServiceDataBean.ExtDataBean extDataBean) {
        if (extDataBean == null) {
            return false;
        } else {
            return true;
        }
    }
}
