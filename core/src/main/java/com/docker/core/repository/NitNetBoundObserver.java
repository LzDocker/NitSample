package com.docker.core.repository;

import android.support.annotation.Nullable;


public class NitNetBoundObserver<T> implements android.arch.lifecycle.Observer<Resource<T>> {


    private NitBoundCallback<T> nitBoundCallback;

    public NitNetBoundObserver(NitBoundCallback<T> netBoundCallback) {
        this.nitBoundCallback = netBoundCallback;
    }

    @Override
    public void onChanged(@Nullable Resource<T> tResource) {
        switch (tResource.status) {
            case LOADING:
                if (tResource.data != null) {
                    nitBoundCallback.onCacheComplete(tResource.data);
                } else {
                    if (tResource.call != null) {
                        nitBoundCallback.onLoading(tResource.call);
                    } else {
                        nitBoundCallback.onLoading();
                    }
                }
                break;
            case SUCCESS:
                nitBoundCallback.onComplete();
                nitBoundCallback.onComplete(tResource);
                break;
            case BUSSINESSERROR:
                nitBoundCallback.onComplete();
                nitBoundCallback.onBusinessError(tResource);
                break;
            case ERROR:
                nitBoundCallback.onComplete();
                nitBoundCallback.onNetworkError(tResource);
                break;
        }
    }
}
