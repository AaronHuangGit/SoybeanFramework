package com.aaron.android.framework.base.widget.refresh;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.aaron.android.framework.R;
import com.aaron.android.framework.base.mvp.presenter.BasePresenter;
import com.aaron.android.framework.base.ui.actionbar.ToolBarController;
import com.aaron.android.framework.utils.ResourceUtils;
import com.aaron.android.framework.utils.SDKVersionUtils;
import com.aaron.android.framework.utils.ToolBarUtils;
import com.aaron.common.utils.StringUtils;

/**
 * Created by aaa on 17/9/20.
 * 带有AppBar的可以下拉刷新和加载更多的fragment
 */

public abstract class AppBarNetworkSwipeRecyclerRefreshPagerLoaderFragment<T extends BasePresenter> extends NetworkSwipeRecyclerRefreshPagerLoaderFragment<T> {

    private LinearLayout mRootView;
    ToolBarController mToolBarController;
    View mToolBar;

    public AppBarNetworkSwipeRecyclerRefreshPagerLoaderFragment() {
    }


    @Override
    protected View createContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        init();
        if (SDKVersionUtils.hasKitKat()) {
            Window window = getActivity().getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        return mRootView;
    }

    private void init() {
        initRootView();
        initToolBar();
        initContentView();
    }

    /**
     * 初始化RootView
     */
    private void initRootView() {
        mRootView = new LinearLayout(getActivity());
        mRootView.setOrientation(LinearLayout.VERTICAL);
    }

    /**
     * 初始化ToolBar
     */
    private void initToolBar() {
        mToolBar = createToolBarLayout();
        if (mToolBar == null){
            mToolBarController = new ToolBarController(getContext());
            showHomeUpIcon(R.drawable.back);
            ToolBarUtils.setToolbarHeight(getContext(), mToolBarController.getToolbar());
            mRootView.addView(mToolBarController.getRootView());
        }else {
            mRootView.addView(mToolBar);
        }
    }

    public View createToolBarLayout() {
        return null;
    }

    /**
     * 初始化Content View
     */
    private void initContentView() {
        mRecyclerView = createRecyclerView();
        if (mRecyclerView == null) {
            getDefaultRecyclerView();
        }
        mFooterView = LayoutInflater.from(getContext()).inflate(R.layout.layout_network_footer, mRecyclerView, false);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
        mRootView.addView(mRecyclerView, layoutParams);
    }

    /**
     * 在onDestroy生命周期中，回收销毁一些资源
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mToolBarController.destroy();
    }

    /**
     * @return 获取ToolBarController
     */
    public ToolBarController getToolBarController() {
        return mToolBarController;
    }

    /**
     * 隐藏ToolBar
     */
    protected void hideAppBar() {
        mRootView.removeView(mToolBarController.getRootView());
    }

    /**
     * @param title 设置标题
     */
    public void setTitle(String title) {
        mToolBarController.setTitle(title);
    }

    /**
     * @param resId 设置标题
     */
    public void setTitle(int resId) {
        mToolBarController.setTitle(resId);
    }


    /**
     * 设置左边的文字按钮
     *
     * @param text 文字内容
     */
    public void showLeftMenu(String text) {
        if (StringUtils.isEmpty(text)) {
            mToolBarController.hideMenu(ToolBarController.ID_MENU_LEFT);
            return;
        }
        mToolBarController.showMenu(ToolBarController.ID_MENU_LEFT, text);
    }

    /**
     * 设置左边的文字按钮
     *
     * @param text 文字内容
     */
    public void showLeftMenu(String text, View.OnClickListener onClickListener) {
        if (StringUtils.isEmpty(text)) {
            mToolBarController.hideMenu(ToolBarController.ID_MENU_LEFT);
            return;
        }
        mToolBarController.showMenu(ToolBarController.ID_MENU_LEFT, text, onClickListener);
    }

    public void setMenuTextColor(int menuId, int resId) {
        if (menuId == ToolBarController.ID_MENU_LEFT) {
            mToolBarController.setLeftMenuTextColor(resId);
        } else if (menuId == ToolBarController.ID_MENU_RIGHT) {
            mToolBarController.setRightMenuTextColor(resId);
        }
    }

    /**
     * 设置右边的文字按钮
     *
     * @param text 文字内容
     */
    public void showRightMenu(String text) {
        if (StringUtils.isEmpty(text)) {
            mToolBarController.hideMenu(ToolBarController.ID_MENU_RIGHT);
            return;
        }
        mToolBarController.showMenu(ToolBarController.ID_MENU_RIGHT, text);
    }

    public void showRightMenu(String text, View.OnClickListener onClickListener) {
        if (StringUtils.isEmpty(text)) {
            mToolBarController.hideMenu(ToolBarController.ID_MENU_RIGHT);
            return;
        }
        mToolBarController.showMenu(ToolBarController.ID_MENU_RIGHT, text, onClickListener);
    }

    /**
     * 设置左边图片按钮
     *
     * @param resId 图片按钮资源
     */
    public void showHomeUpIcon(int resId) {
        //默认左边图标点击监听是关闭当前的Activity
        showHomeUpIcon(resId, mDefaultHomeIconClickListener);
    }


    /**
     * 设置右边图片按钮
     *
     * @param resId           图片按钮资源
     * @param onClickListener 点击事件监听
     */
    public void showHomeUpIcon(int resId, View.OnClickListener onClickListener) {
        Drawable drawable = null;
        if (resId != 0) {
            drawable = ResourceUtils.getDrawable(resId);
        }
        mToolBarController.setLeftIcon(drawable, onClickListener);
    }

    /**
     * 设置右边图片按钮
     *
     * @param resId           图片按钮资源
     * @param onClickListener 点击事件监听
     */
    public void setRightIcon(int resId, View.OnClickListener onClickListener) {
        Drawable drawable = null;
        if (resId != 0) {
            drawable = ResourceUtils.getDrawable(resId);
        }
        mToolBarController.setRightIcon(drawable, onClickListener);
    }

    public void setLeftMenuTextDrawable(int flag, int resId) {
        mToolBarController.setLeftMenuTextDrawable(flag, resId);
    }

    public void setRightMenuTextDrawable(int flag, int resId) {
        mToolBarController.setRightMenuTextDrawable(flag, resId);
    }

    /**
     * 设置左边图片按钮
     *
     * @param resId 图片按钮资源
     */
    public void setRightIcon(int resId) {
        setRightIcon(resId, null);
    }


    /**
     * 设置菜单点击事件
     *
     * @param onMenuItemClickListener OnMenuItemClickListener
     */
    public void setMenuOnClickListener(ToolBarController.OnMenuItemClickListener onMenuItemClickListener) {
        mToolBarController.setOnMenuItemClickListener(onMenuItemClickListener);
    }


    /**
     * 默认左边图片按钮监听器
     */
    private final View.OnClickListener mDefaultHomeIconClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getActivity().finish();
        }
    };
}
