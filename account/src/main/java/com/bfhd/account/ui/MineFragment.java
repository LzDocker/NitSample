package com.bfhd.account.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.BR;
import com.bfhd.account.R;
import com.bfhd.account.databinding.AccountFragmentMineBinding;
import com.bfhd.account.vm.AccountViewModel;
import com.bfhd.account.vo.MyInfoVo;
import com.bfhd.circle.base.CommonFragment;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.vo.ShareVo;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.vo.VisitingCardVo;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import me.leolin.shortcutbadger.ShortcutBadger;

public class MineFragment extends CommonFragment<AccountViewModel, AccountFragmentMineBinding> {

    private String TAG = "MineFragment";

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    private Disposable disposable;

    private String headImg = "";

    @Inject
    ViewModelProvider.Factory factory;

    @Override
    protected int getLayoutId() {
        return R.layout.account_fragment_mine;
    }

    @Override
    public AccountViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(AccountViewModel.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.onActivityCreated(savedInstanceState);
        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
//            if (rxEvent.getT().equals("refresh_focus") || rxEvent.getT().equals("refresh_card")) {
//                mViewModel.getMyInfo();
//            }
        });
    }



    @Override
    protected void initView(View var1) {
//        mViewModel.cardDetail();

//        mViewModel.getMyInfo();
        // 个人信息
        mBinding.get().accountIvUserIcon.setOnClickListener(v -> {
            Intent intent = new Intent(MineFragment.this.getHoldingActivity(), AccountPersonInfoActivity.class);
            startActivity(intent);
        });
        // 圈子
        mBinding.get().llMineCompanyCircle.setOnClickListener(v -> {
            ARouter.getInstance().build(AppRouter.MINECIRCLELIST).navigation();
        });
        // 动态
        mBinding.get().llMineDynamic.setOnClickListener(v -> {
            Intent intent = new Intent(MineFragment.this.getHoldingActivity(), AccountMineActiveActivity.class);
            startActivity(intent);
        });
        // 关注
        mBinding.get().llMineLiked.setOnClickListener(v -> {
            Intent intent = new Intent(MineFragment.this.getHoldingActivity(), AccounAttentionListActivity.class);
            startActivity(intent);

        });
        // 粉丝
        mBinding.get().llMineFans.setOnClickListener(v -> {
            Intent intent = new Intent(MineFragment.this.getHoldingActivity(), AccounFansListActivity.class);
            startActivity(intent);
        });

        //积分
        mBinding.get().linPoint.setOnClickListener(v -> {
            Intent intent = new Intent(MineFragment.this.getHoldingActivity(), AccounPointRecordListActivity.class);
            startActivity(intent);
        });
        // 消息中心
        mBinding.get().accountLinMessage.setOnClickListener(v -> {
            Intent intent = new Intent(MineFragment.this.getHoldingActivity(), AccounMessageListtActivity.class);
            startActivity(intent);

        });
        // 我的订单
        mBinding.get().accountTvOrder.setOnClickListener(v -> {
            Intent intent = new Intent(MineFragment.this.getHoldingActivity(), AccounOrderListtActivity.class);
            startActivity(intent);

        });

        // 积分商城
        mBinding.get().accountTvJfsc.setOnClickListener(v -> {
            // webview
            Intent intent = new Intent(MineFragment.this.getHoldingActivity(), AccountPointStoreActivity.class);
            startActivity(intent);

        });
        // 我的收藏
        mBinding.get().accountTvWdsc.setOnClickListener(v -> {
            Intent intent = new Intent(MineFragment.this.getHoldingActivity(), AccountSafeCollectionListActivity.class);
            startActivity(intent);

        });
        // 一键呼救设置
        mBinding.get().accountTvOnekeysos.setOnClickListener(v -> {
            Intent intent = new Intent(MineFragment.this.getHoldingActivity(), AccountOneKeySosActivity.class);
            startActivity(intent);

        });
        // 签到记录
        mBinding.get().accountTvSignlist.setOnClickListener(v -> {

            Intent intent = new Intent(MineFragment.this.getHoldingActivity(), AccountSigninListActivity.class);
            startActivity(intent);

        });
        // 邀请好友
        mBinding.get().accountTvJoinFriend.setOnClickListener(v -> {
            // 放在圈子里实现

            ShareVo shareVo = new ShareVo();
            UserInfoVo userInfoVo = CacheUtils.getUser();
            shareVo.setShareDesc(userInfoVo.nickname + "邀请您使用海外安全通");
            shareVo.setShareTit("邀请好友");
            shareVo.setShareUrl("http://app.cosri.org.cn/index.php?m=default.download_app&from=singlemessage&isappinstalled=0");
            showShare(shareVo);

        });
        // 意见反馈
        mBinding.get().accountTvSegusstion.setOnClickListener(v -> {
            Intent intent = new Intent(MineFragment.this.getHoldingActivity(), AccounSuggestionActivity.class);
            startActivity(intent);

        });
        // 设置
        mBinding.get().accountSetting.setOnClickListener(v -> {
            Intent intent = new Intent(MineFragment.this.getHoldingActivity(), AccounSettingActivity.class);
            intent.putExtra("tel", mBinding.get().getMyinfo().getContact_us());
            startActivity(intent);
        });
        // 联系我们
        mBinding.get().accountTvCallUs.setOnClickListener(v -> {
            if (TextUtils.isEmpty(mBinding.get().getMyinfo().getContact_us())) {
                return;
            }
            //跳转到拨号界面，同时传递电话号码
            Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mBinding.get().getMyinfo().getContact_us()));
            startActivity(dialIntent);
        });
        mBinding.get().accountVideo.setOnClickListener(v -> {
            String videoUrl =
                    "https://zaijiaxue.oss-cn-beijing.aliyuncs.com/static2/var/upload/cn/language/6%20%E9%BB%84%E5%B1%B1%E5%A5%87%E6%9D%BE_L%20CN.mp4";
            String thumbUrl = "http://app.zjxk12.com/var/upload/2019/05/2019050508195443760_600x400.jpg";
//            ARouter.getInstance().build(AppRouter.Video_Player).withString("videoUrl", videoUrl).withString("thumbUrl", thumbUrl).navigation();

//            AliVideoPlayerFragment fragment = AliVideoPlayerFragment.newInstance(videoUrl,thumbUrl);
//            FragmentUtils.add(getChildFragmentManager(), fragment, R.id.pro_frame);


        });
    }

    private void setUserInfo(VisitingCardVo visitingCardVo) {
        UserInfoVo userInfoVo = CacheUtils.getUser();
        mBinding.get().accountUserName.setText(userInfoVo.fullName);
        mBinding.get().accountCompanyName.setText(userInfoVo.nickname);
        mBinding.get().accountTvNum.setText("暂无积分¬");
        mBinding.get().accountUserName.setText(userInfoVo.fullName);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void initImmersionBar() {
        superInitImmersionBar(R.color.white, null);
    }

    @Override
    public void onVisible() {
        super.onVisible();
//        superInitImmersionBar(R.color.white, null);
        mViewModel.getMyInfo();
    }

    @Override
    public void onInvisible() {
        super.onInvisible();
//        superInitImmersionBar(R.color.account_colorPrimary, null);
    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);

        switch (viewEventResouce.eventType) {
            case 108:
                VisitingCardVo visitingCardVo = (VisitingCardVo) viewEventResouce.data;
                if (viewEventResouce != null) {
                    setUserInfo(visitingCardVo);
                }
                break;
            case 111: // myinfo
                if (viewEventResouce.data != null) {
                    MyInfoVo myInfoVo = (MyInfoVo) viewEventResouce.data;
                    mBinding.get().setVariable(BR.myinfo, myInfoVo);
                    if (!TextUtils.isEmpty(headImg) && !TextUtils.isEmpty(myInfoVo.getAvatar()) && headImg.equals(myInfoVo.getAvatar())) {
                    } else {
                        headImg = myInfoVo.getAvatar();
                        mBinding.get().setVariable(BR.imgurl, myInfoVo.getAvatar());
                    }
                    if (!TextUtils.isEmpty(myInfoVo.getNotReadMsgNum())) {
                        int num = Integer.parseInt(myInfoVo.getNotReadMsgNum());
                        ShortcutBadger.applyCount(getHoldingActivity(), num);
                        RxBus.getDefault().post(new RxEvent<>("Badger", num));
                    }
                }
                break;
        }
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

}
