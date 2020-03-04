package com.docker.apps.active.ui.manager;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dcbfhd.utilcode.utils.ActivityUtils;
import com.dcbfhd.utilcode.utils.ImageUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.apps.R;
import com.docker.apps.active.vm.ActiveCommonViewModel;
import com.docker.apps.active.vo.ActiveManagerVo;
import com.docker.apps.active.vo.ActiveVo;
import com.docker.apps.active.vo.card.ActiveInfoCard;
import com.docker.apps.active.vo.card.ActiveManagerCard;
import com.docker.apps.active.vo.card.ActiveManagerDetailVo;
import com.docker.apps.databinding.ProActiveManagerDetailBinding;
import com.docker.cirlev2.util.BdUtils;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.command.ReplyCommandParam;
import com.docker.common.common.config.Constant;
import com.docker.common.common.config.GlideApp;
import com.docker.common.common.config.ThiredPartConfig;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.utils.tool.PhotoGalleryUtils;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.widget.card.NitBaseProviderCard;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

/*
 * 活动管理详情
 **/

//api.php?m=activity.manage
@Route(path = AppRouter.ACTIVE_MANAGER_DETAIL)
public class ActiveManagerDetailActivity extends NitCommonActivity<ActiveCommonViewModel, ProActiveManagerDetailBinding> {

    public ActiveVo activeVo;

    public ActiveManagerDetailVo mActiveManagerDetailVo;

    private Disposable disposable;

    @Override
    public ActiveCommonViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(ActiveCommonViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pro_active_manager_detail;
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar.setTitle("活动管理");

        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.isActParent = true;
        commonListOptions.RvUi = Constant.KEY_RVUI_LINER;
        commonListOptions.falg = 0;
        NitBaseProviderCard.providerCardNoRefreshForFrame(getSupportFragmentManager(), R.id.frame, commonListOptions);


        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
            if (rxEvent.getT().equals("activedel")
                    || rxEvent.getT().equals("activeStusUpdate")
                    || rxEvent.getT().equals("active_refresh")
                    || rxEvent.getT().equals("activemodify")) {

//                mBinding.empty.showLoading();
                processRequest();
            }
        });

    }


    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        NitDelegetCommand nitDelegetCommand = new NitDelegetCommand() {
            @Override
            public Class providerOuterVm() {
                return null;
            }

            @Override
            public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {
                ActiveInfoCard activeInfoCard = new ActiveInfoCard(0, 0);
                NitBaseProviderCard.providerCard(commonListVm, activeInfoCard, nitCommonFragment);
                ActiveManagerCard activeManagerCard = new ActiveManagerCard(0, 1);
                NitBaseProviderCard.providerCard(commonListVm, activeManagerCard, nitCommonFragment);
                activeManagerCard.setCommand((ReplyCommandParam) o -> {
                    processDo((ActiveManagerVo) o);
                });

                mViewModel.voMediatorLiveData.observe(ActiveManagerDetailActivity.this, activeManagerDetailVo -> {
                    if (activeManagerDetailVo != null) {
                        mBinding.empty.hide();
                        activeManagerDetailVo.dataid = activeVo.dataid;
                        mActiveManagerDetailVo = activeManagerDetailVo;
                        activeInfoCard.setActiveManagerDetailVo(activeManagerDetailVo);
                        activeManagerCard.setActiveManagerDetailVo(activeManagerDetailVo);
                        mBinding.setItem(activeManagerDetailVo);
                        processImg();
                    } else {
                        mBinding.empty.showError();
                        mBinding.empty.setOnretryListener(() -> {
                            processRequest();
                        });
                    }
                });
                processRequest();
            }
        };
        return nitDelegetCommand;
    }


    private void processImg() {
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .centerCrop();
        GlideApp.with(ActiveManagerDetailActivity.this)
                .applyDefaultRequestOptions(options)
                .load(ThiredPartConfig.BarcoderUrl + mActiveManagerDetailVo.detailUrl).into(mBinding.ivCode);
    }


    private void processRequest() {
        HashMap<String, String> parm = new HashMap<>();
        activeVo = (ActiveVo) getIntent().getSerializableExtra("activeVo");
        parm.put("activityid", activeVo.dataid);
        parm.put("utid", activeVo.utid);
        parm.put("uuid", CacheUtils.getUser().uuid);
        mViewModel.getActiveManagerDv(parm);
    }


    private void processDo(ActiveManagerVo activeManagerVo) {
        /*
        *        observableList.add(new ActiveManagerVo(R.mipmap.active_icon_detail, "查看活动页面", 0));
        observableList.add(new ActiveManagerVo(R.mipmap.active_icon_share, "分享活动", 1));
        observableList.add(new ActiveManagerVo(R.mipmap.active_icon_scaner, "下载二维码", 2));
        observableList.add(new ActiveManagerVo(R.mipmap.active_icon_modify, "修改活动", 3));
        observableList.add(new ActiveManagerVo(R.mipmap.active_icon_down, "下架", 4));
        observableList.add(new ActiveManagerVo(R.mipmap.active_icon_del, "删除活动", 5));
        * */

        switch (activeManagerVo.id) {
            case 0:
                ARouter.getInstance().build(AppRouter.ACTIVE_DEATIL_ACTIVITY)
                        .withString("activityid", activeVo.dataid)
                        .withString("activitytitle", activeVo.title)
                        .navigation();
                break;
            case 1:
                share();
                break;
            case 2:
                downbarcode();
                break;
            case 3:
                editContent();
                break;
            case 4:
                downactive(activeManagerVo);
                break;
            case 5:
                delactive();
                break;
        }
    }

    private void share() {
        if (activeVo == null) {
            return;
        }
        ShareBoardConfig config = new ShareBoardConfig();//新建ShareBoardConfig
        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR);
        config.setTitleVisibility(false);
        config.setIndicatorVisibility(false);
        config.setCancelButtonVisibility(false);
        config.setCancelButtonVisibility(false);
        config.setShareboardBackgroundColor(Color.WHITE);
        UMImage image = new UMImage(ActivityUtils.getTopActivity(), BdUtils.getImgUrl(activeVo.banner.get(0).getImg()));//网络图片
        image.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
        image.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分
        UMWeb web = new UMWeb(mActiveManagerDetailVo.detailUrl);
        web.setTitle(mActiveManagerDetailVo.title);//标题
        web.setThumb(image);  //缩略图
        web.setDescription(activeVo.content);//描述
        new ShareAction(ActivityUtils.getTopActivity()).withMedia(web)
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


    private void downbarcode() {
        PhotoGalleryUtils.saveImageToGallery(this, ImageUtils.view2Bitmap(mBinding.ivCode), Constant.BaseFileFloder, "ccc");
        ToastUtils.showShort("保存成功");
    }

    private void editContent() {

        Bundle bundle = new Bundle();
        bundle.putInt("editType", 2);
        bundle.putString("activityid", activeVo.dataid);
        bundle.putString("activitytitle", activeVo.title);
        ARouter.getInstance().build(AppRouter.CIRCLE_PUBLISH_v2_INDEX)
                .withInt("editType", 2)
                .withString("title", "活动")
                .withBundle("bundle", bundle)
                .withString("pubRoterPath", AppRouter.ACTIVE_PUBLISH)
                .navigation();
    }

    private void downactive(ActiveManagerVo activeManagerVo) {
        mViewModel.updateStatus(mActiveManagerDetailVo, activeManagerVo);
    }

    private void delactive() {
        mViewModel.delActive(mActiveManagerDetailVo);
    }

    @Override
    public void initView() {

        // 扫码核销
        mBinding.tvScanerTicker.setOnClickListener(v -> {
            ARouter.getInstance().build(AppRouter.ACTIVE_MANAGE_VERFIC).withString("activityid", activeVo.dataid).navigation();
        });

        // 名单管理
        mBinding.tvPersionManager.setOnClickListener(v -> {
            ARouter.getInstance().build(AppRouter.ACTIVE_MANAGER_PERSION_LIST).withString("activityid", activeVo.dataid).navigation();
        });
    }

    @Override
    public void initObserver() {

        mViewModel.mDoactiveLv.observe(this, s -> {
            switch (s) {
                case "1":
                    ToastUtils.showShort("操作成功");
//                    finish();
                    break;
                case "2":
                    ToastUtils.showShort("删除成功");
                    finish();
                    break;
            }
        });
    }


    @Override
    public void initRouter() {

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


}
