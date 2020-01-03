package com.docker.apps.active.ui.index;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.docker.apps.R;
import com.docker.apps.active.vo.FilterVo;
import com.docker.apps.databinding.ProActiveCoutainerFragmentBinding;
import com.docker.cirlev2.BR;
import com.docker.common.common.adapter.NitAbsSampleAdapter;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.ui.base.NitCommonListFragment;
import com.docker.common.common.vm.NitEmptyViewModel;
import com.docker.common.common.vo.WorldNumList;
import com.docker.common.common.widget.XPopup.FilterPopupView;
import com.lxj.xpopup.XPopup;

import static android.app.Activity.RESULT_OK;

public class ActiveContainerFragment extends NitCommonFragment<NitEmptyViewModel, ProActiveCoutainerFragmentBinding> {

    Fragment fragment;

    FilterPopupView filterPopupView;

    private NitAbsSampleAdapter mFilterAdapter;

    public static ActiveContainerFragment getInstance() {
        ActiveContainerFragment fragment = new ActiveContainerFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pro_active_coutainer_fragment;
    }

    @Override
    protected NitEmptyViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(NitEmptyViewModel.class);
    }

    @Override
    protected void initView(View var1) {
        fragment = (Fragment) ARouter.getInstance()
                .build(AppRouter.CIRCLE_DYNAMIC_LIST_FRAME)
                .navigation();

        FragmentUtils.add(getChildFragmentManager(), fragment, R.id.frame_active);


        initFilterAdapter();

        mBinding.get().tvSortZone.setOnClickListener(v -> {
            if (filterPopupView == null) {
                filterPopupView =
                        (FilterPopupView) new XPopup.Builder(ActiveContainerFragment.this.getHoldingActivity())
                                .atView(mBinding.get().llFilter)
                                .asCustom(new FilterPopupView(ActiveContainerFragment.this.getHoldingActivity()));
                filterPopupView.providerPopAdapter(mFilterAdapter);
            }
            if (!filterPopupView.isShow()) {
                filterPopupView.show();
            }
        });

        mBinding.get().tvSortArea.setOnClickListener(v -> {
            ARouter.getInstance().build(AppRouter.ACCOUNT_COUNTRY).navigation(this.getHoldingActivity(), 3001);
        });
    }

    private void initFilterAdapter() {
        mFilterAdapter = new NitAbsSampleAdapter(R.layout.pro_active_item_filter, BR.item) {
            @Override
            public void onRealviewBind(ViewHolder holder, int position) {
            }
        };
        FilterVo filterVo0 = new FilterVo();
        filterVo0.title = "综合排序";
        filterVo0.state = 0;
        filterVo0.setChecked(true);

        FilterVo filterVo = new FilterVo();
        filterVo.title = "最新发布";
        filterVo.state = 1;

        FilterVo filterVo1 = new FilterVo();
        filterVo1.title = "最多参与";
        filterVo1.state = 2;


        mFilterAdapter.add(filterVo0);
        mFilterAdapter.add(filterVo);
        mFilterAdapter.add(filterVo1);

        mFilterAdapter.setOnItemClickListener((view, position) -> {
            filterPopupView.dismiss();
            mBinding.get().tvSortZone.setText(((FilterVo) mFilterAdapter.getItem(position)).title);
            ((NitCommonListFragment) fragment).onReFresh(null);
            for (int i = 0; i < mFilterAdapter.getmObjects().size(); i++) {
                ((FilterVo) mFilterAdapter.getmObjects().get(i)).setChecked(false);
            }
            ((FilterVo) mFilterAdapter.getmObjects().get(position)).setChecked(true);
        });
    }

    @Override
    public void initImmersionBar() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3001 && resultCode == RESULT_OK) {
            WorldNumList.WorldEnty worldEnty = (WorldNumList.WorldEnty) data.getSerializableExtra("datasource");
            ((NitCommonListFragment) fragment).onReFresh(null);
        }
    }
}
