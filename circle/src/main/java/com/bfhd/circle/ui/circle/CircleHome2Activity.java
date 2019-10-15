package com.bfhd.circle.ui.circle;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bfhd.circle.R;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.databinding.CircleActivityHome2Binding;
import com.bfhd.circle.vm.CircleViewModel;
import com.bfhd.circle.vo.CircleTitlesVo;
import com.bfhd.circle.vo.bean.StaCirParam;
import com.docker.common.common.router.AppRouter;

import java.util.List;

import javax.inject.Inject;

/*
 * 未申请我的和太极家
 * */
//@Route(path = AppRouter.CIRCLEHOME2)
public class CircleHome2Activity extends HivsBaseActivity<CircleViewModel, CircleActivityHome2Binding> {

    @Inject
    ViewModelProvider.Factory factory;

    public static void startMe(Context context, StaCirParam startCircleBean) {
        Intent intent = new Intent(context, CircleDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("mStartParam", startCircleBean);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.circle_activity_home2;
    }

    @Override
    public CircleViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleViewModel.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding.setViewmodel(mViewModel);
        mToolbar.setTitle("我的和太极家");

    }

    public void initView() {
        mBinding.tvApplyHome.setOnClickListener(view -> {
            CircleCreateHwjActivity.startMe(CircleHome2Activity.this, 1, null, null);
        });
    }


    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 103:
                mBinding.refresh.finishRefresh();
                mBinding.refresh.finishLoadMore();
                if (viewEventResouce.data != null && ((List<CircleTitlesVo>) viewEventResouce.data).size() > 0) {

                } else {
                    // 没有一级栏目的数据 那就写死吧
                }
                break;
            case 113:

                break;

            case 211:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
