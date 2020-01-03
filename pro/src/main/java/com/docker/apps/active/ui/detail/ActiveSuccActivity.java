package com.docker.apps.active.ui.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.apps.R;
import com.docker.apps.active.ui.index.ActiveContainerFragment;
import com.docker.apps.active.widget.CardActivePopup;
import com.docker.apps.databinding.ProActiveIndexActivityBinding;
import com.docker.apps.databinding.ProActiveSuccActivityBinding;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.XPopup.XPopupActivity;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.vm.NitEmptyViewModel;
import com.docker.common.common.vo.WorldNumList;
import com.docker.common.common.widget.XPopup.CenterPopup;
import com.docker.common.common.widget.indector.CommonIndector;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.XPopupCallback;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;

@Route(path = AppRouter.ACTIVE_SUCC)
public class ActiveSuccActivity extends NitCommonActivity<NitEmptyViewModel, ProActiveSuccActivityBinding> {


    private BasePopupView basePopupView;

    @Override
    public void initView() {
        mToolbar.setTitle("报名成功");

        mBinding.tvLookCard.setOnClickListener(v -> {
            // dialog
            CardActivePopup centerPopup = new CardActivePopup(this);
            basePopupView = new XPopup.Builder(this).setPopupCallback(new XPopupCallback() {
                @Override
                public void onCreated() {

                }

                @Override
                public void beforeShow() {

                }

                @Override
                public void onShow() {

                }

                @Override
                public void onDismiss() {

                }

                @Override
                public boolean onBackPressed() {
                    return false;
                }
            }).asCustom(centerPopup).show();

        });

        mBinding.tvBack.setOnClickListener(v -> finish());
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
        return R.layout.pro_active_succ_activity;
    }

    @Override
    public NitEmptyViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(NitEmptyViewModel.class);
    }

}

