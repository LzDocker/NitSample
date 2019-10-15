package com.docker.common.common.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.R;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.video.vm.AliPlayerViewModel;
import com.docker.common.common.widget.SuperFileView;
import com.docker.common.databinding.CommonViewDocumentBinding;
import com.docker.core.base.BaseActivity;
import com.docker.core.di.httpmodule.progress.ProgressListen;
import com.docker.core.di.httpmodule.progress.ProgressManager;

import java.io.File;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;


/**
 * kxf，ppt wold 等文件预览
 */

@Route(path = AppRouter.ViewDoc_Document)
public class ViewDocumentActivity extends BaseActivity<AliPlayerViewModel, CommonViewDocumentBinding> {//implements TbsReaderView.ReaderCallback {

    private SuperFileView superFileView;
    @Inject
    ViewModelProvider.Factory factory;

    @Autowired()
    public String fileURL, fileName;//文件的网络地址,保存的文件名

    private String downloadPath;


    @Override
    protected int getLayoutId() {
        return R.layout.common_view_document;
    }

    @Override
    public AliPlayerViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(AliPlayerViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        initView();
    }

    protected void inject() {
        super.inject();

    }

    public void initView() {
        superFileView = mBinding.fileDetailSuperfile;
        downloadPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
        startPreview();
        mBinding.empty.setOnretryListener(() -> {
            finish();
            ARouter.getInstance().build(AppRouter.ViewDoc_Document).withString("fileURL", fileURL)
                    .withString("fileName", fileName)
                    .navigation();
        });
        mToolbar.setTitle(fileName);
        mToolbar.hide();
    }

    public void startPreview() {
        if (isLocalExist()) {
            mBinding.empty.hide();
            superFileView.displayFile(getLocalFile());
        } else {
            downloadFile();
        }
    }

    private String parseName(String url) {
        String fileName = null;
        try {
            fileName = url.substring(url.lastIndexOf("/") + 1);
        } finally {
            if (TextUtils.isEmpty(fileName)) {
                fileName = String.valueOf(System.currentTimeMillis());
            }
        }
        return fileName;
    }

    private boolean isLocalExist() {
        return getLocalFile().exists();
    }

    private File getLocalFile() {
        return new File(downloadPath, fileName);
    }

//

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (superFileView != null) {
            superFileView.onStopDisplay();
        }
    }


//    @Override
//    public void onCallBackAction(Integer integer, Object o, Object o1) {
//        if (isLocalExist()) {
//            displayFile();
//        }
//    }


    @Inject
    ProgressManager progressManager;

    private void downloadFile() {
        progressManager.download(downloadPath, fileName, fileURL, new ProgressListen() {
            @Override
            public void ondownloadStart(Call call) {

            }

            @Override
            public Call onProcessUploadMethod(Map<String, RequestBody> params) {
                return null;
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                ToastUtils.showShort("下载失败");
                mBinding.empty.showError();
            }

            @Override
            public void onProgress(long progress, long total, boolean done) {
            }

            @Override
            public void onComplete(Response<ResponseBody> response) {
//                mBinding.refresh.finishRefresh();
                mBinding.empty.hide();
                File file = new File(downloadPath, fileName);
                try {
                    superFileView.displayFile(file);
                } catch (Exception e) {

                }
            }
        });

    }

}
