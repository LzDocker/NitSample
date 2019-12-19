package com.docker.cirlev2.ui.list;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.CollectionUtils;
import com.docker.cirlev2.vm.CircleDynamicListViewModel;
import com.docker.cirlev2.vo.entity.CircleTitlesVo;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.ui.base.NitCommonListFragment;
import com.docker.common.common.widget.indector.CommonIndector;
import com.docker.common.common.widget.refresh.SmartRefreshLayout;
import com.docker.common.databinding.CommonTabFrameLayoutBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Route(path = AppRouter.CIRCLE_DYNAMIC_LIST_FRAME_COUTAINER)
public class CircleDynamicCoutainerFragment extends NitCommonFragment<CircleDynamicListViewModel, CommonTabFrameLayoutBinding> {


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
        return com.docker.common.R.layout.common_tab_frame_layout;
    }

    @Override
    public CircleDynamicListViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleDynamicListViewModel.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceStates) {
        super.onCreate(savedInstanceStates);
        ARouter.getInstance().inject(this);
    }

    @Override
    protected void initView(View var1) {
        mCircleTitlesVo = (List<CircleTitlesVo>) getArguments().getSerializable("tabVo");

        // 动态默认添加 "全部" tab
//        isAddTotalTab = "动态".equals(circleTitlesVo.get(pos).getName());
        // 动态可以编辑栏目
//        mBinding.get().commonTvEdit.setVisibility(("1".equals(circleTitlesVo.get(pos).getIsedit()) && role > 0) ? View.VISIBLE : View.GONE);

        processTab(mCircleTitlesVo.get(pos).getChildClass());

        /*
         * 编辑 todo
         * */
        mBinding.get().commonTvEdit.setOnClickListener(v -> {

        });
    }

    /*
     * 获取当前child 位置
     * */
    public int getCurrenTab() {
        if (mCircleTitlesVo.get(pos).getChildClass() != null && titles.
                length > 0 && Arrays.asList(titles).contains("全部")) {
            if (mBinding.get().viewpager.getCurrentItem() == 0) {
                return mBinding.get().viewpager.getCurrentItem();
            } else {
                return mBinding.get().viewpager.getCurrentItem() - 1;
            }
        } else {
            return mBinding.get().viewpager.getCurrentItem();
        }
    }

    private void processTab(List<CircleTitlesVo> circleTitlesVos) {
        if (isAddTotalTab) {  // 添加 全部
            mBinding.get().magicIndicator.setVisibility(View.VISIBLE);
            if (CollectionUtils.isEmpty(circleTitlesVos)) {
                titles = new String[]{"全部", "全部", "全部"};
                fragments.add((Fragment) ARouter.getInstance()
                        .build(AppRouter.CIRCLE_DYNAMIC_LIST_FRAME)
                        .navigation());
                fragments.add((Fragment) ARouter.getInstance()
                        .build(AppRouter.CIRCLE_DYNAMIC_LIST_FRAME)
                        .navigation());
                fragments.add((Fragment) ARouter.getInstance()
                        .build(AppRouter.CIRCLE_DYNAMIC_LIST_FRAME)
                        .navigation());
            } else {
                titles = new String[circleTitlesVos.size() + 1];
                titles[0] = "全部";
                for (int i = 0; i < circleTitlesVos.size(); i++) {
                    titles[i + 1] = circleTitlesVos.get(i).getName();
                    fragments.add((Fragment) ARouter.getInstance()
                            .build(AppRouter.CIRCLE_DYNAMIC_LIST_FRAME)
                            .navigation());
                }
            }
        } else {
            if (CollectionUtils.isEmpty(circleTitlesVos)) {
                mBinding.get().magicIndicator.setVisibility(View.GONE);
                titles = new String[]{""};
                fragments.add((Fragment) ARouter.getInstance()
                        .build(AppRouter.CIRCLE_DYNAMIC_LIST_FRAME)
                        .withSerializable("param", mCircleTitlesVo.get(pos))
                        .withInt("childPosition", -1)
                        .navigation());
            } else {
                mBinding.get().magicIndicator.setVisibility(View.VISIBLE);
                titles = new String[circleTitlesVos.size()];
                for (int i = 0; i < circleTitlesVos.size(); i++) {
                    titles[i] = circleTitlesVos.get(i).getName();
                    fragments.add((Fragment) ARouter.getInstance()
                            .build(AppRouter.CIRCLE_DYNAMIC_LIST_FRAME)
                            .withSerializable("param", mCircleTitlesVo.get(pos))
                            .withInt("childPosition", i)
                            .navigation());
                }
            }
        }
        // magic
        mBinding.get().viewpager.setAdapter(new CommonpagerAdapter(getChildFragmentManager(), fragments, titles));
        CommonIndector commonIndector = new CommonIndector();
        commonIndector.initMagicIndicatorScroll(titles, mBinding.get().viewpager, mBinding.get().magicIndicator, this.getHoldingActivity());
        // magic
    }

    @Override
    public void onReFresh(SmartRefreshLayout refreshLayout) {
        super.onReFresh(refreshLayout);
        if (CollectionUtils.isEmpty(fragments)) {
            refreshLayout.finishRefresh();
            return;
        }
        ((NitCommonFragment) (fragments.get(mBinding.get().viewpager.getCurrentItem()))).onReFresh(refreshLayout);
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
