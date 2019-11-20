package com.docker.cirlev2.ui.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2ActivityInviteBinding;
import com.docker.cirlev2.vm.CircleDetailIndexViewModel;
import com.docker.cirlev2.vo.entity.MemberGroupingVo;
import com.docker.cirlev2.vo.param.StaCirParam;
import com.docker.common.common.command.NitContainerCommand;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vo.ShareBean;
import com.docker.common.common.vo.UserInfoVo;
import com.gyf.immersionbar.ImmersionBar;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;

import java.util.HashMap;

/*
 * 圈子邀请好友
 * */
public class CircleInviteActivity extends NitCommonActivity<CircleDetailIndexViewModel, Circlev2ActivityInviteBinding> {

    private StaCirParam mStartParam;
    MemberGroupingVo modifyGroupVo;


    public static void startMe(Context context, StaCirParam startCircleBean, String circlename, String circleimg) {

        Intent intent = new Intent(context, CircleInviteActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("mStartParam", startCircleBean);
        bundle.putSerializable("circlename", circlename);
        bundle.putSerializable("circleimg", circleimg);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.circlev2_activity_invite;
    }

    @Override
    public CircleDetailIndexViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleDetailIndexViewModel.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mStartParam = (StaCirParam) getIntent().getSerializableExtra("mStartParam");
        super.onCreate(savedInstanceState);
        mBinding.setImgurl(getIntent().getStringExtra("circleimg"));
        mBinding.setName(getIntent().getStringExtra("circlename"));
    }

    @Override
    public void initView() {
        mToolbar.hide();
        mBinding.tvInvite.setOnClickListener(v -> {
            HashMap<String, String> params = new HashMap<>();
            UserInfoVo userInfoVo = CacheUtils.getUser();
            if (!TextUtils.isEmpty(mBinding.edContent.getText().toString().trim())) {
                params.put("content", mBinding.edContent.getText().toString().trim());
            }
            if (modifyGroupVo != null) {
                params.put("groupid", modifyGroupVo.getGroupid());
            } else {
                params.put("groupid", "0");
            }
            params.put("utid", mStartParam.getUtid());
            params.put("uuid", userInfoVo.uuid);
            mViewModel.invite(params);
        });

        mBinding.tvGroup.setOnClickListener(v -> {
            CircleGroupListActivity.startMe(this, mStartParam, 102);
        });
    }

    @Override
    public void initObserver() {
        mViewModel.mShareLv.observe(this, shareBean -> {
            showShare(shareBean);
        });
    }

    @Override
    public void initRouter() {

    }

    @Override
    public NitContainerCommand providerNitContainerCommand(int flag) {
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 102) {
            modifyGroupVo = (MemberGroupingVo) data.getSerializableExtra("group");
            mBinding.tvGroup.setText(modifyGroupVo.getGroupName());
        }
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
        UMImage image = new UMImage(this, shareBean.getShareImg());//网络图片
        image.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
        image.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分
        UMWeb web = new UMWeb(shareBean.getShareUrl());
        web.setTitle(shareBean.getShareTit());//标题
        web.setThumb(image);  //缩略图
        web.setDescription(shareBean.getShareDesc());//描述
        new ShareAction(this).withMedia(web)
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ)
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
    public void initImmersionBar() {
        ImmersionBar.with(CircleInviteActivity.this)
                .statusBarDarkFont(true)
                .titleBarMarginTop(mBinding.getRoot())
                .init();
    }
}
