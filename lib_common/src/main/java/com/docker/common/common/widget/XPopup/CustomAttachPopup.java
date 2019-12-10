package com.docker.common.common.widget.XPopup;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.docker.common.R;
import com.lxj.xpopup.core.HorizontalAttachPopupView;

import io.reactivex.annotations.NonNull;

public class CustomAttachPopup extends HorizontalAttachPopupView {
    public CustomAttachPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.common_custom_attach_popup;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        findViewById(R.id.tv_zan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });
        findViewById(R.id.tv_comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });
    }
}
