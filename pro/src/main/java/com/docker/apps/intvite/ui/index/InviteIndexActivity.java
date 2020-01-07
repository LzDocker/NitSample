package com.docker.apps.intvite.ui.index;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.apps.R;
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

@Route(path = AppRouter.INVITE_INDEX)
public class InviteIndexActivity extends NitCommonActivity<NitEmptyViewModel, ProInviteIndexActivityBinding> {


    @Override
    public void initView() {
        mToolbar.setTitle("邀人推广");

        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.isActParent = true;
        commonListOptions.falg = 0;
        commonListOptions.RvUi = Constant.KEY_RVUI_LINER;
        NitBaseProviderCard.providerCardNoRefreshForFrame(getSupportFragmentManager(), R.id.frame_invite, commonListOptions);

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
        return R.layout.pro_invite_index_activity;
    }

    @Override
    public NitEmptyViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(NitEmptyViewModel.class);
    }

    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {

        NitDelegetCommand nitDelegetCommand = new NitDelegetCommand() {
            @Override
            public Class providerOuterVm() {
                return null;
            }

            @Override
            public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {
                InviteHeadCardVo inviteHeadCardVo = new InviteHeadCardVo(0, 0);
                NitBaseProviderCard.providerCard(commonListVm, inviteHeadCardVo, nitCommonFragment);
                InviteCardVo inviteCardVo = new InviteCardVo(0, 1);
                NitBaseProviderCard.providerCard(commonListVm, inviteCardVo, nitCommonFragment);
                InviteRewardVo inviteRewardVo = new InviteRewardVo(0, 2);
                NitBaseProviderCard.providerCard(commonListVm, inviteRewardVo, nitCommonFragment);
            }
        };
        return nitDelegetCommand;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}