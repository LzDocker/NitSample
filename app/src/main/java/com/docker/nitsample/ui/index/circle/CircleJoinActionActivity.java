package com.docker.nitsample.ui.index.circle;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.nitsample.R;
import com.docker.nitsample.databinding.ActivityJoinActionBinding;
import com.docker.nitsample.vm.circle.CircleJoinListVm;

import java.util.HashMap;

@Route(path = AppRouter.HOME_JOIN_ACTION)
public class CircleJoinActionActivity extends NitCommonActivity<CircleJoinListVm, ActivityJoinActionBinding> {


    @Autowired
    public String circleid;

    @Autowired
    public String utid;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_join_action;
    }

    @Override
    public CircleJoinListVm getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleJoinListVm.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void initView() {
        mToolbar.hide();

        mBinding.ivBack.setOnClickListener(v -> {
            finish();
        });
        mBinding.tvSend.setOnClickListener(v -> {

            // 发送

            if (TextUtils.isEmpty(mBinding.edContent.getText().toString().trim())) {
                ToastUtils.showShort("请输入申请理由");
            } else {

                HashMap<String, String> param = new HashMap<>();
                param.put("circleid",getIntent().getStringExtra("circleid"));
                param.put("utid", getIntent().getStringExtra("utid"));
                param.put("memberid", CacheUtils.getUser().uid);
                param.put("uuid", CacheUtils.getUser().uuid);
                param.put("msg", mBinding.edContent.getText().toString().trim());
                mViewModel.sendCheckMesg(param);
            }

        });

    }

    @Override
    public void initObserver() {

        mViewModel.mCheckLv.observe(this, s -> {
            ToastUtils.showShort("申请成功");
            finish();
        });
    }

    @Override
    public void initRouter() {

    }

}

