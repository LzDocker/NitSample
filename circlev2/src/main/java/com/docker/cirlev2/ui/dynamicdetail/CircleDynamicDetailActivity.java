package com.docker.cirlev2.ui.dynamicdetail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2DynamicDetailActivityBinding;
import com.docker.cirlev2.vm.CircleDynamicDetailViewModel;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.widget.card.NitBaseProviderCard;
import com.docker.common.databinding.CommonFragmentListBinding;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

import static com.docker.common.common.router.AppRouter.CIRCLE_dynamic_v2_detail;

@Route(path = CIRCLE_dynamic_v2_detail)
public class CircleDynamicDetailActivity extends NitCommonActivity<CircleDynamicDetailViewModel, Circlev2DynamicDetailActivityBinding> {

    @Autowired
    String dynamicId;
    public ServiceDataBean mDynamicDetailVo;
    private Disposable disposable;

    @Override
    protected int getLayoutId() {
        return R.layout.circlev2_dynamic_detail_activity;
    }

    @Override
    public CircleDynamicDetailViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleDynamicDetailViewModel.class);
    }

    @Override
    public void initView() {
        mToolbar.hide();
        dynamicId = getIntent().getStringExtra("dynamicId");
        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
            if (rxEvent.getT().equals("show_share")) {
                showShare((ServiceDataBean.ShareBean) rxEvent.getR());
            }
        });
    }


    @Override
    public void initObserver() {
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> param = new HashMap();
        param.put("dynamicid", dynamicId);
        param.put("memberid", userInfoVo.uid);
        param.put("uuid", userInfoVo.uuid);
        mViewModel.fechDynamicDetail(param).observe(CircleDynamicDetailActivity.this, serviceDataBean -> {
            mDynamicDetailVo = serviceDataBean;
            processData();
            processView();
        });
    }

    private void processView() {
        if (mDynamicDetailVo != null) {
            // ui


        } else {


        }
    }

    @Override
    public void initRouter() {
        ARouter.getInstance().inject(this);
    }

    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        return null;
    }

    private void processData() {
        if (mDynamicDetailVo != null) {
            switch (mDynamicDetailVo.getType()) {
                case "goods":
                case "news":
                    FragmentUtils.add(getSupportFragmentManager(), DynamicH5Fragment.getInstance(mDynamicDetailVo), R.id.frame_content);
                    break;
                case "dynamic":
                case "answer":
                    FragmentUtils.add(getSupportFragmentManager(), DynamicDetailFragment.getInstance(mDynamicDetailVo), R.id.frame_content);
                    break;
            }
        }
    }


    public void showShare(ServiceDataBean.ShareBean shareBean) {
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
        UMImage image = new UMImage(this, shareBean.getShareImg());//网络图片
        image.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
        image.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分
        UMWeb web = new UMWeb(shareBean.getShareUrl());
        web.setTitle(shareBean.getShareTit());//标题
        web.setThumb(image);  //缩略图
        web.setDescription(shareBean.getShareDesc());//描述
        new ShareAction(this).withMedia(web)
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
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}