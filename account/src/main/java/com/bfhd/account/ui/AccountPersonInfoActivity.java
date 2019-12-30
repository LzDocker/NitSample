package com.bfhd.account.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.R;
import com.bfhd.account.databinding.AccountActivityPersonInfoBinding;
import com.bfhd.account.vm.AccountViewModel;

import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.ui.common.CircleSourceUpFragment;
import com.bfhd.circle.vo.bean.SourceUpParam;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.vo.VisitingCardVo;
import com.docker.common.common.widget.picker.CommonWheelPicker;

import javax.inject.Inject;

import cn.qqtheme.framework.picker.OptionPicker;

/*
 * 个人信息
 **/

public class AccountPersonInfoActivity extends HivsBaseActivity<AccountViewModel, AccountActivityPersonInfoBinding> {
    @Inject
    ViewModelProvider.Factory factory;
    SourceUpParam mHeadSourceUpParam;
    private CircleSourceUpFragment sourceHeadUpFragment;
    private VisitingCardVo visitingCardVo = new VisitingCardVo();
    public boolean isSingle = false;

    @Override
    public AccountViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(AccountViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.account_activity_person_info;
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel.cardDetail();
        mBinding.setItem(visitingCardVo);
    }

    @Override
    public void initView() {
        mHeadSourceUpParam = new SourceUpParam(SourceUpParam.SOURCE_TYPE_AUTO, 1);
        mHeadSourceUpParam.needCrop = true;
        sourceHeadUpFragment = CircleSourceUpFragment.newInstance(mHeadSourceUpParam);
        sourceHeadUpFragment.setmSingleImageView(mBinding.imgHead);
        FragmentUtils.add(getSupportFragmentManager(), sourceHeadUpFragment, R.id.frame_head);

        mHeadSourceUpParam.status.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                // 根据状态回调 来判断是否图片已经上传
                if (mHeadSourceUpParam.status.get() == 2) {
                    //获取头像照片的路径信息
                    visitingCardVo.setAvatar(mHeadSourceUpParam.imgList.get(0));
                }
            }
        });

        //选择性别
        mBinding.accountTvSex.setOnClickListener(v -> {
            CommonWheelPicker.showSexPicker(AccountPersonInfoActivity.this, new OptionPicker.OnOptionPickListener() {
                @Override
                public void onOptionPicked(int index, String item) {
                    mBinding.accountTvSex.setText(item);
                    sexToCode(item);
                }
            });
        });


        //保存个人信息
        mBinding.proTvSubmit.setOnClickListener(v -> {
            if (checkParam()) {
                VisitingCardVo visitingCardVo1 = visitingCardVo;
                mViewModel.saveCardInfo(visitingCardVo);
            }
        });

        //点击选择头像
        mBinding.llHeadIcon.setOnClickListener(v -> {
            isSingle = true;
            sourceHeadUpFragment.enterToSelect(2);
        });


    }


    public boolean checkParam() {
        VisitingCardVo visitingCardVo = mBinding.getItem();
        if (TextUtils.isEmpty(visitingCardVo.getFullName())) {
            ToastUtils.showShort("请输入真实姓名");
            return false;
        }

        return true;
    }

    public void sexToCode(String sex) {
        String code = "";
        if (sex.equals("保密")) {
            visitingCardVo.setSex("0");
        } else if (sex.equals("男")) {
            visitingCardVo.setSex("1");
        } else {
            visitingCardVo.setSex("2");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (isSingle) {
            sourceHeadUpFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 108:
                if (viewEventResouce.data != null) {
                    visitingCardVo = (VisitingCardVo) viewEventResouce.data;
                    mBinding.setItem(visitingCardVo);
                } else {
                    UserInfoVo userInfoVo = CacheUtils.getUser();
                    String phone = userInfoVo.mobile;
                    visitingCardVo = new VisitingCardVo();
                    visitingCardVo.setTel(phone);
                    mBinding.setItem(visitingCardVo);
                }
                break;

            case 538:
                AccountPersonInfoActivity.this.finish();
                break;

            case 539:

                break;
        }
    }
}
