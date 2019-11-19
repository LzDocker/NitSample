package com.bfhd.rxdemo;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.bfhd.evaluate.R;
import com.bfhd.evaluate.databinding.ActivityRxdemoBinding;
import com.bfhd.evaluate.vm.EnStudyRxViewModel;
import com.bfhd.evaluate.vo.RadioLessonVo;
import com.bfhd.evaluate.vo.RadioMenuVo;
import com.docker.common.common.command.NitContainerCommand;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;

import java.util.List;

import javax.inject.Inject;

///评测------新概念英语课程列表
@Route(path = AppRouter.EVALUATE_RXJAVA)
public class RxDemoActivity extends NitCommonActivity<EnStudyRxViewModel, ActivityRxdemoBinding> {

    @Inject
    ViewModelProvider.Factory factory;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rxdemo;
    }

    @Override
    public EnStudyRxViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(EnStudyRxViewModel.class);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding.rxdemoConcat.setOnClickListener(v -> {
            mViewModel.rxConcatMap("44", "37c0fc402ba039c78b07065bfd4d114c");

        });
        mBinding.rxdemoMerge.setOnClickListener(v -> {
            mViewModel.rxMerge("44", "37c0fc402ba039c78b07065bfd4d114c");
        });

    }

    @Override
    public void initView() {

    }

    @Override
    public void initRouter() {
        ARouter.getInstance().inject(this);
    }


    @Override
    public void initObserver() {
        mViewModel.mContainerLiveData.observe(this, object -> {
            if (object instanceof RadioLessonVo) {
                mBinding.rxdemoData.setText("\n\n\n" + JSON.toJSONString((RadioLessonVo) object));
            } else {
                List list = (List) object;
                if (list.get(0) instanceof RadioMenuVo) {
                    mBinding.rxdemoData.setText(mBinding.rxdemoData.getText() + "\n\n\n" + JSON.toJSONString(object));
                } else {
                    mBinding.rxdemoData.setText(mBinding.rxdemoData.getText() + "\n\n\n" + JSON.toJSONString(object));
                }
            }

        });
    }


    @Override
    public NitContainerCommand providerNitContainerCommand(int flag) {
        return (NitContainerCommand) () -> (EnStudyRxViewModel.class);
    }


}
