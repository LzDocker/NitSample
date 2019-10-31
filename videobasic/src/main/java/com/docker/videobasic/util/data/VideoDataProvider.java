package com.docker.videobasic.util.data;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.docker.common.common.vo.ServiceDataBean;
import com.docker.video.entity.DataSource;
import com.docker.video.event.BundlePool;
import com.docker.video.event.EventKey;
import com.docker.video.provider.BaseDataProvider;
import com.docker.video.provider.VideoBean;

import java.util.List;

/**
 * Created by Taurus on 2018/4/15.
 */

public class VideoDataProvider extends BaseDataProvider {

    private DataSource mDataSource;

    private List<ServiceDataBean> mVideos;

    public void setData(List<ServiceDataBean> list){
        mVideos = list;
    }

    private Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    @Override
    public void handleSourceData(DataSource sourceData) {
        this.mDataSource = sourceData;
        onProviderDataStart();
        mHandler.removeCallbacks(mLoadDataRunnable);
        mHandler.postDelayed(mLoadDataRunnable, 2000);
    }

    private Runnable mLoadDataRunnable = new Runnable() {
        @Override
        public void run() {
            long id = mDataSource.getId();
            int index = (int) (id%mVideos.size());
            ServiceDataBean bean = mVideos.get(index);
            mDataSource.setData(bean.getExtData().getResource().get(0).getUrl());
            mDataSource.setTitle(bean.getNickname());
            Bundle bundle = BundlePool.obtain();
            bundle.putSerializable(EventKey.SERIALIZABLE_DATA, mDataSource);
            onProviderMediaDataSuccess(bundle);
        }
    };

    @Override
    public void cancel() {
        mHandler.removeCallbacks(mLoadDataRunnable);
    }

    @Override
    public void destroy() {
        cancel();
    }
}
