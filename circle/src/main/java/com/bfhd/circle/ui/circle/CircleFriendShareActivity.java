package com.bfhd.circle.ui.circle;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.bfhd.circle.R;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.databinding.CircleActivityCircleFriendShareBinding;
import com.bfhd.circle.vm.CircleViewModel;
import com.bfhd.circle.vo.MemberGroupingVo;
import com.bfhd.circle.vo.ShareVo;
import com.bfhd.circle.vo.bean.StaCirParam;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vo.UserInfoVo;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;

import java.util.HashMap;

import javax.inject.Inject;

/*
 * 圈子邀请好友
 * */
public class CircleFriendShareActivity extends HivsBaseActivity<CircleViewModel, CircleActivityCircleFriendShareBinding> {

    @Inject
    ViewModelProvider.Factory factory;
    //    private int type;
    private StaCirParam mStartParam;

    MemberGroupingVo modifyGroupVo;

    public static void startMe(Context context, StaCirParam startCircleBean, String circlename, String circleimg) {

        Intent intent = new Intent(context, CircleFriendShareActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("mStartParam", startCircleBean);
        bundle.putSerializable("circlename", circlename);
        bundle.putSerializable("circleimg", circleimg);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.circle_activity_circle_friend_share;
    }

    @Override
    public CircleViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleViewModel.class);
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
        mToolbar.setTitle("邀请好友");

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
            CircleMemberGroupListActivity.startMe(this, mStartParam, 102);
        });
    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 500:
                if (viewEventResouce.data != null) {
                    showShare((ShareVo) viewEventResouce.data);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 102) {
            modifyGroupVo = (MemberGroupingVo) data.getSerializableExtra("group");
            mBinding.tvGroup.setText(modifyGroupVo.getGroupName());
        }

    }

    public void showShare(ShareVo shareBean) {
        if(shareBean==null){
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


}
