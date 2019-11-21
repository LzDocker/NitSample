package com.docker.common.common.utils;

import android.os.Looper;
import android.view.View;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;


/**
 * kxf -> 2019-11-20
 **/
public final class ViewClickObservable extends Observable<Object> {
    private final View view;

    public ViewClickObservable(View view) {
        this.view = view;
    }

    @Override
    protected void subscribeActual(Observer<? super Object> observer) {
        if (!checkMainThread(observer)) {
            return;
        }
        Listener listener = new Listener(view, observer);
        observer.onSubscribe(listener);
        view.setOnClickListener(listener);
    }

    public static final class Listener extends MainThreadDisposable implements View.OnClickListener {
        private final View view;
        private final Observer<? super Object> observer;

        Listener(View view, Observer<? super Object> observer) {
            this.view = view;
            this.observer = observer;
        }

        @Override
        public void onClick(View v) {
            if (!isDisposed()) {
                observer.onNext("");
            }
        }

        @Override
        protected void onDispose() {
            view.setOnClickListener(null);
        }
    }

    public static boolean checkMainThread(Observer<?> observer) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            observer.onError(new IllegalStateException(
                    "Expected to be called on the main thread but was " + Thread.currentThread().getName()));
            return false;
        }
        return true;
    }

}
