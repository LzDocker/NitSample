package com.docker.active.ui.publish;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.ScreenUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.active.R;
import com.docker.active.databinding.ProActiveContentEditActivityBinding;
import com.docker.cirlev2.ui.common.CircleCoverActivity;
import com.docker.cirlev2.vm.PublishViewModel;
import com.docker.cirlev2.vo.param.SourceCoverParam;
import com.docker.cirlev2.widget.ColorPickerDialog;
import com.docker.common.common.binding.CommonBdUtils;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.utils.SoftKeyBroadManager;
import com.luck.picture.lib.permissions.RxPermissions;

import java.io.File;
import java.util.ArrayList;

import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnCompressListener;

@Route(path = AppRouter.ACTIVE_CONTENT_EDIT)
public class ActiveContentEditActivity extends NitCommonActivity<PublishViewModel, ProActiveContentEditActivityBinding> {
    public SourceCoverParam mSourceCoverParam;

    private String content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        content = getIntent().getStringExtra("data");
        super.onCreate(savedInstanceState);
        if (!TextUtils.isEmpty(content)) {
            mBinding.richEditor.setHtml(content);
        }
    }

    @Override
    public void initView() {
        mToolbar.setTitle("编辑活动");
        mToolbar.setTvRight("完成", v -> {
            Intent intent = new Intent();
            String content = mBinding.richEditor.getHtml();
            intent.putExtra("data", content);
            setResult(RESULT_OK, intent);
            finish();
        });

        SoftKeyBroadManager softKeyBroadManager = new SoftKeyBroadManager(mBinding.root);
        softKeyBroadManager.addSoftKeyboardStateListener(softKeyboardStateListener);

        mBinding.richEditor.setPadding(5, 5, 5, 5);
        mBinding.richEditor.focusEditor();
        mBinding.richEditor.setPlaceholder("请输入活动内容");

        mBinding.imageView1.setOnClickListener(v -> {
            mBinding.richEditor.undo();
        });
        mBinding.imageView2.setOnClickListener(v -> {
            mBinding.richEditor.redo();
        });
        mBinding.imageView3.setOnClickListener(v -> {
            if (mBinding.imageView3.isSelected()) {
                mBinding.imageView3.setSelected(false);
            } else {
                mBinding.imageView3.setSelected(true);
            }
            mBinding.richEditor.setBold();//粗体
        });

        mBinding.imageView4.setOnClickListener(v -> {
            if (mBinding.imageView4.isSelected()) {
                mBinding.imageView4.setSelected(false);
            } else {
                mBinding.imageView4.setSelected(true);
            }
            mBinding.richEditor.setItalic();//斜体
        });

        mBinding.imageView5.setOnClickListener(v -> {

            if (mBinding.imageView5.isSelected()) {
                mBinding.imageView5.setSelected(false);
            } else {
                mBinding.imageView5.setSelected(true);
            }
            mBinding.richEditor.setStrikeThrough();//横线
        });
        mBinding.imageView6.setOnClickListener(v -> {
            mBinding.richEditor.redo();

            if (mBinding.imageView6.isSelected()) {
                mBinding.imageView6.setSelected(false);
            } else {
                mBinding.imageView6.setSelected(true);
            }
            mBinding.richEditor.setUnderline();//下划线
        });
        mBinding.imageView7.setOnClickListener(v -> {

            mBinding.richEditor.setIndent();
        });
        mBinding.imageView8.setOnClickListener(v -> {
            mBinding.richEditor.setOutdent();
        });
        mBinding.imageView9.setOnClickListener(v -> {
            mBinding.richEditor.setBullets();
        });
        mBinding.imageView10.setOnClickListener(v -> {
            mBinding.richEditor.setNumbers();
        });
        mBinding.imageView11.setOnClickListener(v -> {
            mBinding.richEditor.setAlignLeft();
        });
        mBinding.imageView12.setOnClickListener(v -> {
            mBinding.richEditor.setAlignCenter();
        });
        mBinding.imageView13.setOnClickListener(v -> {
            mBinding.richEditor.setAlignRight();
        });
        mBinding.imageView14.setOnClickListener(v -> {
            mBinding.richEditor.setFontSize(6);
        });
        mBinding.imageView15.setOnClickListener(v -> {
            mBinding.richEditor.setFontSize(12);
        });
        mBinding.imageView16.setOnClickListener(v -> {
            mBinding.richEditor.setFontSize(18);
        });
        mBinding.imageView17.setOnClickListener(v -> {
            mBinding.richEditor.setFontSize(24);
        });
        mBinding.imageView18.setOnClickListener(v -> {
            mBinding.richEditor.setFontSize(30);
        });
        mBinding.imageView19.setOnClickListener(v -> {
            mBinding.richEditor.setFontSize(36);
        });
        mBinding.imageView20.setOnClickListener(v -> {
            ColorPickerDialog dialog = new ColorPickerDialog(this, Color.BLACK, "选择文字颜色",
                    color -> mBinding.richEditor.setTextColor(color));
            dialog.setTitle("选择文字颜色");
            dialog.show();
        });
        mBinding.imageView21.setOnClickListener(v -> {
            ColorPickerDialog dialog = new ColorPickerDialog(this, Color.WHITE, "选择文字颜色",
                    color -> mBinding.richEditor.setTextColor(color));
            dialog.setTitle("选择文字背景颜色");
            dialog.show();
        });
        mBinding.imageView22.setOnClickListener(v -> {
            applyPerssion();
        });
    }

    private void applyPerssion() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .subscribe(granted -> {
                    if (granted) {
                        mSourceCoverParam = new SourceCoverParam(SourceCoverParam.UI_TYPE_TOP_SHOW, 1, new ArrayList<>());
                        mSourceCoverParam.needCrop = true;
                        mSourceCoverParam.width = ScreenUtils.getScreenWidth();
                        CircleCoverActivity.startMeForResult(this, mSourceCoverParam, 101);
                    } else {
                        ToastUtils.showShort("权限被拒绝，请在设置里面开启相应权限，若无相应权限会影响使用");
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 101) {
                mSourceCoverParam = (SourceCoverParam) data.getSerializableExtra("sourceCoverParam");
                Luban.compress(this, new File(mSourceCoverParam.getImgList().get(0)))       // 初始化Luban，并传入要压缩的图片
                        .setMaxHeight(480)             // 限制图片高度
                        .setMaxWidth(480)
                        .putGear(Luban.CUSTOM_GEAR)      // 设定压缩模式，默认 THIRD_GEAR
                        .launch(new OnCompressListener() {
                            @Override
                            public void onStart() {
                            }

                            @Override
                            public void onSuccess(File file) {
                                mViewModel.publishImgToserver(file);
                            }

                            @Override
                            public void onError(Throwable e) {
                            }
                        });
            }
        }
    }

    SoftKeyBroadManager.SoftKeyboardStateListener softKeyboardStateListener = new SoftKeyBroadManager.SoftKeyboardStateListener() {
        @Override
        public void onSoftKeyboardOpened(int keyboardHeightInPx) {
            mBinding.horizontalScrollView.requestLayout();
        }

        @Override
        public void onSoftKeyboardClosed() {
            mBinding.horizontalScrollView.requestLayout();
        }
    };

    @Override
    public void initObserver() {
        mViewModel.mImageUploadLV.observe(this, s -> {
            mBinding.richEditor.insertImage(CommonBdUtils.getServerImageUrl(s), "");
        });
    }

    @Override
    public void initRouter() {
        ARouter.getInstance().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pro_active_content_edit_activity;
    }

    @Override
    public PublishViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(PublishViewModel.class);
    }

}

