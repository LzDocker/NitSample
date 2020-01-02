package com.bfhd.account.ui.tygs.fragment;

import android.app.ActivityManager;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.R;
import com.bfhd.account.databinding.AccountInvitationLayoutBinding;
import com.bfhd.account.vm.AccountRewardViewModel;
import com.dcbfhd.utilcode.utils.CollectionUtils;
import com.docker.cirlev2.ui.detail.CircleEditTabActivity;
import com.docker.cirlev2.vm.CircleDynamicListViewModel;
import com.docker.cirlev2.vo.entity.CircleTitlesVo;
import com.docker.cirlev2.vo.param.StaCirParam;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.widget.indector.CommonIndector;
import com.docker.common.common.widget.refresh.SmartRefreshLayout;
import com.docker.common.databinding.CommonTabFrameLayoutBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Route(path = AppRouter.ACCOUNT_invitation)
public class AccountInvitationCoutainerFragment extends NitCommonFragment<AccountRewardViewModel, AccountInvitationLayoutBinding> {

    private boolean isAddTotalTab = false;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    // 外部传参数
    private List<CircleTitlesVo> mCircleTitlesVo;
    @Autowired
    int pos;   // tab pos

    @Autowired
    int role;  // 角色

    private String[] titles = null;

    @Override
    protected int getLayoutId() {
        return R.layout.account_invitation_layout;
    }

    @Override
    public AccountRewardViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(AccountRewardViewModel.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceStates) {
        super.onCreate(savedInstanceStates);
        ARouter.getInstance().inject(this);
    }

    @Override
    protected void initView(View var1) {

    }


    @Override
    public void onReFresh(SmartRefreshLayout refreshLayout) {
        super.onReFresh(refreshLayout);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void initImmersionBar() {

    }

    @Override
    public void onVisible() {
        super.onVisible();

    }
}
