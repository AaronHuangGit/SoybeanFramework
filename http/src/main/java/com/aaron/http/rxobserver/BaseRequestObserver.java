package com.aaron.http.rxobserver;

import com.aaron.common.utils.LogUtils;
import com.aaron.http.code.result.Result;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created on 17/3/14.
 *
 * @author aaron.huang
 * @version 1.0.0
 */

public abstract class BaseRequestObserver<T extends Result> implements Observer<T> {
    protected final String TAG = getClass().getName();
    private Disposable mDisposable;
    private CompositeDisposable mCompositeDisposable;

    @Override
    public void onSubscribe(Disposable d) {
        LogUtils.d(TAG, "ProgressObserver onSubscribe...");
        mDisposable = d;
        if (mCompositeDisposable != null) {
            mCompositeDisposable.add(getDisposable());
        }
    }

    @Override
    public void onError(Throwable e) {
        mDisposable.dispose();
    }

    @Override
    public void onComplete() {
        mDisposable.dispose();
        LogUtils.d(TAG, "ProgressObserver onComplete...");
    }

    public Disposable getDisposable() {
        return mDisposable;
    }

    public void setCompositeDisposable(CompositeDisposable compositeDisposable) {
        mCompositeDisposable = compositeDisposable;
    }
}
