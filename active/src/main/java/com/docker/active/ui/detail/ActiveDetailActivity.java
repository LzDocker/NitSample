package com.docker.active.ui.detail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.active.R;
import com.docker.active.databinding.ProActiveDetailActivityBinding;
import com.docker.active.vm.ActiveCommonViewModel;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vo.ShareBean;
import com.docker.common.common.vo.UserInfoVo;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

@Route(path = AppRouter.ACTIVE_DEATIL_ACTIVITY)
public class ActiveDetailActivity extends NitCommonActivity<ActiveCommonViewModel, ProActiveDetailActivityBinding> {


    public int edit = 0;

    @Override
    public void initView() {
        mToolbar.setTitle("活动详情");
        mToolbar.setIvRight(R.mipmap.gray_menu, v -> {
            onShareClick();
        });
    }

    @Override
    public void initObserver() {
        mViewModel.mShareLv.observe(this, new Observer<ShareBean>() {
            @Override
            public void onChanged(@Nullable ShareBean shareBean) {
                if (shareBean != null) {
                    showShare(shareBean);
                }
            }
        });
    }

    Disposable disposable;
    ActiveDetailFragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragment = (ActiveDetailFragment) ARouter.getInstance()
                .build(AppRouter.ACTIVE_DEATIL)
                .withInt("edit", getIntent().getIntExtra("edit", 0))
                .withSerializable("activityid", getIntent().getStringExtra("activityid")).navigation();
        FragmentUtils.add(getSupportFragmentManager(), fragment, R.id.frame);


        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
            if (rxEvent.getT().equals("login_state_change")) {
//                finish();
//                ActiveDetailActivity.this.recreate();
                FragmentUtils.remove(fragment);
                fragment = (ActiveDetailFragment) ARouter.getInstance()
                        .build(AppRouter.ACTIVE_DEATIL)
                        .withInt("edit", getIntent().getIntExtra("edit", 0))
                        .withSerializable("activityid", getIntent().getStringExtra("activityid")).navigation();
                FragmentUtils.add(getSupportFragmentManager(), fragment, R.id.frame);
            }
        });
    }

    @Override
    public void initRouter() {
        ARouter.getInstance().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pro_active_detail_activity;
    }

    @Override
    public ActiveCommonViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(ActiveCommonViewModel.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }

    public void onShareClick() {
        if (CacheUtils.getUser() == null) {
            ARouter.getInstance().build(AppRouter.ACCOUNT_LOGIN).withBoolean("isFoceLogin", true).navigation();
            return;
        }
        if (fragment != null && fragment.activeVo != null) {
            HashMap<String, String> params = new HashMap<>();
            UserInfoVo userInfoVo = CacheUtils.getUser();
            //circleid 598
            //dataid 84
            //activityid 84
            //dynamicid
            //memberid 67
            //type activity
            //utid 8d93e6a11a530bafabe31724e2b35972
            //uuid 420cd8fd09e4ae6cfb8f3b3fdf5b7af4

            params.put("type", "activity");
            params.put("circleid", fragment.activeVo.circleid);
            params.put("memberid", userInfoVo.uid);
            params.put("uuid", userInfoVo.uuid);
            params.put("activityid", fragment.activeVo.dataid);
            params.put("utid", fragment.activeVo.utid);
            params.put("dataid", fragment.activeVo.dataid);

            mViewModel.FetchShareData(params);
        }
    }

    private void showShare(ShareBean shareBean) {
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
}

