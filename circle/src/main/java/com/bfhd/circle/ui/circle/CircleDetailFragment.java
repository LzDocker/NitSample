package com.bfhd.circle.ui.circle;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bfhd.circle.R;
import com.bfhd.circle.base.CommonFragment;
import com.bfhd.circle.databinding.CircleFragmentCircleDetailtBinding;
import com.bfhd.circle.vm.CircleViewModel;
import com.bfhd.circle.vo.CircleTitlesVo;
import com.bfhd.circle.vo.bean.StaCirParam;
import com.bfhd.circle.widget.TabLabelTextLayout;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.widget.refresh.SmartRefreshLayout;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

/*
 *
 * */
public class CircleDetailFragment extends CommonFragment<CircleViewModel, CircleFragmentCircleDetailtBinding> {


    @Inject
    ViewModelProvider.Factory factory;
    private List<CircleTitlesVo> circleTitlesVos;
    private List<CircleTitlesVo> childBeans;
    private List<Fragment> childfragments;
    private CommonpagerAdapter commonpagerAdapter;
    private String[] childtitles;
    private int mParentpos = 0;
    private SmartRefreshLayout smartRefreshLayout;
    private StaCirParam mStartParam;
    private boolean isEdit = false; // 是否添加编辑分类

    private boolean isAddTotalTab = false;

    @Override
    protected int getLayoutId() {
        return R.layout.circle_fragment_circle_detailt;
    }


    public static CircleDetailFragment newInstance(List<CircleTitlesVo> circleTitlesVos, int position, StaCirParam mStartParam) {
        CircleDetailFragment detailFragment = new CircleDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("param", (Serializable) circleTitlesVos);
        bundle.putSerializable("mStartParam", (Serializable) mStartParam);
        bundle.putInt("position", position);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    protected CircleViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleViewModel.class);
    }

    @Override
    protected void initView(View var1) {
        circleTitlesVos = (List<CircleTitlesVo>) getArguments().getSerializable("param");
        mStartParam = (StaCirParam) getArguments().getSerializable("mStartParam");
        mParentpos = getArguments().getInt("position");
        childBeans = circleTitlesVos.get(mParentpos).getChildClass();
        if ("动态".equals(circleTitlesVos.get(mParentpos).getName())) {
            isAddTotalTab = true;
        } else {
            isAddTotalTab = false;
        }
        if ("1".equals(circleTitlesVos.get(mParentpos).getIsedit()) && mStartParam.role > 0) {
            isEdit = true;
        } else {
            isEdit = false;
        }

        initChildTab(childBeans);//childBeans: QQ 133
    }

    private void initChildTab(List<CircleTitlesVo> childBeans) {

        if (isAddTotalTab) {
            if (childBeans == null || childBeans.size() == 0) {
                mBinding.get().tabCircleChildTitle.setVisibility(View.VISIBLE);
                childtitles = new String[]{"全部"};
                childfragments = new ArrayList<>();
                childfragments.add(CircleInfoListFragment.newInstance(circleTitlesVos.get(mParentpos), -1, mStartParam));
                commonpagerAdapter = new CommonpagerAdapter(getChildFragmentManager(), childfragments);
                mBinding.get().viewPager.setAdapter(commonpagerAdapter);
                mBinding.get().tabCircleChildTitle.setViewPager(mBinding.get().viewPager, childtitles);
                mBinding.get().viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int i, float v, int i1) {
                    }

                    @Override
                    public void onPageSelected(int i) {
                        if (CircleDetailFragment.this.smartRefreshLayout != null) {
                            smartRefreshLayout.finishRefresh();
                            smartRefreshLayout.finishLoadMore();
                        }
                    }

                    @Override
                    public void onPageScrollStateChanged(int i) {
                    }
                });

            } else {
                mBinding.get().tabCircleChildTitle.setVisibility(View.VISIBLE);
                childtitles = new String[childBeans.size() + 1];
                childtitles[0] = "全部";
                childfragments = new ArrayList<>();
                childfragments.add(CircleInfoListFragment.newInstance(circleTitlesVos.get(mParentpos), -1, mStartParam));
                for (int i = 0; i < childBeans.size(); i++) {
                    childfragments.add(CircleInfoListFragment.newInstance(circleTitlesVos.get(mParentpos), i, mStartParam));
                    childtitles[i + 1] = childBeans.get(i).getName();
                }
                commonpagerAdapter = new CommonpagerAdapter(getChildFragmentManager(), childfragments);
                mBinding.get().viewPager.setAdapter(commonpagerAdapter);
                mBinding.get().tabCircleChildTitle.setViewPager(mBinding.get().viewPager, childtitles);
                mBinding.get().viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int i, float v, int i1) {
                    }

                    @Override
                    public void onPageSelected(int i) {
//                    mChildTabCurrent = i;
                        if (CircleDetailFragment.this.smartRefreshLayout != null) {
                            smartRefreshLayout.finishRefresh();
                            smartRefreshLayout.finishLoadMore();
                        }
                    }

                    @Override
                    public void onPageScrollStateChanged(int i) {
                    }
                });
            }
        } else {
            if (childBeans == null || childBeans.size() == 0) {
                childfragments = new ArrayList<>();
                childfragments.add(CircleInfoListFragment.newInstance(circleTitlesVos.get(mParentpos), -1, mStartParam));
                commonpagerAdapter = new CommonpagerAdapter(getChildFragmentManager(), childfragments);
                mBinding.get().viewPager.setAdapter(commonpagerAdapter);
//                mBinding.get().tabCircleChildTitle.setVisibility(View.GONE);
            } else {
                mBinding.get().tabCircleChildTitle.setVisibility(View.VISIBLE);
                childtitles = new String[childBeans.size()];
                childfragments = new ArrayList<>();
                for (int i = 0; i < childBeans.size(); i++) {
                    childfragments.add(CircleInfoListFragment.newInstance(circleTitlesVos.get(mParentpos), i, mStartParam));
                    childtitles[i] = childBeans.get(i).getName();
                }
                commonpagerAdapter = new CommonpagerAdapter(getChildFragmentManager(), childfragments);
                mBinding.get().viewPager.setAdapter(commonpagerAdapter);
                mBinding.get().tabCircleChildTitle.setViewPager(mBinding.get().viewPager, childtitles);
                mBinding.get().viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int i, float v, int i1) {
                    }

                    @Override
                    public void onPageSelected(int i) {
                        if (CircleDetailFragment.this.smartRefreshLayout != null) {
                            smartRefreshLayout.finishRefresh();
                            smartRefreshLayout.finishLoadMore();
                        }
                    }

                    @Override
                    public void onPageScrollStateChanged(int i) {
                    }
                });
            }

        }

        if (isEdit) {
            mBinding.get().tabCircleChildTitle.setVisibility(View.VISIBLE);
            TabLabelTextLayout tabLabel = new TabLabelTextLayout(this.getHoldingActivity());
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT);
            layoutParams.gravity = Gravity.CENTER_VERTICAL;
            tabLabel.setOnClickListener(v -> {
                onEditClick();
            });
            ((ViewGroup) mBinding.get().tabCircleChildTitle.getChildAt(0)).addView(tabLabel, layoutParams);
        }
        mBinding.get().viewPager.setOffscreenPageLimit(childfragments.size());
        mBinding.get().viewPager.setCurrentItem(0);
    }

    @Override
    public void initImmersionBar() {
    }

    /*
     * 编辑
     * */
    private void onEditClick() {
        StaCirParam staCirParam = new StaCirParam(circleTitlesVos.get(0).getCircleid(), circleTitlesVos.get(0).getUtid(), mParentpos + "");
        staCirParam.type = 2;
        CircleEditClassActivity.startMe(this.getHoldingActivity(), staCirParam, CircleEditClassActivity.LEVEL_2_EDITCODE);
    }
// todo  // 刷新时有问题，改为重新打开 后期在优化
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case CircleEditClassActivity.LEVEL_2_EDITCODE:
//                if (resultCode == RESULT_OK) {
//                    mViewModel.initCircleParam(((CircleDetailActivity)(CircleDetailFragment.this.getHoldingActivity())).mStartParam);
//                    mViewModel.getCircleClass();
//                }
//                break;
//        }
//
//    }

//    @Override
//    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
//        super.OnVmEnentListner(viewEventResouce);
//        switch (viewEventResouce.eventType) {
//            case 103: // 获取分类数据成功
//
////                mBinding.get().viewPager.removeAllViews();
////                childfragments.clear();
////                commonpagerAdapter.notifyDataSetChanged();
////                mBinding.get().tabCircleChildTitle.notifyDataSetChanged();
//                FragmentUtils.removeAll(getChildFragmentManager());
//
//                circleTitlesVos = (List<CircleTitlesVo>) viewEventResouce.data;
//                childBeans = ((List<CircleTitlesVo>) viewEventResouce.data).get(mParentpos).getChildClass();
//                initChildTab(childBeans);
//                break;
//        }
//    }

    /*
     *
     * 加载更多的时候调用
     * */
    @Override
    public void OnLoadMore(SmartRefreshLayout smartRefreshLayout) {
        super.OnLoadMore(smartRefreshLayout);
        this.smartRefreshLayout = smartRefreshLayout;
        ((CircleInfoListFragment) childfragments.get(mBinding.get().viewPager.getCurrentItem())).OnLoadMore(smartRefreshLayout);
    }

    @Override
    public void OnRefresh(SmartRefreshLayout smartRefreshLayout) {
        this.smartRefreshLayout = smartRefreshLayout;
        ((CircleInfoListFragment) childfragments.get(mBinding.get().viewPager.getCurrentItem())).OnRefresh(smartRefreshLayout);
    }


    public int getCurrenTab() {
        if (childtitles != null && childtitles.length > 0 && Arrays.asList(childtitles).contains("全部")) {
            if (mBinding.get().viewPager.getCurrentItem() == 0) {
                return mBinding.get().viewPager.getCurrentItem();
            } else {
                return mBinding.get().viewPager.getCurrentItem() - 1;
            }
        } else {
            return mBinding.get().viewPager.getCurrentItem();
        }
    }

    public int getFrameCount() {
        return mBinding.get().tabCircleChildTitle.getTabCount();
    }
}
