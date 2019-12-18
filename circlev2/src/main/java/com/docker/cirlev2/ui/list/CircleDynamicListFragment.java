package com.docker.cirlev2.ui.list;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.cirlev2.vm.CircleDynamicListViewModel;
import com.docker.cirlev2.vo.entity.CircleTitlesVo;
import com.docker.cirlev2.vo.param.StaCirParam;
import com.docker.common.common.config.CommonConstant;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonListFragment;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vo.UserInfoVo;

import static com.docker.common.common.config.Constant.KEY_RVUI_GRID2;

@Route(path = AppRouter.CIRCLE_DYNAMIC_LIST_FRAME)
public class CircleDynamicListFragment extends NitCommonListFragment<CircleDynamicListViewModel> {

    CommonListOptions commonListReq = new CommonListOptions();

    public CircleTitlesVo mCircleTitlesVo;
    public int mChildPos = 0;


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

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public CommonListOptions getArgument() {


//        commonListReq.ReqParam.put("uuid", "3c29a4eed44db285468df3443790e64a");
//        commonListReq.ReqParam.put("uuid", "8621e837a2a1579710a95143e5862424");
//        commonListReq.ReqParam.put("memberid", "3");
//        commonListReq.ReqParam.put("memberid", "64");
//        commonListReq.ReqParam.put("companyid", "1");

//        commonListReq.refreshState = Constant.KEY_REFRESH_ONLY_LOADMORE;
//        mCircleTitlesVo = (CircleTitlesVo) getArguments().getSerializable("param");
//        mChildPos = getArguments().getInt("childPosition");
//        UserInfoVo userInfoVo = CacheUtils.getUser();
//        commonListReq.ReqParam.put("memberid", userInfoVo.uid);
//        commonListReq.ReqParam.put("uuid", userInfoVo.uuid);
//        commonListReq.ReqParam.put("utid", mCircleTitlesVo.getUtid());
//        commonListReq.ReqParam.put("circleid", mCircleTitlesVo.getCircleid());
//        commonListReq.ReqParam.put("classid", mCircleTitlesVo.getClassid());
//        if (mChildPos != -1) {
//            commonListReq.ReqParam.put("circleid2", mCircleTitlesVo.getChildClass().get(mChildPos).getCircleid());
//            commonListReq.ReqParam.put("t", mCircleTitlesVo.getChildClass().get(mChildPos).getDataType());
//        } else {
//            commonListReq.ReqParam.put("t", mCircleTitlesVo.getDataType());
//        }
//
//        if ("goods".equals(commonListReq.ReqParam.get("t"))) {
//            commonListReq.RvUi = KEY_RVUI_GRID2;
//        }
//
//        if (TextUtils.isEmpty(commonListReq.ReqParam.get("t"))) {
//            commonListReq.ReqParam.remove("t");
//        }

        commonListReq.refreshState = Constant.KEY_REFRESH_ONLY_LOADMORE;
//        commonListReq.ReqParam.put("t", "dynamic");
        commonListReq.ReqParam.put("uuid", "420cd8fd09e4ae6cfb8f3b3fdf5b7af4");
        commonListReq.ReqParam.put("memberid", "67");
        commonListReq.ReqParam.put("companyid", "1");


        return commonListReq;
    }

    @Override
    public void initImmersionBar() {

    }

    @Override
    public void onVisible() {
        super.onVisible();

    }
}
