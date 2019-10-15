package com.bfhd.circle.ui.common;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.circle.R;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.databinding.CircleActivityCommonH5Binding;
import com.bfhd.circle.ui.safe.CommonWebFragment;
import com.bfhd.circle.vm.CircleViewModel;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.docker.common.common.router.AppRouter;

import javax.inject.Inject;

@Route(path = AppRouter.COMMONH5)
public class CommonH5Activity extends HivsBaseActivity<CircleViewModel, CircleActivityCommonH5Binding> {
    private String webUrl;
    private String title;
    private CommonWebFragment commonWebFragment;

    public static void startMe(Context context, String webUrl, String title) {
        Intent intent = new Intent(context, CommonH5Activity.class);
        Bundle bundle = new Bundle();
        bundle.putString("weburl", webUrl);
        bundle.putString("title", title);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    @Inject
    ViewModelProvider.Factory factory;

    @Override
    protected int getLayoutId() {
        return R.layout.circle_activity_common_h5;
    }

    @Override
    public CircleViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleViewModel.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        webUrl = getIntent().getStringExtra("weburl");
        title = getIntent().getStringExtra("title");
        super.onCreate(savedInstanceState);
        if (!TextUtils.isEmpty(title)) {
            mToolbar.setTitle(title);
        }
        if (!TextUtils.isEmpty(webUrl)) {
            commonWebFragment = CommonWebFragment.newInstance(webUrl);
        } else {
            if (!TextUtils.isEmpty(getIntent().getStringExtra("type"))) {
                commonWebFragment = CommonWebFragment.newInstance(getIntent().getStringExtra("type"), webUrl);
            }
        }
        FragmentUtils.add(getSupportFragmentManager(), commonWebFragment, R.id.frame);
    }

    @Override
    public void initView() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        commonWebFragment.onActivityResult(requestCode, resultCode, data);
    }
}
