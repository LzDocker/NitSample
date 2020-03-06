package com.docker.cirlev2.ui.create;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2CreateActivityBinding;
import com.docker.cirlev2.databinding.Circlev2SampleActivityBinding;
import com.docker.cirlev2.ui.CircleInfoActivity;
import com.docker.cirlev2.vm.CircleCreateViewModel;
import com.docker.cirlev2.vm.CircleMinesViewModel;
import com.docker.cirlev2.vm.SampleListViewModel;
import com.docker.cirlev2.vo.param.StaCirParam;
import com.docker.cirlev2.vo.vo.CircleCreateVo;
import com.docker.common.common.command.NitContainerCommand;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.container.NitCommonContainerFragment;

import timber.log.Timber;

import static com.docker.common.common.config.Constant.KEY_RVUI_HOR;
import static com.docker.common.common.config.Constant.KEY_RVUI_LINER;

@Route(path = AppRouter.CIRCLE_CREATE_v2)
public class CircleCreateActivity extends NitCommonActivity<SampleListViewModel, Circlev2CreateActivityBinding> {
    public static final int CIRCLE_TYPE_COMPANY = 1;    // 企业圈
    public static final int CIRCLE_TYPE_ACTIVE = 2;    // 兴趣圈
    public static final int CIRCLE_TYPE_COUNTRY = 3;    // 国别圈

    @Autowired
    int flag;  // 创建圈子类型

    @Autowired
    String circleid; // 要编辑的圈子id

    @Autowired
    String utid; // 要编辑的圈子

    public static void startMe(Context context, int type, String utid, String circleID) {
        Intent intent = new Intent(context, CircleCreateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("TYPE", type);
        bundle.putSerializable("mUtid", utid);
        bundle.putSerializable("circleID", circleID);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.circlev2_create_activity;
    }

    @Override
    public SampleListViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(SampleListViewModel.class);
    }

    @Override
    public void initView() {
//        mToolbar.hide();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("sss", utid + "onCreate: ==============" + circleid);
        if (TextUtils.isEmpty(utid)) {
            mToolbar.setTitle("创建");
        } else {
            mToolbar.setTitle("编辑");
        }
        switch (flag) {
            case CIRCLE_TYPE_COMPANY:
                FragmentUtils.add(getSupportFragmentManager(), CircleCompanyFragment.newInstance(utid, circleid), R.id.circlev2_frame);
                break;
            case CIRCLE_TYPE_ACTIVE:
                FragmentUtils.add(getSupportFragmentManager(), CircleActiveFragment.newInstance(utid, circleid), R.id.circlev2_frame);
                break;
            case CIRCLE_TYPE_COUNTRY:
                FragmentUtils.add(getSupportFragmentManager(), CircleConutryFragment.newInstance(utid, circleid), R.id.circlev2_frame);
                break;
        }

        mBinding.activityCreatcircleBtn.setOnClickListener(v -> {
            switch (flag) {
                case CIRCLE_TYPE_COMPANY:
                    ((CircleCompanyFragment) FragmentUtils.findFragment(getSupportFragmentManager(), CircleCompanyFragment.class)).create();
                    break;
                case CIRCLE_TYPE_ACTIVE:
                    ((CircleActiveFragment) FragmentUtils.findFragment(getSupportFragmentManager(), CircleActiveFragment.class)).create();
                    break;
                case CIRCLE_TYPE_COUNTRY:
                    ((CircleConutryFragment) FragmentUtils.findFragment(getSupportFragmentManager(), CircleConutryFragment.class)).create();
                    break;
            }
        });
    }

    @Override
    public void initObserver() {

    }

    @Override
    public void initRouter() {
        ARouter.getInstance().inject(this);
    }


    @Override
    public NitContainerCommand providerNitContainerCommand(int flag) {
        return null;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (flag) {
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
