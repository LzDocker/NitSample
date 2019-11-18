package com.bfhd.evaluate.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.evaluate.vm.EnStudyViewModel;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonListFragment;

/**
 * 新概念课程列表
 * {@link com.bfhd.evaluate.vo.RadioLessonVo}
 */
@Route(path = AppRouter.EVALUATE_LESSON_LIST)
public class AudioLessonFragment extends NitCommonListFragment<EnStudyViewModel> {

    CommonListOptions commonListReq = new CommonListOptions();

    public static AudioLessonFragment newInstance() {
        return new AudioLessonFragment();
    }

    @Autowired
    public String id;

    @Override
    public EnStudyViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(EnStudyViewModel.class);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceStates) {
        super.onCreate(savedInstanceStates);
        ARouter.getInstance().inject(this);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        mBinding.get()
//                .recyclerView
//                .setLayoutManager(new LinearLayoutManager(getHoldingActivity()));
    }

    @Override
    protected void initView(View var1) {

    }

    @Override
    public CommonListOptions getArgument() {
        commonListReq.refreshState = 0;
        commonListReq.RvUi = 0;
        commonListReq.ReqParam.put("lession_dubbing_id", id);
        commonListReq.ReqParam.put("uuid", "8621e837a2a1579710a95143e5862424");
        commonListReq.ReqParam.put("memberid", "64");
        return commonListReq;
    }

    @Override
    public void initImmersionBar() {
    }
}
