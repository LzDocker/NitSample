package com.docker.common.common.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.core.base.BaseActivity;
import com.docker.core.base.BaseVm;

/**
 * h5 跳转原生界面
 */
public class ArouterSchemeActivity extends BaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceStates) {
        super.onCreate(savedInstanceStates);
        ARouter.getInstance().inject(this);
        Uri uri = getIntent().getData();
        String[] dataurl = uri.toString().split("/?");
        // 根据uri 获取参数 跳转
        ARouter.getInstance().build(uri).navigation(this, new NavCallback() {
            @Override
            public void onArrival(Postcard postcard) {
                finish();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    public BaseVm getmViewModel() {
        return null;
    }

    @Override
    protected void inject() {
        super.inject();

    }

}
