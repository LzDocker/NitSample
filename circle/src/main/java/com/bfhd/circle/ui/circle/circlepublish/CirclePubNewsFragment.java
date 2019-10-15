package com.bfhd.circle.ui.circle.circlepublish;

import android.Manifest;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.bfhd.circle.R;
import com.bfhd.circle.base.CommonFragment;
import com.bfhd.circle.base.Constant;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.databinding.CircleFragmentCirclePubNewsBinding;
import com.bfhd.circle.ui.circle.CircleCoverActivity;
import com.bfhd.circle.ui.circle.CirclePerssionSelectActivity;
import com.bfhd.circle.ui.circle.CirclePublishActivity;
import com.bfhd.circle.ui.circle.CircleShareSelectActivity;
import com.bfhd.circle.vm.CirclePublishViewModel;
import com.bfhd.circle.vo.PublishImgSpeicalVo;
import com.bfhd.circle.vo.ServiceDataBean;
import com.bfhd.circle.vo.bean.SourceCoverParam;
import com.bfhd.circle.vo.bean.StaCirParam;
import com.bfhd.circle.widget.ColorPickerDialog;
import com.dcbfhd.utilcode.utils.KeyboardUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vo.UserInfoVo;
import com.luck.picture.lib.permissions.RxPermissions;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnCompressListener;

import static android.app.Activity.RESULT_OK;

/*
 * 新闻类型的动态
 * */
public class CirclePubNewsFragment extends CommonFragment<CirclePublishViewModel, CircleFragmentCirclePubNewsBinding> {


    @Inject
    ViewModelProvider.Factory factory;

    public SourceCoverParam mSourceCoverParam;

    private Disposable disposable;

//    CommonDialog commonDialog;

    /*
     *
     * 选择圈子 选择 分类 子栏目
     *
     * extenMap size 来区分选了多少级
     *
     * */
    private StaCirParam mHandParam; // 接收到的分组数据

//    private StaCirParam mPerssionParam; // 权限数据

    private int mEditType = 1; // 1 发布 2 编辑

    public StaCirParam mStartParam;

    @Override
    protected int getLayoutId() {
        return R.layout.circle_fragment_circle_pub_news;
    }


    public static CirclePubNewsFragment newInstance() {
        CirclePubNewsFragment detailFragment = new CirclePubNewsFragment();
        return detailFragment;
    }

    @Override
    protected CirclePublishViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(CirclePublishViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.get().setViewmodel(mViewModel);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mHandParam = ((CirclePublishActivity) getHoldingActivity()).getmStartParam();
        mEditType = ((CirclePublishActivity) getHoldingActivity()).getmEditType();

        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
            if (rxEvent.getT().equals("GroupSelect")) {
                mHandParam = (StaCirParam) rxEvent.getR();
                processStaparam();
            }
            if (rxEvent.getT().equals("circle_perrsion_update")) { // 权限
                mHandParam = (StaCirParam) rxEvent.getR();
                if (mHandParam != null) {
                    mBinding.get().tvPerssionSelect.setText(mHandParam.extentron2);
                }
            }
        });


        if (mEditType == 2) {
            mHandParam = processStaParam(mHandParam.serviceDataBean);
        }
        processStaparam();
    }


    public void processStaparam() {

        if (mHandParam != null) {
            mBinding.get().circleSelect.setVisibility(View.VISIBLE);
            if(!TextUtils.isEmpty(mHandParam.getCircleid())){
                mBinding.get().perssionSelect.setVisibility(View.VISIBLE);
            }

            if (TextUtils.isEmpty(mHandParam.extentron2)) {
                mBinding.get().tvPerssionSelect.setText("全部");
            } else {
                // 编辑逻辑后面再写
            }
            switch (mHandParam.getExtenMap().size()) { // 圈子 分类
                case 0:    // 没有一级栏目 只有圈子
                    mBinding.get().tvCircleSelect.setText(mHandParam.getExtentron());
                    break;
                case 2:   // 没有选择二级栏目
                    mBinding.get().tvCircleSelect.setText(mHandParam.getExtentron() + " / " + mHandParam.getExtenMap().get("classname1"));
                    break;
                case 4:  // 都有
                    mBinding.get().tvCircleSelect.setText(mHandParam.getExtentron() + " / " + mHandParam.getExtenMap().get("classname1") + " / " + mHandParam.getExtenMap().get("classname2"));
                    break;
            }
        }
    }

    private StaCirParam processStaParam(ServiceDataBean serviceDataBean) {
        mHandParam.getExtenMap().put("classid1", serviceDataBean.getClassid());
        mHandParam.getExtenMap().put("classname1", "");
        mHandParam.getExtenMap().put("classid2", serviceDataBean.getClassid2());
        mHandParam.getExtenMap().put("classname2", "");
        if (serviceDataBean.getExtData() != null) {
            mBinding.get().richEditor.setHtml(serviceDataBean.getExtData().getContent());
        }
        if(!TextUtils.isEmpty(serviceDataBean.getExtData().getTitle())){
            mBinding.get().circleEditName.setText(serviceDataBean.getExtData().getTitle());
        }
        return mHandParam;
    }


    @Override
    public void initImmersionBar() {

    }


    @Override
    protected void initView(View var1) {

//        commonDialog = CommonDialog.newInstance();
        mBinding.get().checkIsRelay.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mBinding.get().perssionSelect.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            mBinding.get().circleSelect.setVisibility(isChecked ? View.VISIBLE : View.GONE);
//            mBinding.get().circleSelect.setVisibility(View.GONE);
        });
        mBinding.get().perssionSelect.setOnClickListener(v -> {
            if (mHandParam == null) {
                ToastUtils.showShort("请先选择要同步的圈子");
                return;
            }
            CirclePerssionSelectActivity.startMe(CirclePubNewsFragment.this.getHoldingActivity(), mHandParam);
        });
        mBinding.get().circleSelect.setOnClickListener(v -> {
            CircleShareSelectActivity.startMe(CirclePubNewsFragment.this.getHoldingActivity(), mHandParam);
        });
        mBinding.get().richEditor.setPadding(5, 5, 5, 5);
        mBinding.get().richEditor.setPlaceholder("请输入新闻内容");
        mBinding.get().circleEditName.setOnFocusChangeListener((v, hasFocus) -> {
            if (mBinding != null && mBinding.get() != null && mBinding.get().horizontalScrollView != null) {
                if (hasFocus) {
                    mBinding.get().horizontalScrollView.setVisibility(View.GONE);
                } else {
                    mBinding.get().horizontalScrollView.setVisibility(View.VISIBLE);
                }
            }
        });

        KeyboardUtils.registerSoftInputChangedListener(CirclePubNewsFragment.this.getHoldingActivity(), new KeyboardUtils.OnSoftInputChangedListener() {
            @Override
            public void onSoftInputChanged(int height) {
                Log.d("sss", "onSoftInputChanged: he" + height);
                if (height > 0) {
                    mBinding.get().llSelsectCoutainer.setVisibility(View.GONE);
                } else {
                    mBinding.get().llSelsectCoutainer.setVisibility(View.VISIBLE);
                }
            }
        });

        mBinding.get().imageView1.setOnClickListener(v -> {
            mBinding.get().richEditor.undo();
        });
        mBinding.get().imageView2.setOnClickListener(v -> {
            mBinding.get().richEditor.redo();
        });
        mBinding.get().imageView3.setOnClickListener(v -> {
            if (mBinding.get().imageView3.isSelected()) {
                mBinding.get().imageView3.setSelected(false);
            } else {
                mBinding.get().imageView3.setSelected(true);
            }
            mBinding.get().richEditor.setBold();//粗体
        });

        mBinding.get().imageView4.setOnClickListener(v -> {
            if (mBinding.get().imageView4.isSelected()) {
                mBinding.get().imageView4.setSelected(false);
            } else {
                mBinding.get().imageView4.setSelected(true);
            }
            mBinding.get().richEditor.setItalic();//斜体
        });

        mBinding.get().imageView5.setOnClickListener(v -> {

            if (mBinding.get().imageView5.isSelected()) {
                mBinding.get().imageView5.setSelected(false);
            } else {
                mBinding.get().imageView5.setSelected(true);
            }
            mBinding.get().richEditor.setStrikeThrough();//横线
        });
        mBinding.get().imageView6.setOnClickListener(v -> {
            mBinding.get().richEditor.redo();

            if (mBinding.get().imageView6.isSelected()) {
                mBinding.get().imageView6.setSelected(false);
            } else {
                mBinding.get().imageView6.setSelected(true);
            }
            mBinding.get().richEditor.setUnderline();//下划线
        });
        mBinding.get().imageView7.setOnClickListener(v -> {

            mBinding.get().richEditor.setIndent();
        });
        mBinding.get().imageView8.setOnClickListener(v -> {
            mBinding.get().richEditor.setOutdent();
        });
        mBinding.get().imageView9.setOnClickListener(v -> {
            mBinding.get().richEditor.setBullets();
        });
        mBinding.get().imageView10.setOnClickListener(v -> {
            mBinding.get().richEditor.setNumbers();
        });
        mBinding.get().imageView11.setOnClickListener(v -> {
            mBinding.get().richEditor.setAlignLeft();
        });
        mBinding.get().imageView12.setOnClickListener(v -> {
            mBinding.get().richEditor.setAlignCenter();
        });
        mBinding.get().imageView13.setOnClickListener(v -> {
            mBinding.get().richEditor.setAlignRight();
        });
        mBinding.get().imageView14.setOnClickListener(v -> {
            mBinding.get().richEditor.setFontSize(6);
        });
        mBinding.get().imageView15.setOnClickListener(v -> {
            mBinding.get().richEditor.setFontSize(12);
        });
        mBinding.get().imageView16.setOnClickListener(v -> {
            mBinding.get().richEditor.setFontSize(18);
        });
        mBinding.get().imageView17.setOnClickListener(v -> {
            mBinding.get().richEditor.setFontSize(24);
        });
        mBinding.get().imageView18.setOnClickListener(v -> {
            mBinding.get().richEditor.setFontSize(30);
        });
        mBinding.get().imageView19.setOnClickListener(v -> {
            mBinding.get().richEditor.setFontSize(36);
        });
        mBinding.get().imageView20.setOnClickListener(v -> {
            ColorPickerDialog dialog = new ColorPickerDialog(CirclePubNewsFragment.this.getHoldingActivity(), Color.BLACK, "选择文字颜色",
                    color -> mBinding.get().richEditor.setTextColor(color));
            dialog.setTitle("选择文字颜色");
            dialog.show();
        });
        mBinding.get().imageView21.setOnClickListener(v -> {
            ColorPickerDialog dialog = new ColorPickerDialog(CirclePubNewsFragment.this.getHoldingActivity(), Color.WHITE, "选择文字颜色",
                    color -> mBinding.get().richEditor.setTextColor(color));
            dialog.setTitle("选择文字背景颜色");
            dialog.show();
        });
        mBinding.get().imageView22.setOnClickListener(v -> {
            applyPerssion();
        });

    }


    private void applyPerssion() {
        /*
        *  Manifest.permission.ACCESS_COARSE_LOCATION,
           Manifest.permission.ACCESS_FINE_LOCATION,
           Manifest.permission.READ_PHONE_STATE
        * */
        RxPermissions rxPermissions = new RxPermissions(this.getHoldingActivity());
        rxPermissions
                .request(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .subscribe(granted -> {
                    if (granted) {
                        mSourceCoverParam = new SourceCoverParam(SourceCoverParam.UI_TYPE_TOP_SHOW, 1, new ArrayList<>());
                        mSourceCoverParam.needCrop = true;
                        CircleCoverActivity.startMeForResult(CirclePubNewsFragment.this.getHoldingActivity(), mSourceCoverParam, 101);
                    } else {
                        ToastUtils.showShort("权限被拒绝，请在设置里面开启相应权限，若无相应权限会影响使用");
                    }

//                    if (granted) {
//                        if (mSourceCoverParam == null) {
//                            mSourceCoverParam = new SourceCoverParam(SourceCoverParam.UI_TYPE_TOP_SHOW, 9, new ArrayList<>());
//                        }
//                        CircleCoverActivity.startMeForResult(CirclePubNewsFragment.this.getHoldingActivity(), mSourceCoverParam, 101);
//                    } else {
//                        ToastUtils.showShort("权限被拒绝，请在设置里面开启相应权限，若无相应权限会影响使用");
//                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 101) {
                mSourceCoverParam = (SourceCoverParam) data.getSerializableExtra("sourceCoverParam");
                Luban.compress(this.getHoldingActivity(), new File(mSourceCoverParam.getImgList().get(0)))       // 初始化Luban，并传入要压缩的图片
                        .setMaxHeight(680)             // 限制图片高度
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

//            if (requestCode == 101) {
//                mSourceCoverParam = (SourceCoverParam) data.getSerializableExtra("sourceCoverParam");
//                this.mSourceCoverParam = mSourceCoverParam;
//                mBinding.get().richEditor.insertImage(mSourceCoverParam.getImgList().get(0), "");
//            }

    }


    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);

        switch (viewEventResouce.eventType) {
            case 103:  // 上传完成插入编辑器
                if (viewEventResouce.data != null && "0".equals(((PublishImgSpeicalVo) viewEventResouce.data).errno)) {
                    mBinding.get().richEditor.insertImage(Constant.getCompleteImageUrl(((PublishImgSpeicalVo) viewEventResouce.data).url), "");
                }
                break;

            case 104:
                ToastUtils.showShort("发布成功");
                RxBus.getDefault().post(new RxEvent<>("dynamic_refresh", ""));
                getHoldingActivity().finish();
                break;
        }
    }


    //
    public String getTitle() {
        return mBinding.get().circleEditName.getText().toString().trim();
    }

    public String getHtml() {
        if (TextUtils.isEmpty(mBinding.get().richEditor.getHtml())) {
            return "";
        } else {
            return mBinding.get().richEditor.getHtml().trim();
        }
    }


    // 发布
    public void publish() {

        if (TextUtils.isEmpty(mBinding.get().circleEditName.getText().toString().trim())) {
            ToastUtils.showShort("请输入动态标题");
            return;
        }
        if (TextUtils.isEmpty(mBinding.get().richEditor.getHtml())) {
            ToastUtils.showShort("请输入动态内容");
            return;
        }
        if (TextUtils.isEmpty(mHandParam.getCircleid()) || TextUtils.isEmpty(mHandParam.getUtid())) {
            ToastUtils.showShort("请选择要发布的圈子");
            return;
        }
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> paramMap = new HashMap();
        paramMap.put("memberid", userInfoVo.uid);
        paramMap.put("uuid", userInfoVo.uuid);
        paramMap.put("type", "news");
        paramMap.put("title", mBinding.get().circleEditName.getText().toString().trim());
        paramMap.put("content", mBinding.get().richEditor.getHtml());
        paramMap.put("circleid", mHandParam.getCircleid());
        paramMap.put("utid", mHandParam.getUtid());
        if (!TextUtils.isEmpty(mHandParam.getExtenMap().get("classid1"))) {
            paramMap.put("classid", mHandParam.getExtenMap().get("classid1"));
        }
        if (!TextUtils.isEmpty(mHandParam.getExtenMap().get("classid2"))) {
            paramMap.put("classid2", mHandParam.getExtenMap().get("classid2"));
        }

        if (TextUtils.isEmpty(mHandParam.extentron2) && mHandParam.extentronList.size() == 0) {
            paramMap.put("visibleType", "0");   // extentronList
        } else {
            paramMap.put("visibleType", "1");

            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < mHandParam.extentronList.size(); i++) {
                String id = (String) mHandParam.extentronList.get(i);
                stringBuilder.append(id).append(",");
            }

            if(stringBuilder.length()>1){
                String ids = stringBuilder.substring(0, stringBuilder.length() - 1);
                paramMap.put("groupids", ids);
            }

        }
        if (mEditType == 2) {
            paramMap.put("dataid", mHandParam.serviceDataBean.getDataid());
        }

        mViewModel.publishNews(paramMap);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
