package com.bfhd.circle.ui.safe;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.circle.R;
import com.bfhd.circle.base.CommonFragment;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.databinding.CircleFragmentDynamicBinding;
import com.bfhd.circle.vm.CircleDynamicViewModel;
import com.bfhd.circle.vo.StaDynaVo;
import com.bfhd.circle.vo.bean.StaCirParam;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vo.ShareBean;
import com.docker.common.common.widget.refresh.SmartRefreshLayout;
import com.docker.core.util.LayoutManager;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/*
 *动态/问答 列表
 * */
public class DynamicFragment extends CommonFragment<CircleDynamicViewModel, CircleFragmentDynamicBinding> {

    private SmartRefreshLayout smartRefreshLayout;
    @Inject
    ViewModelProvider.Factory factory;
    StaDynaVo staDynaVo;

    @Override
    protected int getLayoutId() {
        return R.layout.circle_fragment_dynamic;
    }

    private Disposable disposable;

    public static DynamicFragment newInstance(StaDynaVo staDynaVo, StaCirParam mStartParam) {
        DynamicFragment dynamicFragment = new DynamicFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("staDynaVo", staDynaVo);
        bundle.putSerializable("mStartParam", mStartParam);
        dynamicFragment.setArguments(bundle);
        return dynamicFragment;
    }

    @Override
    protected CircleDynamicViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleDynamicViewModel.class);
    }

    @Override
    protected void initView(View var1) {
        mBinding.get().setViewmodel(mViewModel);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.onActivityCreated(savedInstanceState);
        staDynaVo = (StaDynaVo) getArguments().getSerializable("staDynaVo");
        if (staDynaVo.ReqParam.containsKey("goodsui")) {
//            mBinding.get().recyclerView.setLayoutManager(com.docker.core.binding.recycle.LayoutManager
//                    .staggeredGrid(2, LinearLayoutManager.VERTICAL)
//                    .create(mBinding.get().recyclerView));
            mBinding.get().recyclerView.setLayoutManager(LayoutManager
                    .grid(2)
                    .create(mBinding.get().recyclerView));
        } else {
            mBinding.get().recyclerView.setLayoutManager(LayoutManager.linear().create(mBinding.get().recyclerView));
        }
        if (staDynaVo.ReqParam.containsKey("attention")) { // 关注persion
            mViewModel.isAddAttenPersion = true;
        }
        mViewModel.initVMParam(staDynaVo, (StaCirParam) getArguments().getSerializable("mStartParam"));
        mViewModel.getInfoData();
        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
            if (rxEvent.getT().equals("dynamic_refresh")) {
                mViewModel.mPage = 1;
                mViewModel.items.clear();
            }
        });
        switch (mViewModel.mStaDy.UiType) {
            case 2:
                mViewModel.isControlrefenable = false;
                break;
            case 3:
            case 1:
                mBinding.get().recyclerView.setNestedScrollingEnabled(true);
                mViewModel.isControlrefenable = true;
                mBinding.get().refresh.setEnableRefresh(false);
                break;
        }
    }

    @Override
    public void onVisible() {
        super.onVisible();
        if (mViewModel.items.size() == 0 && mViewModel.mPage == 1 && !mViewModel.isLoadState) {
            mViewModel.getInfoData();
        }
    }

    @Override
    public void OnRefresh(SmartRefreshLayout smartRefreshLayout) {
        super.OnRefresh(smartRefreshLayout);
        mViewModel.mPage = 1;
        mViewModel.getInfoData();
        this.smartRefreshLayout = smartRefreshLayout;
    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 201:
            case 202:
            case 203:
                if (this.smartRefreshLayout != null) {
                    smartRefreshLayout.finishRefresh();
                    smartRefreshLayout.finishLoadMore();
                }
                if (mBinding.get().refresh != null) {
                    if (viewEventResouce.data != null && ((Collection) viewEventResouce.data).size() < 20) {
                        mBinding.get().refresh.setNoMoreData(true);
                    } else if (viewEventResouce.data == null && mViewModel.items.size() > 0) {
                        mBinding.get().refresh.setNoMoreData(true);
                    } else {
                        mBinding.get().refresh.setNoMoreData(false);
                    }
                }

                if (viewEventResouce.data == null) {
                    if (mViewModel.mStaDy.UiType == 3) { // 相关推荐
                        mBinding.get().getRoot().setVisibility(View.GONE);
                        mViewModel.mStaDy.status.set(false);
                        mViewModel.mStaDy.status.notifyChange();
                    }
                } else {
                    if (mViewModel.mStaDy.UiType == 3) { // 相关推荐
                        mViewModel.mStaDy.status.set(true);
                        mViewModel.mStaDy.status.notifyChange();
                    }
                }
                break;
            case 204: // 大图预览
//                PictureSelector.create(this).externalPicturePreview(Integer.parseInt(viewEventResouce.message), (List<LocalMedia>) viewEventResouce.data);
                PictureSelector
                        .create(this)
                        .themeStyle(R.style.picture_default_style)
                        .openExternalPreview(Integer.parseInt(viewEventResouce.message), (List<LocalMedia>) viewEventResouce.data);
                break;
            case 211:
                showShare((ShareBean) viewEventResouce.data);
                break;
        }
    }

    @Override
    public void initImmersionBar() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }


    // 外部更改请求接口的参数
    // isall 是否全量更改
    public void UpdateReqParam(boolean isAll, Pair<String, String> pair) {
        mViewModel.isLoadState = false;
        if (isAll) {
            mViewModel.mStaDy.ReqParam.clear();
            if (!TextUtils.isEmpty(pair.first) && !TextUtils.isEmpty(pair.second)) {
                mViewModel.mStaDy.ReqParam.put(pair.first, pair.second);
            }
        } else {
            if (!TextUtils.isEmpty(pair.first) && !TextUtils.isEmpty(pair.second)) {
                mViewModel.mStaDy.ReqParam.put(pair.first, pair.second);
            }
            if (!TextUtils.isEmpty(pair.first) && TextUtils.isEmpty(pair.second)) {
                mViewModel.mStaDy.ReqParam.remove(pair.first);
            }
        }
        mViewModel.mPage = 1;
        mViewModel.items.clear();
    }


    // 外部更改请求接口的参数
    // isall 是否全量更改
    public void UpdateReqParam(boolean isAll, ArrayList<Pair<String, String>> pairList) {
        mViewModel.isLoadState = false;
        if (isAll) {
            mViewModel.mStaDy.ReqParam.clear();
            for (int i = 0; i < pairList.size(); i++) {
                if (!TextUtils.isEmpty(pairList.get(i).first) && !TextUtils.isEmpty(pairList.get(i).second)) {
                    mViewModel.mStaDy.ReqParam.put(pairList.get(i).first, pairList.get(i).second);
                }
            }
        } else {
            if (pairList != null && pairList.size() > 0) {
                String key = pairList.get(0).first;
                if ("goodlist".equals(key)) {
                    for (int i = 0; i < pairList.size(); i++) {
                        if (!TextUtils.isEmpty(pairList.get(i).first)) {
                            mViewModel.mStaDy.ReqParam.put(pairList.get(i).first, pairList.get(i).second);
                        }
                    }
                } else {
                    for (int i = 0; i < pairList.size(); i++) {
                        if (!TextUtils.isEmpty(pairList.get(i).first) && !TextUtils.isEmpty(pairList.get(i).second)) {
                            mViewModel.mStaDy.ReqParam.put(pairList.get(i).first, pairList.get(i).second);
                        }
                    }
                }
            }

        }
        mViewModel.mPage = 1;
        mViewModel.items.clear();
    }

    public void showShare(ShareBean shareBean) {
        if (shareBean == null) {
            return;
        }
        ShareBoardConfig config = new ShareBoardConfig();//新建ShareBoardConfig
        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR);
        config.setTitleVisibility(false);
        config.setIndicatorVisibility(false);
        config.setCancelButtonVisibility(false);
        config.setCancelButtonVisibility(false);
        config.setShareboardBackgroundColor(Color.WHITE);
        UMImage image = new UMImage(this.getHoldingActivity(), shareBean.getShareImg());//网络图片
        image.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
        image.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分
        UMWeb web = new UMWeb(shareBean.getShareUrl());
        web.setTitle(shareBean.getShareTit());//标题
        web.setThumb(image);  //缩略图
        web.setDescription(shareBean.getShareDesc());//描述
        new ShareAction(this.getHoldingActivity()).withMedia(web)
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        ToastUtils.showShort("分享失败请重试");
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {

                    }
                }).open(config);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this.getHoldingActivity()).onActivityResult(requestCode, resultCode, data);
    }

    public interface onListener {
        void OnListener(String code, String msg);
    }

    private onListener listener;

    public void setListener(onListener listener) {
        this.listener = listener;
    }

}
