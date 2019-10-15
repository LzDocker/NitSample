package com.bfhd.circle.ui.circle;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bfhd.circle.R;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.databinding.CircleActivityCirclePersionPerssionBinding;
import com.bfhd.circle.vm.CircleViewModel;
import com.bfhd.circle.vo.PersionPerssionVo;
import com.bfhd.circle.vo.bean.StaCirParam;
import javax.inject.Inject;

/*
 * 圈子成员权限设置
 * */
public class CirclePersonPerssionActivity extends HivsBaseActivity<CircleViewModel, CircleActivityCirclePersionPerssionBinding> {

    @Inject
    ViewModelProvider.Factory factory;
    //    private int type;
    private StaCirParam mStartParam;

    private PersionPerssionVo mvo;

    public static void startMe(Context context, StaCirParam startCircleBean, int code, PersionPerssionVo persionPerssionVo) {

        Intent intent = new Intent(context, CirclePersonPerssionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("mStartParam", startCircleBean);
        bundle.putSerializable("PersionPerssionVo", persionPerssionVo);
        intent.putExtras(bundle);
        ((Activity) context).startActivityForResult(intent, code);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.circle_activity_circle_persion_perssion;
    }

    @Override
    public CircleViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleViewModel.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mStartParam = (StaCirParam) getIntent().getSerializableExtra("mStartParam");
        mvo = (PersionPerssionVo) getIntent().getSerializableExtra("PersionPerssionVo");
        super.onCreate(savedInstanceState);
        mBinding.setVo(mvo);
    }

    @Override
    public void initView() {
        mToolbar.setTitle("设置权限");
        mToolbar.setTvRight("完成",v -> {
           Intent intent = new Intent();
           intent.putExtra("perssion",mBinding.getVo());
           setResult(RESULT_OK,intent);
           finish();
        });
    }

}
