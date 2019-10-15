package com.bfhd.circle.ui.circle;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;

import com.bfhd.circle.BR;
import com.bfhd.circle.R;
import com.bfhd.circle.base.CommonFragment;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.base.adapter.HivsSampleAdapter;
import com.bfhd.circle.databinding.CircleFragmentCircleListBinding;
import com.bfhd.circle.vm.CircleListViewModel;
import com.bfhd.circle.vo.CircleListVo;
import com.bfhd.circle.vo.bean.StaCirParam;
import com.bfhd.circle.vo.bean.StaCircleListParam;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.widget.refresh.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/*圈子列表   附近的 热门  // 通用圈子列表
 *todo 待重构 gg
 * */
public class CircleListFragment extends CommonFragment<CircleListViewModel, CircleFragmentCircleListBinding> {


    private SmartRefreshLayout smartRefreshLayout;
    @Inject
    ViewModelProvider.Factory factory;
    private HivsSampleAdapter adapter;
    private StaCircleListParam mStaParam;

    @Override
    protected int getLayoutId() {
        return R.layout.circle_fragment_circle_list;
    }

    public static CircleListFragment newInstance(StaCircleListParam staCircleListParam) {
        CircleListFragment circleListFragment = new CircleListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("mStaParam", staCircleListParam);
        circleListFragment.setArguments(bundle);
        return circleListFragment;
    }

    @Override
    protected CircleListViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleListViewModel.class);
    }

    @Override
    protected void initView(View var1) {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.get().setViewmodel(mViewModel);
        adapter = new HivsSampleAdapter(R.layout.circle_item_circle_frame_list, BR.item);
        adapter.setOnchildViewClickListener(new int[]{R.id.rl_coutainer, R.id.tv_enter}, (childview, position) -> {
            int i = childview.getId();
            if (i == R.id.rl_coutainer) {
                CircleListVo circleListVo = (CircleListVo) adapter.getmObjects().get(position);
                CircleDetailActivity.startMe(CircleListFragment.this.getHoldingActivity(), new StaCirParam(circleListVo.circleid, circleListVo.getUtid(), ""));
            }
            if (i == R.id.tv_enter) { // 加入
                CircleListVo vo = (CircleListVo) adapter.getmObjects().get(position);
                mViewModel.joinCircle(vo.circleid, vo.getUtid(), position);
//                if ("1".equals(vo.is_allow_join)) {
//
//                } else {
//                    // todo 申请加入圈子
//                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        mStaParam = (StaCircleListParam) getArguments().getSerializable("mStaParam");
        super.onActivityCreated(savedInstanceState);
        mViewModel.setStaParam(mStaParam);

        if (mStaParam.Uity == 0) { // 内部自己控制
            mBinding.get().emptyNone.setVisibility(View.GONE);
            mBinding.get().recyclerView.setAdapter(adapter);
            mBinding.get().recyclerView.setNestedScrollingEnabled(true);
        } else {
            mBinding.get().refresh.setVisibility(View.GONE);
            mBinding.get().recyclerViewNone.setAdapter(adapter);
            mBinding.get().recyclerViewNone.setNestedScrollingEnabled(true);
        }
    }

    @Override
    public void initImmersionBar() {

    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 103:
                if (viewEventResouce.data != null) {
                    if (mViewModel.mPage == 1) {
                        adapter.getmObjects().clear();
                        adapter.getmObjects().addAll((List<CircleListVo>) viewEventResouce.data);
                        adapter.notifyDataSetChanged();
                    } else {
                        adapter.getmObjects().addAll((List<CircleListVo>) viewEventResouce.data);
                        adapter.notifyItemRangeChanged(adapter.getmObjects().size() - ((List<CircleListVo>) viewEventResouce.data).size(), adapter.getmObjects().size());
                    }
                }
                if (adapter.getmObjects().size() == 0 && mViewModel.mPage == 1) {
                    if (mStaParam.Uity == 1) {
                        mBinding.get().emptyNone.showNodata();
                    }
                } else {
                    if (mStaParam.Uity == 1) {
                        mBinding.get().emptyNone.hide();
                    }
                }
                if (smartRefreshLayout != null) {
                    smartRefreshLayout.finishLoadMore();
                    smartRefreshLayout.finishRefresh();
                }
                break;
            case 104:
                if (mStaParam.Uity == 1) {
                    mBinding.get().emptyNone.showError();
                    mBinding.get().emptyNone.setOnretryListener(() -> {
                        mBinding.get().emptyNone.showLoading();
                        if (mStaParam.ReqType == 0) {
                            mViewModel.getData();
                        } else {
                            mViewModel.getCircleList(mStaParam.paramMap);
                        }
                    });
                }
                break;

            case 105: // 加入圈子成功
                if (viewEventResouce.data != null) {
                    ((CircleListVo) adapter.getmObjects().get((Integer) viewEventResouce.data)).setIsJoin("1");
                    adapter.notifyItemRangeChanged((Integer) viewEventResouce.data, 1);
                    RxBus.getDefault().post(new RxEvent<>("refresh_circle_myjoin", null)); // 刷新我的圈子
                }
                break;
        }
    }

    /*
     *
     * 加载更多的时候调用
     * */
    @Override
    public void OnLoadMore(SmartRefreshLayout smartRefreshLayout) {
        super.OnLoadMore(smartRefreshLayout);
        mViewModel.mIsfirstLoad = false;
        if (mStaParam.ReqType == 0) {
            mViewModel.getData();
        } else {
            mViewModel.getCircleList(mStaParam.paramMap);
        }
        this.smartRefreshLayout = smartRefreshLayout;
    }

    @Override
    public void OnRefresh(SmartRefreshLayout smartRefreshLayout) {
        super.OnRefresh(smartRefreshLayout);
        mViewModel.mIsfirstLoad = false;
        if (mViewModel != null) {
            mViewModel.mPage = 1;
            if (mStaParam.ReqType == 0) {
//                if (adapter != null) {
//                    adapter.clear();
//                }
                mViewModel.getData();
            } else {
//                if (adapter != null) {
//                    adapter.clear();
//                }
                mViewModel.getCircleList(mStaParam.paramMap);
            }
            this.smartRefreshLayout = smartRefreshLayout;
        }

    }

    @Override
    public void onVisible() {
        super.onVisible();
        if (adapter.getmObjects().size() == 0 && mViewModel.mPage == 1) {
            mViewModel.mIsfirstLoad = false;
            if (mStaParam.ReqType == 0) {
                mViewModel.getData();
            } else {
                mViewModel.getCircleList(mStaParam.paramMap);
            }
        }
    }

    public void UpdateReqParam(boolean isAll, ArrayList<Pair<String, String>> pairList, boolean isrefresh) {
        if (isAll) {
            mStaParam.paramMap.clear();
            for (int i = 0; i < pairList.size(); i++) {
                if (!TextUtils.isEmpty(pairList.get(i).first) && !TextUtils.isEmpty(pairList.get(i).second)) {
                    mStaParam.paramMap.put(pairList.get(i).first, pairList.get(i).second);
                }
            }
        } else {
            for (int i = 0; i < pairList.size(); i++) {
                if (!TextUtils.isEmpty(pairList.get(i).first) && !TextUtils.isEmpty(pairList.get(i).second)) {
                    mStaParam.paramMap.put(pairList.get(i).first, pairList.get(i).second);
                }
            }
        }
        mViewModel.mPage = 1;
        if (adapter != null) {
            adapter.clear();
        }
        mViewModel.mIsfirstLoad = false;
        if (isrefresh) {
            if (mStaParam.ReqType == 0) {
                mViewModel.getData();
            } else {
                mViewModel.getCircleList(mStaParam.paramMap);
            }
        }
    }


}
