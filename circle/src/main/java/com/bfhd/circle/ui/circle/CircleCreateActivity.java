package com.bfhd.circle.ui.circle;

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
import com.bfhd.circle.databinding.CircleActivityCircleCreateBinding;
import com.bfhd.circle.ui.circle.circlecreate.CircleActiveFragment;
import com.bfhd.circle.ui.circle.circlecreate.CircleCompanyFragment;
import com.bfhd.circle.ui.circle.circlecreate.CircleConutryFragment;
import com.bfhd.circle.vm.CircleViewModel;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.docker.common.common.router.AppRouter;

import javax.inject.Inject;

/*
 * 新建圈子
 * 编辑圈子
 * */
@Route(path = AppRouter.CIRCLE_CREATE)
public class CircleCreateActivity extends HivsBaseActivity<CircleViewModel, CircleActivityCircleCreateBinding> {


    public static final int CIRCLE_TYPE_COMPANY = 1;    // 企业圈
    public static final int CIRCLE_TYPE_ACTIVE = 2;    // 兴趣圈
    public static final int CIRCLE_TYPE_COUNTRY = 3;    // 国别圈
    private int mCreateType = CIRCLE_TYPE_COMPANY;
    private String mUtid;
    private String mCircleID;

    public static void startMe(Context context, int type, String utid,String circleID) {
        Intent intent = new Intent(context, CircleCreateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("TYPE", type);
        bundle.putSerializable("mUtid", utid);
        bundle.putSerializable("circleID", circleID);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Inject
    ViewModelProvider.Factory factory;

    @Override
    protected int getLayoutId() {
        return R.layout.circle_activity_circle_create;
    }


    @Override
    public CircleViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleViewModel.class);
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mCreateType = (int) getIntent().getExtras().get("TYPE");
        mUtid = getIntent().getStringExtra("mUtid");
        mCircleID = getIntent().getStringExtra("circleID");
        super.onCreate(savedInstanceState);
        mBinding.setViewmodel(mViewModel);
        if (TextUtils.isEmpty(mUtid)) {
            mToolbar.setTitle("创建圈子");
        } else {
            mToolbar.setTitle("编辑圈子");

        }
    }

    @Override
    public void initView() {
        switch (mCreateType) {
            case CIRCLE_TYPE_COMPANY:
                FragmentUtils.add(getSupportFragmentManager(), CircleCompanyFragment.newInstance(mUtid,mCircleID), R.id.circle_publish_frame);
                break;
            case CIRCLE_TYPE_ACTIVE:
                FragmentUtils.add(getSupportFragmentManager(), CircleActiveFragment.newInstance(mUtid,mCircleID), R.id.circle_publish_frame);
                break;
            case CIRCLE_TYPE_COUNTRY:
                FragmentUtils.add(getSupportFragmentManager(), CircleConutryFragment.newInstance(mUtid,mCircleID), R.id.circle_publish_frame);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (mCreateType) {
            case CIRCLE_TYPE_COMPANY:
                FragmentUtils.findFragment(getSupportFragmentManager(), CircleCompanyFragment.class).onActivityResult(requestCode, resultCode, data);
                break;
            case CIRCLE_TYPE_ACTIVE:
                FragmentUtils.findFragment(getSupportFragmentManager(), CircleActiveFragment.class).onActivityResult(requestCode, resultCode, data);
                break;
            case CIRCLE_TYPE_COUNTRY:
                FragmentUtils.findFragment(getSupportFragmentManager(), CircleConutryFragment.class).onActivityResult(requestCode, resultCode, data);
                break;
        }
    }
}
