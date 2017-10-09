package com.aaron.android.framework.base.mvp.presenter;

import com.aaron.android.framework.base.mvp.view.BaseView;

/**
 * Created on 2017/6/1.
 *
 * @author aaron.huang
 * @version 1.0.0
 */

public interface BasePresenter<T extends BaseView> {

    /**
     * 绑定View
     *
     * @param view BaseView
     */
    void attachView(T view);

    /**
     * 从View解绑
     */
    void detachView();
}
