package com.docker.core.repository;


import com.dcbfhd.utilcode.utils.ToastUtils;

import retrofit2.Call;

/**
 * Created by zhangxindang on 2018/9/6.
 */

public abstract class NitBoundCallback<T> {


    public NitBoundCallback() {
    }

    /*
     * 缓存读取成功
     * */
    public void onCacheComplete(T Result) {

    }

    public void onComplete(Resource<T> resource) {

    }

    public void onComplete() {
    }

    public void onBusinessError(Resource<T> resource) {
        if (resource.message != null) {
            ToastUtils.showShort(resource.message);
        }
    }

    ;

    public void onNetworkError(Resource<T> resource) {
        if (resource.message != null) {
            ToastUtils.showShort(resource.message);
        }
    }

    ;

    public void onLoading() {

    }

    ;

    public void onLoading(Call call) {
    }

    ;

}
