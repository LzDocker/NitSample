package com.bfhd.circle.ui.circle;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.bfhd.circle.base.HivsBaseViewModel;
import com.bfhd.circle.base.OpenBaseListActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.ui.adapter.CircleGroupSelectAdapter;
import com.bfhd.circle.vm.CircleViewModel;
import com.bfhd.circle.vo.CircleTitlesVo;
import com.bfhd.circle.vo.bean.StaCirParam;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/*
 * 选择分享圈子 -- 子栏目
 * */
public class CircleShareGroupSelectActivity extends OpenBaseListActivity<CircleViewModel> {

    private CircleGroupSelectAdapter mAdapter;
    //    private CircleListVo mCirclevo;
    private StaCirParam staCirParam;
    @Inject
    ViewModelProvider.Factory factory;

    public static void startMe(Context context, StaCirParam staCirParam) {
        Intent intent = new Intent(context, CircleShareGroupSelectActivity.class);
        intent.putExtra("StaCirParam", staCirParam);
        context.startActivity(intent);
    }

    @Override
    public CircleViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleViewModel.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        staCirParam = (StaCirParam) getIntent().getSerializableExtra("StaCirParam");
        super.onCreate(savedInstanceState);
    }

    @Override
    public HivsBaseViewModel setViewmodel() {
        return null;
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
        mBinding.recyclerView.setAdapter(mAdapter);
        mViewModel.getCircleClass(staCirParam);
        mBinding.refresh.setEnablePureScrollMode(true);
    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        if (viewEventResouce.eventType == 103 && (Collection) viewEventResouce.data != null) {
            mAdapter.getmObjects().addAll((Collection) viewEventResouce.data);
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
