package com.docker.video_edit.sample.ui.activity;

import android.Manifest.permission;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.common.common.router.AppRouter;
import com.docker.video_edit.R;
import com.docker.video_edit.sample.base.BaseActivity;
import com.docker.video_edit.sample.helper.ToolbarHelper;
import com.luck.picture.lib.permissions.RxPermissions;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

@Route(path = AppRouter.VideoEditIndex)
public class MainActivity extends BaseActivity {

    private RxPermissions mRxPermissions;

    @Override
    protected int getLayoutId() {
        return R.layout.vd_activity_video_edit_index;
    }

    @Override
    protected void initToolbar(ToolbarHelper toolbarHelper) {
        toolbarHelper.setTitle("视频编辑");
        toolbarHelper.hideBackArrow();
    }

    @Override
    protected void initView() {
        mRxPermissions = new RxPermissions(this);
        ARouter.getInstance().inject(this);
    }

    /**
     * 拍照
     *
     * @param view
     */
    public void takeCamera(View view) {
        mRxPermissions
                .request(permission.WRITE_EXTERNAL_STORAGE, permission.RECORD_AUDIO, permission.CAMERA)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        subscribe(d);
                    }

                    @Override
                    public void onNext(Boolean granted) {
                        if (granted) { //已获取权限
                            Intent intent = new Intent(MainActivity.this, VideoCameraActivity.class);
                            startActivityForResult(intent, 100);
                        } else {
                            Toast.makeText(MainActivity.this, "给点权限行不行？", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 相册
     *
     * @param view
     */
    public void takeAlbum(View view) {
        mRxPermissions
                .request(permission.WRITE_EXTERNAL_STORAGE, permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        subscribe(d);
                    }

                    @Override
                    public void onNext(Boolean granted) {
                        if (granted) { //已获取权限
                            Intent intent = new Intent(MainActivity.this, VideoAlbumActivity.class);
                            startActivityForResult(intent, 100);
                        } else {
                            Toast.makeText(MainActivity.this, "给点权限行不行？", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
