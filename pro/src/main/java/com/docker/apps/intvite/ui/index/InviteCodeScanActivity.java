package com.docker.apps.intvite.ui.index;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.apps.R;
import com.docker.apps.databinding.ProInviteCodeScanActivityBinding;
import com.docker.apps.databinding.ProInviteIndexActivityBinding;
import com.docker.apps.intvite.vo.card.InviteCardVo;
import com.docker.apps.intvite.vo.card.InviteHeadCardVo;
import com.docker.apps.intvite.vo.card.InviteRewardVo;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vm.NitEmptyViewModel;
import com.docker.common.common.widget.card.NitBaseProviderCard;
import com.umeng.socialize.UMShareAPI;

@Route(path = AppRouter.INVITE_INDEX_CODE_SCAN)
public class InviteCodeScanActivity extends NitCommonActivity<NitEmptyViewModel, ProInviteCodeScanActivityBinding> {

    @Override
    public void initView() {
        mToolbar.setTitle("邀人推广");
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
