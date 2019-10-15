package com.bfhd.circle.ui.circle;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bfhd.circle.R;
import com.bfhd.circle.base.CommonFragment;
import com.bfhd.circle.databinding.CircleFragmentCircleInfoListBinding;
import com.bfhd.circle.ui.safe.DynamicFragment;
import com.bfhd.circle.vm.CircleViewModel;
import com.bfhd.circle.vo.CircleTitlesVo;
import com.bfhd.circle.vo.StaDynaVo;
import com.bfhd.circle.vo.bean.StaCirParam;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.widget.refresh.SmartRefreshLayout;
import java.io.Serializable;

import javax.inject.Inject;

/*圈子详情列表 ---动态 问答
 *
 * */
public class CircleInfoListFragment extends CommonFragment<CircleViewModel, CircleFragmentCircleInfoListBinding> {


    @Inject
    ViewModelProvider.Factory factory;
    private StaCirParam mStartParam;
    public CircleTitlesVo mCircleTitlesVo;
    public int mChildPos = 0;
    private DynamicFragment dynamicFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.circle_fragment_circle_info_list;
    }

    public static CircleInfoListFragment newInstance(CircleTitlesVo circleTitlesVos, int childPosition, StaCirParam mStartParam) {

        CircleInfoListFragment circleInfoListFragment = new CircleInfoListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("param", (Serializable) circleTitlesVos);
        bundle.putSerializable("mStartParam", (Serializable) mStartParam);
        bundle.putInt("childPosition", childPosition);
        circleInfoListFragment.setArguments(bundle);
        return circleInfoListFragment;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        mCircleTitlesVo = (CircleTitlesVo) getArguments().getSerializable("param");
        mStartParam = (StaCirParam) getArguments().getSerializable("mStartParam");
        mChildPos = getArguments().getInt("childPosition");
        super.onActivityCreated(savedInstanceState);
        UserInfoVo userInfoVo = CacheUtils.getUser();
        StaDynaVo staDynaVo = new StaDynaVo();
        staDynaVo.UiType = 1;
        staDynaVo.ReqType = 1;
        staDynaVo.ReqParam.put("memberid", userInfoVo.uid);
        staDynaVo.ReqParam.put("uuid", userInfoVo.uuid);
        staDynaVo.ReqParam.put("utid", mStartParam.getUtid());
        staDynaVo.ReqParam.put("circleid", mStartParam.getCircleid());

//        staDynaVo.ReqParam.put("classid", mCircleTitlesVo.getClassid());
//        if (mChildPos != -1) {
//            staDynaVo.ReqParam.put("classid2", mCircleTitlesVo.getChildClass().get(mChildPos).getClassid());
//        }
        
        if ("dynamic".equals(mCircleTitlesVo.getDataType())) {
//            staDynaVo.ReqParam.put("classid", mCircleTitlesVo.getClassid());
            staDynaVo.ReqParam.put("t", mCircleTitlesVo.getDataType());
//            if (mChildPos != -1) {
//                staDynaVo.ReqParam.put("t", mCircleTitlesVo.getDataType());
////                staDynaVo.ReqParam.put("classid2", mCircleTitlesVo.getChildClass().get(mChildPos).getClassid());
//            }
        } else {
            staDynaVo.ReqParam.put("goodsui", "goods");
            if (mChildPos != -1) {
                staDynaVo.ReqParam.put("t", mCircleTitlesVo.getChildClass().get(mChildPos).getDataType());
            } else {
                staDynaVo.ReqParam.put("t", mCircleTitlesVo.getDataType());
            }
        }

        dynamicFragment = DynamicFragment.newInstance(staDynaVo, null);
        FragmentUtils.add(getChildFragmentManager(), dynamicFragment, R.id.frame_circle);
    }

    @Override
    protected CircleViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleViewModel.class);
    }

    @Override
    protected void initView(View var1) {
    }

    @Override
    public void initImmersionBar() {

    }

    /*
     *
     * 加载更多的时候调用
     * */
    @Override
    public void OnLoadMore(SmartRefreshLayout smartRefreshLayout) {
        super.OnLoadMore(smartRefreshLayout);
        dynamicFragment.OnLoadMore(smartRefreshLayout);
    }

    @Override
    public void OnRefresh(SmartRefreshLayout smartRefreshLayout) {
        super.OnRefresh(smartRefreshLayout);
        dynamicFragment.OnRefresh(smartRefreshLayout);
    }

}
