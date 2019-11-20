package com.docker.cirlev2.ui.persion;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2ActivityCirclePersionPerssionBinding;
import com.docker.cirlev2.vm.CirclePersionViewModel;
import com.docker.cirlev2.vo.entity.PersionPerssionVo;
import com.docker.cirlev2.vo.param.StaCirParam;
import com.docker.common.common.command.NitContainerCommand;
import com.docker.common.common.ui.base.NitCommonActivity;

/*
 * 圈子成员权限设置
 * */
public class CirclePersonPerssionActivity extends NitCommonActivity<CirclePersionViewModel, Circlev2ActivityCirclePersionPerssionBinding> {

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
        return R.layout.circlev2_activity_circle_persion_perssion;
    }

    @Override
    public CirclePersionViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CirclePersionViewModel.class);
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
        mToolbar.setTvRight("完成", v -> {
            Intent intent = new Intent();
            intent.putExtra("perssion", mBinding.getVo());
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    @Override
    public void initObserver() {

    }

    @Override
    public void initRouter() {

    }

    @Override
    public NitContainerCommand providerNitContainerCommand(int flag) {
        return null;
    }

}
