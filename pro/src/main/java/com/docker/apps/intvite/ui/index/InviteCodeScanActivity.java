package com.docker.apps.intvite.ui.index;

import android.arch.lifecycle.ViewModelProviders;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.apps.BR;
import com.docker.apps.R;
import com.docker.apps.databinding.ProInviteCodeScanActivityBinding;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vm.NitEmptyViewModel;

@Route(path = AppRouter.INVITE_INDEX_CODE_SCAN)
public class InviteCodeScanActivity extends NitCommonActivity<NitEmptyViewModel, ProInviteCodeScanActivityBinding> {

    @Autowired()
    String code;

    @Override
    public void initView() {
        mToolbar.setTitle("邀人推广");
        mBinding.tvDesc.setText(CacheUtils.getUser().nickname + "邀请你注册桃源公社");
        code = getIntent().getStringExtra("code");
        mBinding.setVariable(BR.barcode, code);
    }

    @Override
    public void initObserver() {

    }

    @Override
    public void initRouter() {
        ARouter.getInstance().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pro_invite_code_scan_activity;
    }

    @Override
    public NitEmptyViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(NitEmptyViewModel.class);
    }

}
