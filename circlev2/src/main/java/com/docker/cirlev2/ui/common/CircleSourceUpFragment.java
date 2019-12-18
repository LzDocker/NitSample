package com.docker.cirlev2.ui.common;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.sdk.android.oss.OSS;
import com.bumptech.glide.Glide;
import com.dcbfhd.utilcode.utils.FileUtils;
import com.dcbfhd.utilcode.utils.LogUtils;
import com.dcbfhd.utilcode.utils.NetworkUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.cirlev2.R;
import com.docker.cirlev2.api.CircleApiService;
import com.docker.cirlev2.databinding.Circlev2FragmentSouceUpBinding;
import com.docker.cirlev2.util.GridIamgeAdapter;
import com.docker.cirlev2.util.mFileUtils;
import com.docker.cirlev2.vm.SampleListViewModel;
import com.docker.cirlev2.vo.entity.PublishImgSpeicalVo;
import com.docker.cirlev2.vo.param.ReleaseDyamicBean;
import com.docker.cirlev2.vo.param.SourceCoverParam;
import com.docker.cirlev2.vo.param.SourceUpParam;
import com.docker.common.common.binding.CommonBdUtils;
import com.docker.common.common.config.ThiredPartConfig;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.utils.oss.MyOSSUtils;
import com.docker.common.common.vm.NitEmptyVm;
import com.docker.core.base.BaseFragment;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.util.AppExecutors;
import com.docker.core.widget.BottomSheetDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;

/*
 *  资源上传 oss
 * */
public class CircleSourceUpFragment extends NitCommonFragment<SampleListViewModel, Circlev2FragmentSouceUpBinding> {


    @Inject
    AppExecutors appExecutors;

    @Inject
    ViewModelProvider.Factory factory;
    private GridIamgeAdapter gridIamgeAdapter;
    private BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
    public List<LocalMedia> selectList = new ArrayList<>();//已选中的图片
    private SourceUpParam mSourceUpParam;
    private SourceCoverParam mSourceCoverParam;
    private OSS oss;
    private ImageView mSingleImageView;

    @Override
    protected int getLayoutId() {
        return R.layout.circlev2_fragment_souce_up;
    }

    public static CircleSourceUpFragment newInstance(SourceUpParam sourceUpParam) {
        CircleSourceUpFragment sourceUpFragment = new CircleSourceUpFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("sourceparam", sourceUpParam);
        sourceUpFragment.setArguments(bundle);
        return sourceUpFragment;
    }


    @Override
    protected SampleListViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(SampleListViewModel.class);
    }

    @Override
    protected void initView(View var1) {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSourceUpParam = (SourceUpParam) getArguments().getSerializable("sourceparam");
        applyPerssion();
    }

    public void setmSingleImageView(ImageView imageView) {
        this.mSingleImageView = imageView;
    }

    @Override
    public void initImmersionBar() {

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
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .subscribe(granted -> {
                    if (granted) {
                        initPicView();
                    } else {
                        ToastUtils.showShort("权限被拒绝，请在设置里面开启相应权限，若无相应权限会影响使用");
                    }
                });
    }


    /*
     * 1  内部调用
     * 2  外部调用
     * */
    public void enterToSelect(int type) {
        RxPermissions rxPermissions = new RxPermissions(this.getHoldingActivity());
        rxPermissions
                .request(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .subscribe(granted -> {
                    if (granted) {
                        String[] titles = new String[]{"从本地选择", "图片库"};
                        bottomSheetDialog.setDataCallback(titles, position -> {
                            bottomSheetDialog.dismiss();
                            bottomSheetDialog.replaceData(titles);
                            switch (position) {
                                case 0:
                                    int max = mSourceUpParam.sourceMaxNum;
                                    if (mSourceCoverParam == null) {
                                        max = mSourceUpParam.sourceMaxNum;
                                    } else {
                                        if (mSingleImageView == null) {
                                            max = mSourceUpParam.sourceMaxNum - mSourceCoverParam.getImgList().size();
                                        }
                                    }
                                    if (mSingleImageView != null) {
                                        selectList.clear();
                                    }
                                    if (max == selectList.size()) {
                                        ToastUtils.showShort("最多选择" + mSourceUpParam.sourceMaxNum + "个文件");
                                        return;
                                    }

                    /*
                    *

    public static final int SOURCE_TYPE_IMG_ONLY = 101;   // 图片
    public static final int SOURCE_TYPE_VIDEO_ONLY = 102; // 视频
    public static final int SOURCE_TYPE_AUTO = 103;    // 两者可同时
    public static final int SOURCE_TYPE_ONE_TYPE = 104; // 只能一种根据第一次选的类型决定之后的类型
                    * */

                                    int mimetype = PictureMimeType.ofAll();
                                    switch (mSourceUpParam.sourceType) {
                                        case SourceUpParam.SOURCE_TYPE_IMG_ONLY:
                                            mimetype = PictureMimeType.ofImage();
                                            break;
                                        case SourceUpParam.SOURCE_TYPE_AUTO:
                                            mimetype = PictureMimeType.ofAll();
                                            break;
                                    }
//                                    // 进入相册 以下是例子：不需要的api可以不写
//                                    PictureSelector.create(CircleSourceUpFragment.this.getHoldingActivity())
//                                            .openGallery()// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
//                                            // 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
//                                            .maxSelectNum(max)// 最大图片选择数量
//                                            .minSelectNum(1)// 最小选择数量
//                                            .imageSpanCount(3)// 每行显示个数
//                                            .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
//                                            .previewImage(true)// 是否可预览图片
//                                            .previewVideo(true)// 是否可预览视频
//                                            .enablePreviewAudio(false) // 是否可播放音频
//                                            .isCamera(true)// 是否显示拍照按钮
//                                            .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
//                                            .enableCrop(false)// 是否裁剪
//                                            .compress(false)// 是否压缩
//                                            .synOrAsy(true)//同步true或异步false 压缩 默认同步
//                                            .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                                            .isGif(true)// 是否显示gif图片
//                                            .selectionMedia(selectList)// 是否传入已选图片
//                                            .minimumCompressSize(100)// 小于100kb的图片不压缩
//                                            .scaleEnabled(true)// 裁剪是否可放大缩小图片
//                                            .videoMaxSecond(15)//显示多少秒以内的视频or音频也可适用
//                                            .recordVideoSecond(15)//录制视频秒数 默认60s
//                                            .forResult(mSourceUpParam.resultcode_local);//结果回调onActivityResult code
////                                            .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code


                                    PictureSelector.create(CircleSourceUpFragment.this.getHoldingActivity())
                                            .openGallery(PictureMimeType.ofAll())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                                            .theme(R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                                            .maxSelectNum(max)// 最大图片选择数量 int
                                            .minSelectNum(1)// 最小选择数量 int
                                            .imageSpanCount(4)// 每行显示个数 int
                                            .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                                            .previewImage(true)// 是否可预览图片 true or false
                                            .previewVideo(true)// 是否可预览视频 true or false
                                            .enablePreviewAudio(true) // 是否可播放音频 true or false
                                            .isCamera(true)// 是否显示拍照按钮 true or false
                                            .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                                            .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                                            .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                                            .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
//                                            .enableCrop()// 是否裁剪 true or false
                                            .compress(true)// 是否压缩 true or false
                                            .glideOverride(160, 160)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                                            .withAspectRatio()// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                                            .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示 true or false
                                            .isGif(true)// 是否显示gif图片 true or false
//                                            .compressSavePath()//压缩图片保存地址
                                            .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                                            .circleDimmedLayer(true)// 是否圆形裁剪 true or false
                                            .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                                            .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                                            .openClickSound(true)// 是否开启点击声音 true or false
                                            .selectionMedia(selectList)// 是否传入已选图片 List<LocalMedia> list
                                            .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                                            .cropCompressQuality(90)// 裁剪压缩质量 默认90 int
                                            .minimumCompressSize(100)// 小于100kb的图片不压缩
                                            .synOrAsy(true)//同步true或异步false 压缩 默认同步
//                                            .cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                                            .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                                            .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                                            .videoQuality(1)// 视频录制质量 0 or 1 int
                                            .videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
                                            .videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
                                            .recordVideoSecond(15)//视频秒数录制 默认60s int
                                            .isDragFrame(false)// 是否可拖动裁剪框(固定)
                                            .forResult(mSourceUpParam.resultcode_local);//结果回调onActivityResult code


                                    break;
                                case 1:
                                    if (mSourceCoverParam == null) {
                                        mSourceCoverParam = new SourceCoverParam(SourceCoverParam.UI_TYPE_TOP_HIDEN, mSourceUpParam.sourceMaxNum - selectList.size(), new ArrayList<>());
                                        mSourceCoverParam.needCrop = mSourceUpParam.needCrop;
                                        mSourceCoverParam.width = mSourceUpParam.width;
                                        mSourceCoverParam.height = mSourceUpParam.height;
                                    } else {
                                        if (mSingleImageView != null) {
                                            selectList.clear();
                                            mSourceCoverParam.sourceMaxNum = 1;
                                        } else {
                                            mSourceCoverParam.sourceMaxNum = mSourceUpParam.sourceMaxNum - selectList.size();
                                        }
                                    }
                                    if (mSingleImageView != null) {
                                        selectList.clear();
                                        mSourceCoverParam.sourceMaxNum = 1;
                                        mSourceCoverParam.getImgList().clear();
                                    }
                                    if (mSourceCoverParam.sourceMaxNum <= 0) {
                                        ToastUtils.showShort("最多选择" + mSourceUpParam.sourceMaxNum + "个文件");
                                        return;
                                    }
                                    CircleCoverActivity.startMeForResult(this.getHoldingActivity(), mSourceCoverParam, mSourceUpParam.resultcode_net);
                                    break;
                            }
                        });
                        if (gridIamgeAdapter == null) {
                            initPicView();
                        }
                        gridIamgeAdapter.setSelectMax(mSourceUpParam.sourceMaxNum);
                        bottomSheetDialog.show(CircleSourceUpFragment.this.getHoldingActivity());
                    } else {
                        ToastUtils.showShort("权限被拒绝，请在设置里面开启相应权限，若无相应权限会影响使用");
                        return;
                    }
                });
    }

    private void initPicView() {
        gridIamgeAdapter = new GridIamgeAdapter(CircleSourceUpFragment.this.getHoldingActivity(), () -> {
            enterToSelect(1);
        });
        if (mSourceUpParam.sourceMaxNum > 4) {
            mBinding.get().recycle.setLayoutManager(new GridLayoutManager(this.getHoldingActivity(), 4));
        } else {
            mBinding.get().recycle.setLayoutManager(new GridLayoutManager(this.getHoldingActivity(), 1));
        }
        mBinding.get().recycle.setAdapter(gridIamgeAdapter);
        if (selectList.size() > 0) {
            gridIamgeAdapter.setList(selectList);
            gridIamgeAdapter.notifyDataSetChanged();
        }
        gridIamgeAdapter.setOnItemClickListener((position, v) -> {
            if (selectList.size() > 0) {
                LocalMedia media = selectList.get(position);
                String pictureType = media.getPictureType();
                int mediaType = PictureMimeType.pictureToVideo(pictureType);
                switch (mediaType) {
                    case 1:
                        // 预览图片 可自定长按保存路径
//                        PictureSelector.create(this).externalPicturePreview(position, selectList);

                        PictureSelector
                                .create(this)
                                .themeStyle(R.style.picture_default_style)
                                .openExternalPreview(position, selectList);
                        break;
                    case 2:
                        // 预览视频
                        if (!media.getPath().startsWith(ThiredPartConfig.BaseImageUrl)) {
                            PictureSelector.create(this).externalPictureVideo(media.getPath());

                        } else {
//                            Intent intent = new Intent(this, VideoPlayerActivity.class);
//                            intent.putExtra("videoUrl", media.getPath().substring(media.getPath().lastIndexOf(Constant.mBaseImageUrl)));
//                            intent.putExtra("imageUrl", media.getPath().substring(0, media.getPath().lastIndexOf(Constant.mBaseImageUrl)));
//                            startActivity(intent);
                        }
                        break;
                    case 3:
                        // 预览音频
                        PictureSelector.create(this).externalPictureAudio(media.getPath());
                        break;
                }
            }
        });
    }

    public UCrop.Options options;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == mSourceUpParam.resultcode_local) {
                // 图片选择结果回调
                selectList = PictureSelector.obtainMultipleResult(data);
                if (mSingleImageView != null) {
                    // 去剪裁
                    Uri sourceUri = Uri.fromFile(new File(selectList.get(0).getPath()));
                    String cropName = System.currentTimeMillis() + ".jpg";
                    Uri dstUri = Uri.fromFile(new File(getHoldingActivity().getCacheDir(), cropName));
                    UCrop uCrop = UCrop.of(sourceUri, dstUri);
                    if (options != null) {
                        uCrop.withOptions(options).start(CircleSourceUpFragment.this.getHoldingActivity());
                    } else {
                        UCrop.Options options = new UCrop.Options();
                        options.setToolbarColor(getHoldingActivity().getResources().getColor(R.color.colorPrimary));
                        options.setToolbarTitle("图片剪裁");
                        options.setStatusBarColor(getHoldingActivity().getResources().getColor(R.color.colorPrimary));
                        options.setFreeStyleCropEnabled(true);
                        options.withMaxResultSize(mSourceUpParam.width, mSourceUpParam.height);
                        uCrop.withOptions(options).start(CircleSourceUpFragment.this.getHoldingActivity());
                    }

                } else {
                    for (LocalMedia media : selectList) {
                        switch (PictureMimeType.pictureToVideo(media.getPictureType())) {
                            case 1:
                                LogUtils.e("图片-----》", media.getCompressPath() + " " + media.getWidth() + " " + media.getHeight());
                                break;
                            case 2:
                                LogUtils.e("视频-----》", media.getPath());
                                break;
                            case 3:
                                // 预览音频
                                LogUtils.e("音频-----》", media.getPath());
                                break;
                        }
                    }
                    gridIamgeAdapter.setList(selectList);
                    gridIamgeAdapter.notifyDataSetChanged();
                }
            } else if (requestCode == mSourceUpParam.resultcode_net) {
                this.mSourceCoverParam = (SourceCoverParam) data.getSerializableExtra("sourceCoverParam");
                if (mSingleImageView != null) {
                    LocalMedia media = new LocalMedia();
                    media.setPath(mSourceCoverParam.getImgList().get(mSourceCoverParam.getImgList().size() - 1));
                    media.setPictureType("image/jpeg");
                    media.setMimeType(PictureMimeType.ofAll());
                    media.setWidth(1);
                    media.setHeight(1);
                    selectList.add(media);
                    processUpload();
                } else {
                    for (int i = 0; i < mSourceCoverParam.getImgList().size(); i++) {
                        LocalMedia media = new LocalMedia();
                        media.setPath(mSourceCoverParam.getImgList().get(i));
                        media.setPictureType("image/jpeg");
                        media.setMimeType(PictureMimeType.ofAll());
                        media.setWidth(1);
                        media.setHeight(1);
                        selectList.add(media);
                    }
                    mSourceCoverParam.getImgList().clear();
                    gridIamgeAdapter.setList(selectList);
                    gridIamgeAdapter.notifyDataSetChanged();
                }
            } else if (requestCode == UCrop.REQUEST_CROP) {
                final Uri resultUri = UCrop.getOutput(data);
                Glide.with(this.getHoldingActivity()).load(resultUri.getPath()).into(mSingleImageView);
                selectList.clear();
                LocalMedia media = new LocalMedia();
                media.setPath(resultUri.getPath());
                media.setPictureType("image/jpeg");
                media.setMimeType(PictureMimeType.ofAll());
                media.setWidth(1);
                media.setHeight(1);
                selectList.add(media);
                processUpload();
            } else if (requestCode == UCrop.RESULT_ERROR) {
                final Throwable cropError = UCrop.getError(data);
                ToastUtils.showShort("剪裁出错了，请重试");
            }
        }
    }

    public void processUpload() {
        showWaitDialog("上传中...");
        appExecutors.diskIO().execute(() -> {
            if (selectList.size() > 0) {
                List<ReleaseDyamicBean> releaseDyamicBeanList = new ArrayList<>();
                for (int i = 0; i < selectList.size(); i++) {
                    LocalMedia localMedia = selectList.get(i);
                    ReleaseDyamicBean releaseDyamicBean = new ReleaseDyamicBean();
                    switch (PictureMimeType.pictureToVideo(localMedia.getPictureType())) {
                        case 1:
                            LogUtils.e("图片-----》");
                            releaseDyamicBean.setT("1");
//                            releaseDyamicBean.setImg(getImageUrl());
                            if (!TextUtils.isEmpty(localMedia.getCompressPath())) {
                                releaseDyamicBean.setImgPath(localMedia.getCompressPath());
                            } else {
                                releaseDyamicBean.setImgPath(localMedia.getPath());
                            }
                            releaseDyamicBean.setSort(i);
                            break;
                        case 2:
                            LogUtils.e("视频-----》");
                            releaseDyamicBean.setT("2");
                            releaseDyamicBean.setVideoUrl(localMedia.getPath().substring(localMedia.getPath().lastIndexOf(ThiredPartConfig.BaseImageUrl) + ThiredPartConfig.BaseImageUrl.length()));
                            releaseDyamicBean.setLocVideoPath(localMedia.getPath());
                            releaseDyamicBean.duration = localMedia.getDuration();
                            try {
                                MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                                if (Build.VERSION.SDK_INT >= 14) {
                                    mmr.setDataSource(localMedia.getPath(), new HashMap<String, String>());
                                } else {
                                    mmr.setDataSource(localMedia.getPath());
                                }
                                Bitmap bitmap = mmr.getFrameAtTime((long) (1) * 1000 * 1000, MediaMetadataRetriever.OPTION_CLOSEST);
                                String vfname = String.valueOf(System.currentTimeMillis()) + "video" + i;
                                if (bitmap != null) {
                                    mFileUtils.saveBitmapPng(bitmap, vfname);
                                    releaseDyamicBean.setVideoImgPath(mFileUtils.SDPATH + vfname + ".png");
                                } else {
                                    releaseDyamicBean.setVideoImgPath("");
                                }
                                releaseDyamicBean.setSort(i);
                            } catch (Exception e) {
                                releaseDyamicBean.setVideoImgPath("");
                            }
                            break;
                        case 3:
                            // 音频
                            LogUtils.e("音频-----》");
                            break;
                    }
                    releaseDyamicBeanList.add(releaseDyamicBean);
                }
                upLoad(releaseDyamicBeanList);
            } else {
                appExecutors.mainThread().execute(() ->
                        {
                            hidWaitDialog();
                            if (mSingleImageView != null) {
                                ToastUtils.showShort("请先选择资源");
                            } else {
                                pushStatusToUser(2);
                            }
                        }
                );
                return;
            }
        });
    }

    public void upLoad(List<ReleaseDyamicBean> releaseDyamicBeanList) {
        mSourceUpParam.imgList.clear();
        if (releaseDyamicBeanList.size() > 0) {
            if ("1".equals(releaseDyamicBeanList.get(0).getT())) { // 图片上传
                upImg(releaseDyamicBeanList);
            } else {
                // 视频上传
                upVideo(releaseDyamicBeanList);
            }
        } else {
            appExecutors.mainThread().execute(() -> {
                ToastUtils.showShort("请先选择资源");
            });
        }
    }

    private void upImg(List<ReleaseDyamicBean> releaseDyamicBeanList) {
        treeMap.clear();
        if (!NetworkUtils.isAvailableByPing()) {
            appExecutors.mainThread().execute(() -> {
                pushStatusToUser(3);// 失败
                hidWaitDialog();
                if (mSingleImageView != null) {
                    ToastUtils.showShort("网络未连接，请设置网络后重试！");
                    selectList.clear();
                }
            });
        } else {
            for (int i = 0; i < releaseDyamicBeanList.size(); i++) {


//                int finalI = i;
                if (releaseDyamicBeanList.get(i).getImgPath().startsWith("http")) {
                    releaseDyamicBeanList.get(i).setUpLoaded(true);
                    treeMap.put(i, releaseDyamicBeanList.get(i).getImgPath());
//                    mSourceUpParam.imgList.add(releaseDyamicBeanList.get(i).getImgPath());
                    if (mSourceUpParam.imgList.size() == releaseDyamicBeanList.size()) {
                        hidWaitDialog();
                        pushStatusToUser(2);// 完成
//                        selectList.clear();
                    }
                } else {
//                    FileUtils.getFileSize(new File(releaseDyamicBeanList.get(i).getImgPath()));
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    BitmapFactory.decodeFile(releaseDyamicBeanList.get(i).getImgPath(), options);
                    options.inJustDecodeBounds = true;
                    //获取图片的宽高
                    int height = options.outHeight;
                    int width = options.outWidth;
                    upImgTooss(width, height, releaseDyamicBeanList, i);
//                    upImgToServer(width, height, releaseDyamicBeanList, i);
                }
            }

        }

    }

    TreeMap<Integer, String> treeMap = new TreeMap<>();

    private void upImgTooss(int width, int height, List<ReleaseDyamicBean> releaseDyamicBeanList, int i) {
        MyOSSUtils.getInstance().upImage(this.getHoldingActivity(), getImageUrl(String.valueOf(width), String.valueOf(height)), releaseDyamicBeanList.get(i).getImgPath(), new MyOSSUtils.OssUpCallback() {
            @Override
            public void successImg(String img_url) {
                if (img_url != null) {
                    String imgurl[] = img_url.split("com");
                    treeMap.put(i, imgurl[1]);
                    appExecutors.mainThread().execute(() -> {
                        if (treeMap.size() == releaseDyamicBeanList.size()) {
                            if (mSingleImageView != null) {
                                selectList.clear();
                                Glide.with(CircleSourceUpFragment.this.getHoldingActivity()).load(img_url).into(mSingleImageView);
                            }
                            hidWaitDialog();
                            mSourceUpParam.imgList.clear();
                            for (int key : treeMap.keySet()) {
                                mSourceUpParam.imgList.add(treeMap.get(key));
                            }
                            pushStatusToUser(2);// 完成
                        }
                    });
                } else {
                    appExecutors.mainThread().execute(() -> {
                        hidWaitDialog();
                        pushStatusToUser(3);// 失败
                        if (mSingleImageView != null) {
                            ToastUtils.showShort("上传失败请重新选择后上传！");
                            selectList.clear();
                        }
                    });

                }
            }

            @Override
            public void successVideo(String video_url) {

            }

            @Override
            public void inProgress(long progress, long zong) {

            }
        });

    }

    @Inject
    CircleApiService circleService;

    private void upImgToServer(int width, int height, List<ReleaseDyamicBean> releaseDyamicBeanList, int i) {
        String imgName = getImageServerName(String.valueOf(width), String.valueOf(height));
        String imgLocalpath = releaseDyamicBeanList.get(i).getImgPath();

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), new File(imgLocalpath));
        MultipartBody.Part body = MultipartBody.Part.createFormData("imgFile", imgName, requestFile);

        circleService.publishImgToServer(body).observe(this, new Observer<ApiResponse<PublishImgSpeicalVo>>() {
            @Override
            public void onChanged(@Nullable ApiResponse<PublishImgSpeicalVo> publishImgSpeicalVoApiResponse) {
                if (publishImgSpeicalVoApiResponse != null
                        && publishImgSpeicalVoApiResponse.isSuccessful()
                        && publishImgSpeicalVoApiResponse.body != null
                        && "0".equals(publishImgSpeicalVoApiResponse.body.errno)
                        && !TextUtils.isEmpty(publishImgSpeicalVoApiResponse.body.url)
                ) {

                    releaseDyamicBeanList.get(i).setUpLoaded(true);
                    mSourceUpParam.imgList.add(publishImgSpeicalVoApiResponse.body.url);
                    appExecutors.mainThread().execute(() -> {
                        if (mSourceUpParam.imgList.size() == releaseDyamicBeanList.size()) {
                            if (mSingleImageView != null) {
                                selectList.clear();
                                Glide.with(CircleSourceUpFragment.this.getHoldingActivity()).load(CommonBdUtils.getImgUrl(publishImgSpeicalVoApiResponse.body.url)).into(mSingleImageView);
                            }
                            hidWaitDialog();
                            pushStatusToUser(2);// 完成
                        }
                    });

                } else {
                    appExecutors.mainThread().execute(() -> {
                        hidWaitDialog();
                        pushStatusToUser(3);// 失败
                        if (mSingleImageView != null) {
                            ToastUtils.showShort("上传失败请重新选择后上传！");
                            selectList.clear();
                        }
                    });
                }
            }
        });

    }


    private void upVideo(List<ReleaseDyamicBean> releaseDyamicBeanList) {

        if (!NetworkUtils.isAvailableByPing()) {
            appExecutors.mainThread().execute(() -> {
                hidWaitDialog();
                pushStatusToUser(3);// 失败
                if (mSingleImageView != null) {
                    hidWaitDialog();
                    ToastUtils.showShort("网络未连接，请设置网络后重试！");
                    selectList.clear();
                }
            });
        } else {
            for (int i = 0; i < releaseDyamicBeanList.size(); i++) {


                if (releaseDyamicBeanList.get(i).isUpLoaded()) {
                    if (i == releaseDyamicBeanList.size()) {
                        appExecutors.mainThread().execute(new Runnable() {
                            @Override
                            public void run() {
                                hidWaitDialog();
                                pushStatusToUser(2);// 完成
                            }
                        });
                    }
                    continue;
                }

                if (releaseDyamicBeanList.get(i).getVideoUrl().contains("http")) {
                    ReleaseDyamicBean releaseDyamicBean = releaseDyamicBeanList.get(i);
                    mSourceUpParam.upLoadVideoList.add(releaseDyamicBean);
                    if (mSourceUpParam.upLoadVideoList.size() == releaseDyamicBeanList.size()) {
                        hidWaitDialog();
                        pushStatusToUser(2);// 完成
                    }
                    continue;
                }

                int finalI = i;
                MyOSSUtils.getInstance().upVideo(this.getHoldingActivity(), FileUtils.getFileName(releaseDyamicBeanList.get(i).getLocVideoPath()), releaseDyamicBeanList.get(i).getLocVideoPath(), new MyOSSUtils.OssUpCallback() {
                    @Override
                    public void successImg(String img_url) {

                    }

                    @Override
                    public void successVideo(String video_url) {
                        if (video_url != null) {
                            ReleaseDyamicBean releaseDyamicBean = releaseDyamicBeanList.get(finalI);
                            String videourl[] = video_url.split("com");
                            releaseDyamicBean.setVideoUrl(videourl[1]);
                            mSourceUpParam.upLoadVideoList.add(releaseDyamicBean);

                            if (TextUtils.isEmpty(releaseDyamicBeanList.get(finalI).getVideoImgPath())) {
                                releaseDyamicBean.setUpLoaded(true);
                                releaseDyamicBean.setVideoImgUrl("");
                                if (mSourceUpParam.upLoadVideoList.size() == releaseDyamicBeanList.size()) {
                                    hidWaitDialog();
                                    pushStatusToUser(2);// 完成
                                }
                            } else {
                                BitmapFactory.Options options = new BitmapFactory.Options();
                                BitmapFactory.decodeFile(releaseDyamicBeanList.get(finalI).getVideoImgPath(), options);
                                options.inJustDecodeBounds = true;
                                //获取图片的宽高
                                int height = options.outHeight;
                                int width = options.outWidth;
                                MyOSSUtils.getInstance().upImage(CircleSourceUpFragment.this.getHoldingActivity(), getImageUrl(String.valueOf(width), String.valueOf(height)), releaseDyamicBeanList.get(finalI).getVideoImgPath(), new MyOSSUtils.OssUpCallback() {
                                    @Override
                                    public void successImg(String img_url) {
                                        appExecutors.mainThread().execute(() -> {
                                            if (img_url != null) {
//                                            mSourceUpParam.imgList.add(img_url);
//                                            releaseDyamicBeanList.get(finalI).setUpLoaded(true);
                                                String imgurl[] = img_url.split("com");
                                                releaseDyamicBean.setUpLoaded(true);
                                                releaseDyamicBean.setVideoImgUrl(imgurl[1]);
                                                if (mSourceUpParam.upLoadVideoList.size() == releaseDyamicBeanList.size()) {
                                                    hidWaitDialog();
                                                    pushStatusToUser(2);// 完成
                                                }
                                            } else {
                                                hidWaitDialog();
                                                pushStatusToUser(3);//失败
                                                ToastUtils.showShort("视频图片上传失败请重试！");
                                            }
                                        });

                                    }

                                    @Override
                                    public void successVideo(String video_url) {

                                    }

                                    @Override
                                    public void inProgress(long progress, long zong) {

                                    }
                                });
                            }

                        } else {
                            appExecutors.mainThread().execute(() -> {
                                hidWaitDialog();
                                ToastUtils.showShort("视频上传失败请重试！");
                                pushStatusToUser(3);//失败
                            });
                        }
                    }

                    @Override
                    public void inProgress(long progress, long zong) {
                    }
                });
            }
        }
    }


    private String getImageUrl(String with, String height) {
        String mpath = "";
        mpath = "upload/image/" + System.currentTimeMillis() + "_" + with + "x" + height + ".png";
        return mpath;
    }

    private String getImageServerName(String with, String height) {
        String name = System.currentTimeMillis() + "_" + with + "x" + height + ".png";
        return name;
    }

    // 发布状态给外部
    private void pushStatusToUser(int state) {
        mSourceUpParam.status.set((int) System.currentTimeMillis());
        mSourceUpParam.status.set(state);
    }

    // 数据回显
    public void processDataRep(List<LocalMedia> localMediaList) {
        this.selectList = localMediaList;
    }

}
