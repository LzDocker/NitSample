package com.docker.apps.active.ui.index;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.ArrayUtils;
import com.dcbfhd.utilcode.utils.CollectionUtils;
import com.docker.apps.R;
import com.docker.apps.databinding.ProActiveIndexActivityBinding;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.adapter.CommonpagerStateAdapter;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.vm.NitEmptyViewModel;
import com.docker.common.common.vo.WorldNumList;
import com.docker.common.common.widget.indector.CommonIndector;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.lang.reflect.Array;
import java.util.ArrayList;

@Route(path = AppRouter.ACTIVE_INDEX)
public class ActiveIndexActivity extends NitCommonActivity<NitEmptyViewModel, ProActiveIndexActivityBinding> {

    ArrayList<Fragment> fragments;
    ArrayList<String> titles;
    CommonNavigator commonNavigator;
    CommonIndector commonIndector;

    @Override
    public void initView() {
        mToolbar.hide();
        mBinding.linBack.setOnClickListener(v -> finish());

        fragments = new ArrayList<>();
        titles = new ArrayList<>();
        titles.add("精选");
        titles.add("北京");
        titles.add("品酒会");
        titles.add("成立会");
        titles.add("股东会");
        titles.add("黔酒行");
        fragments.add(ActiveContainerFragment.getInstance());
        fragments.add(ActiveContainerFragment.getInstance());
        fragments.add(ActiveContainerFragment.getInstance());
        fragments.add(ActiveContainerFragment.getInstance());
        fragments.add(ActiveContainerFragment.getInstance());
        fragments.add(ActiveContainerFragment.getInstance());

        // magic
        mBinding.viewpager.setAdapter(new CommonpagerStateAdapter(getSupportFragmentManager(), fragments));
        commonIndector = new CommonIndector();
        commonIndector.mTitleList = titles;
        commonNavigator = commonIndector.initMagicIndicatorScrollSpac(mBinding.viewpager, mBinding.magic, this);
        // magic


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
        return R.layout.pro_active_index_activity;
    }

    @Override
    public NitEmptyViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(NitEmptyViewModel.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3001 && resultCode == RESULT_OK) {
            if (mBinding.viewpager.getCurrentItem() == 1) {
                WorldNumList.WorldEnty worldEnty = (WorldNumList.WorldEnty) data.getSerializableExtra("datasource");
                if (mBinding.viewpager.getCurrentItem() == 1) {
                    commonIndector.mTitleList.remove(1);
                    commonIndector.mTitleList.add(1, worldEnty.name);
                    commonNavigator.getAdapter().notifyDataSetChanged();
                }
            }
            fragments.get(mBinding.viewpager.getCurrentItem()).onActivityResult(requestCode, resultCode, data);
        }
    }
}

