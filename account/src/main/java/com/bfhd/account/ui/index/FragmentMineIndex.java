package com.bfhd.account.ui.index;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bfhd.account.vm.AccountIndexListViewModel;
import com.bfhd.circle.vo.ShareVo;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.ui.base.NitCommonListFragment;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;

import io.reactivex.disposables.Disposable;

public class FragmentMineIndex extends NitCommonListFragment<AccountIndexListViewModel> {

    CommonListOptions commonListReq = new CommonListOptions();

    public static FragmentMineIndex newInstance() {
        return new FragmentMineIndex();
    }

    private Disposable disposable;

//    AccountHeadCardViewModel accountHeadCardViewModel;


    @Override
    public AccountIndexListViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(AccountIndexListViewModel.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



//        accountHeadCardViewModel = ProviderAccountHeadCard.providerAccountHead(this, factory, mViewModel, 0);

//        accountHeadCardViewModel = ViewModelProviders.of(this, factory).get(AccountHeadCardViewModel.class);
//        mViewModel.addCardVo(accountHeadCardViewModel.accountHeadCardVo);
//        this.getLifecycle().addObserver(accountHeadCardViewModel);
//        accountHeadCardViewModel.mServerLiveData.observe(this, null);


//        AccountIndexItemVo accountIndexItemVo = new AccountIndexItemVo();
//        mViewModel.addCardVo(accountIndexItemVo);


        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
            if (rxEvent.getT().equals("refresh_focus") || rxEvent.getT().equals("refresh_card")) {
//                accountHeadCardViewModel.loadData();
            }
        });

    }

    @Override
    public CommonListOptions getArgument() {
        commonListReq.refreshState = 3;
        commonListReq.RvUi = 0;
        return commonListReq;
    }


    @Override
    public void onVisible() {
        super.onVisible();
//        accountHeadCardViewModel.fetchMyInfo();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }

    public void showShare(ShareVo shareBean) {
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
//        UMImage image = new UMImage(this.getHoldingActivity(), shareBean.getShareImg());//网络图片
//        image.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
//        image.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分
        UMWeb web = new UMWeb(shareBean.getShareUrl());
        web.setTitle(shareBean.getShareTit());//标题
//        web.setThumb(image);  //缩略图
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

    @Override
    public void initImmersionBar() {

    }
}
