package com.bfhd.circle.base;

import android.support.annotation.Nullable;

import com.docker.core.repository.Resource;

public class HivsNetBoundObserver<T> implements android.arch.lifecycle.Observer<Resource<T>> {


    private NetBoundCallback<T> netBoundCallback;

    public HivsNetBoundObserver(NetBoundCallback<T> netBoundCallback) {
        this.netBoundCallback = netBoundCallback;
    }

    @Override
    public void onChanged(@Nullable Resource<T> tResource) {
        switch (tResource.status) {
            case LOADING:
                if (tResource.data != null) {
                    netBoundCallback.onCacheComplete(tResource.data);
                } else {
                    if (tResource.call != null) {
                        netBoundCallback.onLoading(tResource.call);
                    } else {
                        netBoundCallback.onLoading();
                    }
                }
                break;
            case SUCCESS:
                netBoundCallback.onComplete();
                netBoundCallback.onComplete(tResource);
                break;
            case BUSSINESSERROR:
                netBoundCallback.onComplete();
                netBoundCallback.onBusinessError(tResource);
                break;
            case ERROR:
                netBoundCallback.onComplete();
                netBoundCallback.onNetworkError(tResource);
                break;
        }
    }


}
