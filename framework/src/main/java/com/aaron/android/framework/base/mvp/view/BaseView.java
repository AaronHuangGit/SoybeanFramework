package com.aaron.android.framework.base.mvp.view;

/**
 * Created on 2017/6/1.
 *
 * @author aaron.huang
 * @version 1.0.0
 */

public interface BaseView {
    void setPresenter();

    void showToast(String message);

    void showToast(int resId);
}
