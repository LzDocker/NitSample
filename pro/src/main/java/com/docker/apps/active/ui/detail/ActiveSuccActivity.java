package com.docker.apps.active.ui.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.FileUtils;
import com.dcbfhd.utilcode.utils.ImageUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.apps.R;
import com.docker.apps.active.ui.manager.ActiveManagerDetailActivity;
import com.docker.apps.active.vo.ActiveSucVo;
import com.docker.apps.active.vo.ActiveVo;
import com.docker.apps.active.widget.CardActivePopup;
import com.docker.apps.databinding.ProActiveSuccActivityBinding;
import com.docker.common.common.config.Constant;
import com.docker.common.common.config.GlideApp;
import com.docker.common.common.config.ThiredPartConfig;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.utils.tool.PhotoGalleryUtils;
import com.docker.common.common.vm.NitEmptyViewModel;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.XPopupCallback;

@Route(path = AppRouter.ACTIVE_SUCC)
public class ActiveSuccActivity extends NitCommonActivity<NitEmptyViewModel, ProActiveSuccActivityBinding> {

    public ActiveSucVo activeSucVo;

    public String activityid;
    public String activitytitle;

    private BasePopupView basePopupView;

    public String flag; // 0 不可以  1 可以


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activeSucVo = (ActiveSucVo) getIntent().getSerializableExtra("ActiveSucVo");
        activityid = (String) getIntent().getStringExtra("activityid");
        activitytitle = (String) getIntent().getStringExtra("activitytitle");
        flag = String.valueOf(activeSucVo.signStatus);
        if ("0".equals(flag)) {
            mBinding.llNoValutify.setVisibility(View.VISIBLE);
            mBinding.llValutify.setVisibility(View.GONE);
        } else {
            mBinding.llValutify.setVisibility(View.VISIBLE);
            mBinding.llNoValutify.setVisibility(View.GONE);
        }

        mBinding.tvBack.setOnClickListener(v -> {
            ARouter.getInstance().build(AppRouter.ACTIVE_DEATIL_ACTIVITY).withString("activityid", activityid).navigation();
        });

    }

    @Override
    public void initView() {
        mToolbar.setTitle("报名成功");

        mBinding.tvLookCard.setOnClickListener(v -> {
            // dialog
            CardActivePopup centerPopup = new CardActivePopup(this);
            basePopupView = new XPopup.Builder(this).setPopupCallback(new XPopupCallback() {
                @Override
                public void onCreated() {
                    TextView textView = basePopupView.findViewById(R.id.tv_pzh);
                    TextView title = basePopupView.findViewById(R.id.tv_title);
                    title.setText(activitytitle);
                    textView.setText("凭证号：" + activeSucVo.evoucherNo);
                    ImageView imageView = basePopupView.findViewById(R.id.iv_bar_code);
                    GlideApp.with(imageView).load(ThiredPartConfig.BarcoderUrl + activeSucVo.evoucherNo).into(imageView);
                    imageView.setOnLongClickListener(v1 -> {
                        PhotoGalleryUtils.saveImageToGallery(ActiveSuccActivity.this, ImageUtils.view2Bitmap(imageView), Constant.BaseFileFloder, "ccc");
                        ToastUtils.showShort("保存成功");
                        return true;
                    });
                }

                @Override
                public void beforeShow() {

                }

                @Override
                public void onShow() {

                }

                @Override
                public void onDismiss() {

                }

                @Override
                public boolean onBackPressed() {
                    return false;
                }
            }).asCustom(centerPopup).show();

        });

        mBinding.tvBack.setOnClickListener(v -> finish());

        mBinding.tvActiveMine.setOnClickListener(v -> {
            // 查看自己的活动
            ARouter.getInstance().build(AppRouter.ACTIVE_MANAGER_LIST).navigation();
            finish();
        });

        mBinding.tvActiveOther.setOnClickListener(v -> {
            // 查看别人的活动
            ARouter.getInstance().build(AppRouter.ACTIVE_INDEX).navigation();
            finish();
        });
    }

    @Override
    public void initObserver() {

    }

    @Override
    public void initRouter() {
        ARouter.getInstance().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pro_active_succ_activity;
    }

    @Override
    public NitEmptyViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(NitEmptyViewModel.class);
    }

}

