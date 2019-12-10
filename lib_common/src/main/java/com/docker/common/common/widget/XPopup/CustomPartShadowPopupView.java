package com.docker.common.common.widget.XPopup;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.docker.common.R;
import com.lxj.xpopup.impl.PartShadowPopupView;

import io.reactivex.annotations.NonNull;

public class CustomPartShadowPopupView extends PartShadowPopupView {
    public CustomPartShadowPopupView(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.common_custom_part_shadow_popup;
    }

    TextView text;

    @Override
    protected void onCreate() {
        super.onCreate();
        text = findViewById(R.id.text);
        Log.e("tag", "CustomPartShadowPopupView onCreate");
//        findViewById(R.id.btnClose).setOnClickListener(v -> dismiss());
//        findViewById(R.id.ch).setOnClickListener(v -> text.setText(text.getText() + "\n 啦啦啦啦啦啦"));
    }

    @Override
    protected void onShow() {
        super.onShow();
        Log.e("tag", "CustomPartShadowPopupView onShow");
    }

    @Override
    protected void onDismiss() {
        super.onDismiss();
        Log.e("tag", "CustomPartShadowPopupView onDismiss");
    }
}
