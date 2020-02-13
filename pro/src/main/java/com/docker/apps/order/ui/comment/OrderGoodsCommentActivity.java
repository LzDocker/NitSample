package com.docker.apps.order.ui.comment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.apps.R;
import com.docker.apps.databinding.ProOrderGoodscommentActivityBinding;
import com.docker.apps.databinding.ProOrderGoodslistActivityBinding;
import com.docker.apps.order.vm.OrderCommentViewModel;
import com.docker.apps.order.vm.OrderCommonViewModel;
import com.docker.apps.order.vo.GoodsVo;
import com.docker.apps.order.vo.OrderVoV2;
import com.docker.cirlev2.ui.common.CircleSourceUpFragment;
import com.docker.cirlev2.util.BdUtils;
import com.docker.cirlev2.vo.entity.RstVo;
import com.docker.cirlev2.vo.param.SourceUpParam;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vo.UserInfoVo;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

/*
 * 评价商品
 **/
@Route(path = AppRouter.ORDER_GOODS_COMMENT)
public class OrderGoodsCommentActivity extends NitCommonActivity<OrderCommentViewModel, ProOrderGoodscommentActivityBinding> {

    private OrderCommonViewModel innervm;
    private Disposable disposable;

    private SourceUpParam mSourceUpParam;
    private CircleSourceUpFragment sourceUpFragment;

    private OrderVoV2 orderVoV2;
    private GoodsVo goodsVo;


    @Override
    public OrderCommentViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(OrderCommentViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pro_order_goodscomment_activity;
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar.setTitle("评价晒单");
        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
            if (rxEvent.getT().equals("refresh_buy")) {

            }
        });
        mViewModel.mCommentOb.set(-1);
        mBinding.setViewmodel(mViewModel);
        orderVoV2 = (OrderVoV2) getIntent().getSerializableExtra("orderVoV2");
        goodsVo = (GoodsVo) getIntent().getSerializableExtra("goodsVo");
        mBinding.setImg(BdUtils.getCompleteImg(goodsVo.goodsImg));
        mSourceUpParam = new SourceUpParam(SourceUpParam.SOURCE_TYPE_AUTO, 9);
        sourceUpFragment = CircleSourceUpFragment.newInstance(mSourceUpParam);
        FragmentUtils.add(getSupportFragmentManager(), sourceUpFragment, com.docker.cirlev2.R.id.souce_up_frame);
        mSourceUpParam.status.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                // 根据状态回调 来判断是否能发布了
                switch (mSourceUpParam.status.get()) {
                    case 2:
                        realPublish();
                        break;
                    case 3:
                        hidWaitDialog();
                        ToastUtils.showShort("上传资源失败请重试！");
                        break;
                }
            }
        });

    }

    @Override
    public void initView() {
        mBinding.proEventDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBinding.tvTextContent.setText(s.length() + "/50");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mBinding.tvPub.setOnClickListener(v -> {

            if (TextUtils.isEmpty(mBinding.proEventDesc.getText().toString())) {
                ToastUtils.showShort("请先输入" + mBinding.proEventDesc.getHint());
                return;
            }

            if (sourceUpFragment.selectList != null && sourceUpFragment.selectList.size() > 0) {
                sourceUpFragment.processUpload();
            } else {
                realPublish();
            }
        });
    }


    private void realPublish() {
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> paramMap = new HashMap();
        paramMap.put("circleid", goodsVo.circleid);
        paramMap.put("orderid", orderVoV2.id);
        paramMap.put("goodsid", goodsVo.goodsid);
        paramMap.put("utid", goodsVo.utid);
        paramMap.put("dynamicid", goodsVo.dynamicid);
        paramMap.put("push_memberid", goodsVo.push_memberid);
        paramMap.put("push_uuid", goodsVo.push_uuid);
        paramMap.put("memberid", userInfoVo.uid);
        paramMap.put("uuid", userInfoVo.uuid);
        paramMap.put("nickname", userInfoVo.fullName);
        paramMap.put("avatar", userInfoVo.avatar);
        paramMap.put("content", mBinding.proEventDesc.getText().toString().trim());
        paramMap.put("starNum", String.valueOf(mViewModel.mCommentOb.get() + 1));
//        paramMap.put("utid", mHandParam.getUtid());
        if (mSourceUpParam.imgList.size() > 0) {
            for (int i = 0; i < mSourceUpParam.imgList.size(); i++) {
                paramMap.put("resource[" + i + "][t]", "1");
                paramMap.put("resource[" + i + "][url]", mSourceUpParam.imgList.get(i));
                paramMap.put("resource[" + i + "][img]", mSourceUpParam.imgList.get(i));
                paramMap.put("resource[" + i + "][sort]", i + 1 + "");
            }
        }
        if (mSourceUpParam.upLoadVideoList.size() > 0) {
            for (int i = 0; i < mSourceUpParam.upLoadVideoList.size(); i++) {
                paramMap.put("resource[" + i + "][t]", "2");
                paramMap.put("resource[" + i + "][url]", mSourceUpParam.upLoadVideoList.get(i).getVideoUrl());
                paramMap.put("resource[" + i + "][img]", mSourceUpParam.upLoadVideoList.get(i).getVideoImgUrl());
                paramMap.put("resource[" + i + "][sort]", i + 1 + "");
            }
        }
        this.runOnUiThread(() -> mViewModel.commentServer(paramMap));

//        ARouter.getInstance().build(AppRouter.ORDER_COMMENT_SUCCESS_LIST)
//                .withString("orderid", orderVoV2.id)
//                .withSerializable("orderVoV2", orderVoV2)
//                .navigation();
    }

    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        NitDelegetCommand nitDelegetCommand = null;

        nitDelegetCommand = new NitDelegetCommand() {
            @Override
            public Class providerOuterVm() {
                return OrderCommonViewModel.class;
            }

            @Override
            public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {
                innervm = (OrderCommonViewModel) commonListVm;

            }
        };
        return nitDelegetCommand;
    }


    @Override
    public void initObserver() {

        mViewModel.mRstLv.observe(this, rstVo -> {
            // 成功界面
            ARouter.getInstance().build(AppRouter.ORDER_COMMENT_SUCCESS_LIST)
                    .withString("orderid", orderVoV2.id)
                    .withSerializable("orderVoV2", orderVoV2)
                    .navigation();

            finish();
        });

    }

    @Override
    public void initRouter() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        sourceUpFragment.onActivityResult(requestCode, resultCode, data);
    }
}