package com.docker.cirlev2.ui.create;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2CreateActivityBinding;
import com.docker.cirlev2.databinding.Circlev2SampleActivityBinding;
import com.docker.cirlev2.vm.CircleCreateViewModel;
import com.docker.cirlev2.vm.CircleMinesViewModel;
import com.docker.cirlev2.vm.SampleListViewModel;
import com.docker.cirlev2.vo.vo.CircleCreateVo;
import com.docker.common.common.command.NitContainerCommand;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.container.NitCommonContainerFragment;

import timber.log.Timber;

import static com.docker.common.common.config.Constant.KEY_RVUI_HOR;
import static com.docker.common.common.config.Constant.KEY_RVUI_LINER;


/*
*     编辑圈子
*
*   ARouter.getInstance().build(AppRouter.CIRCLE_CREATE_v2)
                    .withString("circleid", "245")
                    .withString("utid", "98699115f2260ef14486f745fc72dbd1")
                    .navigation();
* */


/*  创建圈子
flag 1 企业    2 兴趣    3国别
*   ARouter.getInstance().build(AppRouter.CIRCLE_CREATE_v2)
                    .withInt("flag", ((CircleCreateCardVo) item).flag)
                    .navigation();
* */

@Route(path = AppRouter.CIRCLE_CREATE_v2)
public class CircleCreateActivity extends NitCommonActivity<SampleListViewModel, Circlev2CreateActivityBinding> {


    @Autowired
    int flag;  // 创建圈子类型

    @Autowired
    String circleid; // 要编辑的圈子id

    @Autowired
    String utid; // 要编辑的圈子

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
        mToolbar.hide();

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.RvUi = KEY_RVUI_LINER;
        commonListOptions.falg = 101;
        commonListOptions.ReqParam.put("flag", String.valueOf(flag));
        commonListOptions.ReqParam.put("circleid", circleid);
        commonListOptions.ReqParam.put("utid", utid);
        NitCommonContainerFragment createCircleFragment = NitCommonContainerFragment.newinstance(commonListOptions);
        FragmentUtils.add(getSupportFragmentManager(), createCircleFragment, R.id.circlev2_frame);
    }

    private void processCreateFrame() {
        switch (flag) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }

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
        NitContainerCommand nitContainerCommand = null;
        switch (flag) {
            case 101:
                nitContainerCommand = (NitContainerCommand) () -> CircleCreateViewModel.class;
                break;
        }
        return nitContainerCommand;
    }

}
