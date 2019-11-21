package com.docker.cirlev2.ui.publish.select;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2ActivityGroupSelectBinding;
import com.docker.cirlev2.util.CircleGroupSelectAdapter;
import com.docker.cirlev2.vm.PublishViewModel;
import com.docker.cirlev2.vo.entity.CircleTitlesVo;
import com.docker.cirlev2.vo.param.StaCirParam;
import com.docker.common.common.command.NitContainerCommand;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/*
 * 选择分享圈子 -- 子栏目
 * */
public class CircleShareGroupSelectActivity extends NitCommonActivity<PublishViewModel, Circlev2ActivityGroupSelectBinding> {

    private CircleGroupSelectAdapter mAdapter;
    private StaCirParam staCirParam;
    @Inject
    ViewModelProvider.Factory factory;

    public static void startMe(Context context, StaCirParam staCirParam) {
        Intent intent = new Intent(context, CircleShareGroupSelectActivity.class);
        intent.putExtra("StaCirParam", staCirParam);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.circlev2_activity_group_select;
    }

    @Override
    public PublishViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(PublishViewModel.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        staCirParam = (StaCirParam) getIntent().getSerializableExtra("StaCirParam");
        super.onCreate(savedInstanceState);
        mBinding.recycle.setLayoutManager(new LinearLayoutManager(this));
        mBinding.empty.setOnretryListener(() -> {
            mViewModel.FetchCircleClass(staCirParam.getCircleid(), staCirParam.getUtid());
        });
        mViewModel.mCircleClassLv.observe(this, circleTitlesVos -> {
            if ((Collection) circleTitlesVos != null) {
                mAdapter.getmObjects().addAll(circleTitlesVos);
                if (mAdapter.getmObjects().size() == 0) {
                    mBinding.empty.showNodata();
                    staCirParam.getExtenMap().remove("classid1");
                    staCirParam.getExtenMap().remove("classname1");
                    staCirParam.getExtenMap().remove("classid2");
                    staCirParam.getExtenMap().remove("classname2");
                    RxBus.getDefault().post(new RxEvent<>("GroupSelect", staCirParam));
                    finish();
                    return;
                } else {
                    CircleTitlesVo checkvo = null;  // 数据回显
                    if (!TextUtils.isEmpty(staCirParam.getExtenMap().get("classid1"))) {
                        for (int i = 0; i < mAdapter.getmObjects().size(); i++) {
                            CircleTitlesVo titleVo = mAdapter.getmObjects().get(i);
                            if (titleVo.getClassid().equals(staCirParam.getExtenMap().get("classid1"))) {
                                checkvo = titleVo;
                                titleVo.setClicked(true);
                            }
                        }
                    }
                    if (!TextUtils.isEmpty(staCirParam.getExtenMap().get("classid2")) && checkvo != null && checkvo.getChildClass() != null) {
                        for (int i = 0; i < checkvo.getChildClass().size(); i++) {
                            CircleTitlesVo childBean = checkvo.getChildClass().get(i);
                            if (childBean.getClassid().equals(staCirParam.getExtenMap().get("classid2"))) {
                                childBean.setClicked(true);
                            }
                        }
                    }
                }
                mBinding.empty.hide();
                mAdapter.notifyDataSetChanged();
            } else {
                mBinding.empty.showError();
            }
        });
    }


    @Override
    public void initView() {
        mToolbar.setTitle("选择分类");
        mToolbar.setTvRight("确定", v -> {
            checkSelect();
            if (staCirParam.getExtenMap().size() > 0) {
                RxBus.getDefault().post(new RxEvent<>("GroupSelect", staCirParam));
                finish();
            } else {
                ToastUtils.showShort("请选择分类");
            }
        });
        mAdapter = new CircleGroupSelectAdapter();
        mBinding.recycle.setAdapter(mAdapter);
        mViewModel.FetchCircleClass(staCirParam.getCircleid(), staCirParam.getUtid());
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

    private void checkSelect() {
        staCirParam.getExtenMap().remove("classid1");
        staCirParam.getExtenMap().remove("classname1");
        staCirParam.getExtenMap().remove("classid2");
        staCirParam.getExtenMap().remove("classname2");
        List<CircleTitlesVo> titleListVos = mAdapter.getmObjects();
        CircleTitlesVo checktitle = null;
        for (CircleTitlesVo titleVo : titleListVos) {
            if (titleVo.isClicked()) {
                checktitle = titleVo;
                staCirParam.getExtenMap().put("classid1", titleVo.getClassid());
                staCirParam.getExtenMap().put("classname1", titleVo.getName());
            }
        }
        if (checktitle != null && checktitle.getChildClass() != null) {
            for (CircleTitlesVo bean : checktitle.getChildClass()) {
                if (bean.isClicked()) {
                    staCirParam.getExtenMap().put("classid2", bean.getClassid());
                    staCirParam.getExtenMap().put("classname2", bean.getName());
                }
            }
        }
    }
}
