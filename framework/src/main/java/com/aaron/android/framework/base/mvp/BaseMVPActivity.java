package com.aaron.android.framework.base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.aaron.android.framework.base.mvp.presenter.BasePresenter;
import com.aaron.android.framework.base.mvp.view.BaseView;
import com.aaron.android.framework.base.ui.BaseActivity;

/**
 * Created on 2017/6/1.
 *
 * @author aaron.huang
 * @version 1.0.0
 */

public abstract class BaseMVPActivity<T extends BasePresenter> extends BaseActivity implements BaseView {

    protected T mPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

}
