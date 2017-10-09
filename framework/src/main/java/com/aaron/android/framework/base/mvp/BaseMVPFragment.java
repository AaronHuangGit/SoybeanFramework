package com.aaron.android.framework.base.mvp;

import android.content.Context;

import com.aaron.android.framework.base.mvp.presenter.BasePresenter;
import com.aaron.android.framework.base.mvp.view.BaseView;
import com.aaron.android.framework.base.ui.BaseFragment;

/**
 * Created on 15/7/4.
 *
 * @author ran.huang
 * @version 1.0.0
 */
public abstract class BaseMVPFragment<T extends BasePresenter> extends BaseFragment implements BaseView {


    protected T mPresenter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
