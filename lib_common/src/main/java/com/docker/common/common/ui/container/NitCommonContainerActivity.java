package com.docker.common.common.ui.container;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.docker.common.R;
import com.docker.common.common.command.NitContainerCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonContainerOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.vm.NitEmptyVm;
import com.docker.common.databinding.CommonActivityContainerBinding;

/*
 * 通用容器单一fragment activity
 *
 *
 * */
@Route(path = AppRouter.COMMON_CONTAINER)
public class NitCommonContainerActivity extends NitCommonActivity<NitEmptyVm, CommonActivityContainerBinding> {

    /*
     * fragment router path
     * */
    private Fragment fragment;

    private CommonContainerOptions commonContainerOptions;

    private String vmpath;

    @Override
    public void initView() {
        commonContainerOptions = (CommonContainerOptions) getIntent().getSerializableExtra(Constant.ContainerParam);
        vmpath = getIntent().getStringExtra(Constant.ContainerCommand);
        fragment = NitCommonContainerFragment.newinstance(commonContainerOptions.commonListOptions);
        FragmentUtils.add(getSupportFragmentManager(), fragment, R.id.frame_root);
        if (TextUtils.isEmpty(commonContainerOptions.title)) {
            mToolbar.hide();
        } else {
            mToolbar.setTitle(commonContainerOptions.title);
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
        return (NitContainerCommand) () -> {
            try {
                Class clszz = Class.forName(vmpath);
                return clszz;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        };
    }

    @Override
    protected int getLayoutId() {
        return R.layout.common_activity_container;
    }

    @Override
    public NitEmptyVm getmViewModel() {
        return ViewModelProviders.of(this, factory).get(NitEmptyVm.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
