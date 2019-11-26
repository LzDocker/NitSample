package com.docker.common.common.widget.XPopup;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.docker.common.R;
import com.lxj.xpopup.core.DrawerPopupView;
import java.util.Random;

import io.reactivex.annotations.NonNull;

/**
 * Description: 自定义抽屉弹窗
 * Create by dance, at 2018/12/20
 */
public class CustomDrawerPopupView extends DrawerPopupView {
    TextView text;
    public CustomDrawerPopupView(@NonNull Context context) {
        super(context);
    }
    @Override
    protected int getImplLayoutId() {
        return R.layout.common_custom_drawer_popup;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        Log.e("tag", "CustomDrawerPopupView onCreate");
        text = findViewById(R.id.text);
        findViewById(R.id.btn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        text.setText(new Random().nextInt()+"");

        //通过设置topMargin，可以让Drawer弹窗进行局部阴影展示
//        ViewGroup.MarginLayoutParams params = (MarginLayoutParams) getPopupContentView().getLayoutParams();
//        params.topMargin = 450;
    }

    @Override
    protected void onShow() {
        super.onShow();
        Log.e("tag", "CustomDrawerPopupView onShow");
    }

    @Override
    protected void onDismiss() {
        super.onDismiss();
        Log.e("tag", "CustomDrawerPopupView onDismiss");
    }
}