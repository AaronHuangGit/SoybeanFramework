package com.aaron.android.framework.base.mvp.presenter;

import com.aaron.android.framework.base.eventbus.BaseMessage;
import com.aaron.android.framework.base.mvp.view.BaseView;
import com.aaron.common.utils.LogUtils;
import com.aaron.http.code.result.Result;
import com.aaron.http.rxobserver.BaseRequestObserver;

import de.greenrobot.event.EventBus;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created on 2017/6/1.
 *
 * @author aaron.huang
 * @version 1.0.0
 */

public abstract class RxBasePresenter<T extends BaseView> implements BasePresenter<T> {
    protected T mView;
    private CompositeDisposable mCompositeDisposable;

    private void disposeAll() {
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.clear();
            LogUtils.d("aaron", getClass().getName() + ": disposeAll()" );
        }
    }

    protected <R extends Result> BaseRequestObserver<R> addObserverToCompositeDisposable(BaseRequestObserver<R> observer) {
        if (observer != null) {
            if (mCompositeDisposable == null) {
                mCompositeDisposable = new CompositeDisposable();
            }
            observer.setCompositeDisposable(mCompositeDisposable);
        }
        return observer;
    }

    @Override
    public void attachView(T view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
        disposeAll();
    }

    public void postEvent(BaseMessage message) {
        EventBus.getDefault().post(message);
    }

}
