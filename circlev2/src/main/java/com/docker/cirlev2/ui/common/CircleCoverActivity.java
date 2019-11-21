package com.docker.cirlev2.ui.common;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.Observable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.dcbfhd.utilcode.utils.AppUtils;
import com.dcbfhd.utilcode.utils.KeyboardUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2ActivityCircleCoverBinding;
import com.docker.cirlev2.vm.PublishViewModel;
import com.docker.cirlev2.vm.SampleListViewModel;
import com.docker.cirlev2.vo.param.SourceCoverParam;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.command.NitContainerCommand;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.core.util.AppExecutors;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/*
 * 选择照片的 act
 *
 * 选择单张图片的时候 提供剪裁功能 不管是选择 网络图片 本地图片 还是拍照
 *
 * 选择多张图片的时候 只显示网络图片
 *
 * */
public class CircleCoverActivity extends NitCommonActivity<PublishViewModel, Circlev2ActivityCircleCoverBinding> {

    //
    private SourceCoverParam mSourceCoverParam;
    List<Fragment> fragmentList;
    private List<LocalMedia> selectList = new ArrayList<>();//已选中的图片
    private boolean cropAtAct = false;
    private String photoPath; //拍照本地存储路径
    public final static int TAKE_PICTURE = 1;
    @Inject
    AppExecutors appExecutors;

    public static void startMe(Context context, SourceCoverParam sourceCoverParam) {
        Intent intent = new Intent(context, CircleCoverActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("sourceCoverParam", sourceCoverParam);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void startMeForResult(Activity context, SourceCoverParam sourceCoverParam, int reauestCode) {
        Intent intent = new Intent(context, CircleCoverActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("sourceCoverParam", sourceCoverParam);
        intent.putExtras(bundle);
        context.startActivityForResult(intent, reauestCode);
    }

    @Inject
    ViewModelProvider.Factory factory;

    @Override
    protected int getLayoutId() {
        return R.layout.circlev2_activity_circle_cover;
    }

    @Override
    public PublishViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(PublishViewModel.class);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        mSourceCoverParam = (SourceCoverParam) getIntent().getExtras().getSerializable("sourceCoverParam");
        super.onCreate(savedInstanceState);
        mToolbar.setTitle("选择图片");
        if (mSourceCoverParam.uiType == mSourceCoverParam.UI_TYPE_TOP_HIDEN) {
            mBinding.circleTopSelect.setVisibility(View.GONE);
        } else {
            mBinding.circleTopSelect.setVisibility(View.VISIBLE);
        }
        if (mSourceCoverParam.sourceMaxNum == 1 && mSourceCoverParam.needCrop) {
            mBinding.tvComplete.setVisibility(View.GONE);
        } else {
            mBinding.tvComplete.setVisibility(View.VISIBLE);
        }
        mSourceCoverParam.status.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (mSourceCoverParam.status.get() == 1) {
                    Intent intent = new Intent();
                    intent.putExtra("sourceCoverParam", mSourceCoverParam);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        // 点击完成
        mBinding.tvComplete.setOnClickListener(v -> {
            if (mSourceCoverParam.getImgList().size() > 0) {
                appExecutors.diskIO().execute(() -> {
                    ArrayList<String> localList = new ArrayList<>();
                    for (int i = 0; i < mSourceCoverParam.getImgList().size(); i++) {
                        File file = getImagePathFromCache(mSourceCoverParam.getImgList().get(i), mSourceCoverParam.width, mSourceCoverParam.height);
                        localList.add(file.getAbsolutePath());
                    }
                    mSourceCoverParam.getImgList().clear();
                    mSourceCoverParam.getImgList().addAll(localList);
                    appExecutors.mainThread().execute(() -> mSourceCoverParam.status.set(1));
                });

            } else {
                ToastUtils.showShort("请选择图片");
            }
        });


        // 选择 单张的时候才显示
        mBinding.tvGraly.setOnClickListener(v -> {
            // 相册
            // 进入相册 以下是例子：不需要的api可以不写
            PictureSelector.create(CircleCoverActivity.this)
                    .openGallery(PictureMimeType.ofAll())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    // 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                    .maxSelectNum(1)// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    .imageSpanCount(3)// 每行显示个数
                    .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                    .previewImage(true)// 是否可预览图片
                    .previewVideo(false)// 是否可预览视频
                    .enablePreviewAudio(false) // 是否可播放音频
                    .isCamera(true)// 是否显示拍照按钮
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    .enableCrop(false)// 是否裁剪
                    .compress(true)// 是否压缩
                    .synOrAsy(true)//同步true或异步false 压缩 默认同步
                    .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                    .isGif(true)// 是否显示gif图片
//                    .selectionMedia(selectList)// 是否传入已选图片
                    .minimumCompressSize(100)// 小于100kb的图片不压缩
                    .scaleEnabled(true)// 裁剪是否可放大缩小图片
//                    .videoMaxSecond(15)//显示多少秒以内的视频or音频也可适用
//                    .recordVideoSecond(15)//录制视频秒数 默认60s
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
        });

        // 选择 单张的时候才显示
        mBinding.tvCamera.setOnClickListener(v -> {
            //  相机
//            TakePicture();
            PictureSelector.create(this)
                    .openCamera(PictureMimeType.ofImage())
                    .forResult(PictureConfig.CHOOSE_REQUEST);
        });
    }

    private File getImagePathFromCache(String url, int width, int height) {
        try {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.override(mSourceCoverParam.width, mSourceCoverParam.height);
            requestOptions.priorityOf(Priority.HIGH).onlyRetrieveFromCache(true);
            File imageFile = Glide.with(this).asFile()
                    .apply(requestOptions)
                    .load(url)
                    .submit().get();
            if (imageFile != null && imageFile.exists()) {
                return imageFile;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    private void TakePicture() {
        photoPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera/" + AppUtils.getAppName() + "/";
        File file = new File(photoPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        photoPath = photoPath + String.valueOf(System.currentTimeMillis()) + ".jpg";
        File mfile = new File(photoPath);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            uri = Uri.fromFile(mfile);
        } else {
            /**
             * 7.0 调用系统相机拍照不再允许使用Uri方式，应该替换为FileProvider
             * 并且这样可以解决MIUI系统上拍照返回size为0的情况
             */
            uri = FileProvider.getUriForFile(this, com.aliyun.clientinforeport.util.AppUtils.getPackageName(this) + ".fileProvider", mfile);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, TAKE_PICTURE);

    }

    @Override
    public void initView() {
        fragmentList = new ArrayList<>();
        fragmentList.add(CircleCovertFragment.newInstance(CircleCovertFragment.IMGTYPE_BAIDU, mSourceCoverParam));
        fragmentList.add(CircleCovertFragment.newInstance(CircleCovertFragment.IMGTYPE_JINGXUAN, mSourceCoverParam));
        CommonpagerAdapter adapter = new CommonpagerAdapter(getSupportFragmentManager(), fragmentList);
        mBinding.viewPager.setAdapter(adapter);
        mBinding.viewPager.setOffscreenPageLimit(fragmentList.size());
        mBinding.tabCircleTitle.setViewPager(mBinding.viewPager, new String[]{"网络搜图", "精选推荐图片"});
        mBinding.refresh.setOnLoadMoreListener(refreshLayout -> ((NitCommonFragment) fragmentList.get(mBinding.viewPager.getCurrentItem())).onLoadMore(mBinding.refresh));
        mBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                mBinding.refresh.finishLoadMore();
                if (i != 0) {
                    mBinding.circleLlSearch.setVisibility(View.GONE);
                    mBinding.refresh.setEnableLoadMore(false);
                } else {
                    mBinding.circleLlSearch.setVisibility(View.VISIBLE);
                    mBinding.refresh.setEnableLoadMore(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        mBinding.tvSubmit.setOnClickListener(v -> {
            ((CircleCovertFragment) (fragmentList.get(0))).keyWords = mBinding.circleEdSearch.getText().toString().trim();
            ((NitCommonFragment) (fragmentList.get(0))).onReFresh(mBinding.refresh);
            KeyboardUtils.hideSoftInput(v);
        });
    }

    @Override
    public void initObserver() {

    }

    @Override
    public void initRouter() {

    }

    @Override
    public NitContainerCommand providerNitContainerCommand(int flag) {
        return null;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PictureConfig.CHOOSE_REQUEST) {  // 相册
            if (resultCode == RESULT_OK) {
                selectList = PictureSelector.obtainMultipleResult(data);
                CropPic(selectList.get(0).getPath());
                cropAtAct = true;
            }
        }
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            mSourceCoverParam.getImgList().add(resultUri.getPath());
            mSourceCoverParam.status.set(1);
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
            ToastUtils.showShort("剪裁出错了，请重试");
        }

        if (resultCode == RESULT_OK && requestCode == TAKE_PICTURE) {
            if (!TextUtils.isEmpty(photoPath)) {
                CropPic(photoPath);
                cropAtAct = true;
            }
        }
        if (!cropAtAct) {
            fragmentList.get(mBinding.viewPager.getCurrentItem()).onActivityResult(requestCode, resultCode, data);
        }
    }


    private void CropPic(String path) {
        Uri sourceUri = Uri.fromFile(new File(path));
        String cropName = System.currentTimeMillis() + ".jpg";
        Uri dstUri = Uri.fromFile(new File(this.getCacheDir(), cropName));
        UCrop uCrop = UCrop.of(sourceUri, dstUri);
        UCrop.Options options = new UCrop.Options();
        options.setToolbarColor(this.getResources().getColor(R.color.colorPrimary));
        options.setToolbarTitle("图片剪裁");
        options.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        options.setFreeStyleCropEnabled(true);
        options.withMaxResultSize(mSourceCoverParam.width, mSourceCoverParam.height);
        uCrop.withOptions(options).start(this);
    }
}
