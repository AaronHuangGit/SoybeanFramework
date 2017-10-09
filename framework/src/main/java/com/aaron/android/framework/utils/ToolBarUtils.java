package com.aaron.android.framework.utils;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.ViewGroup;

/**
 * @Author SanFen
 * @Email sanfenruxi1@163.com
 * @Date 2017/9/19
 * @Version 1.0
 */
public class ToolBarUtils {
    public static int actionBarHeight = 0;

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        int identifier = context.getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (identifier > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(identifier);
        }
        return statusBarHeight;
    }

    /**
     * 获取标题栏的高度
     * @param context con
     * @return
     */
    public static int getActionBarSizeHeight(Context context) {
        if (actionBarHeight != 0) {
            return actionBarHeight;
        }
        TypedValue tv = new TypedValue();
        if (context.getTheme()
                .resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(
                    tv.data, context.getResources().getDisplayMetrics());
        }
        return actionBarHeight;

    }

    /**
     * 设置Toolbar 高度 主要在fragment中使用沉浸式时使用
     *
     * @param context context
     * @param toolbar toolbar
     */
    public static void setToolbarHeight(Context context, Toolbar toolbar) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            ViewGroup.LayoutParams layoutParams = toolbar.getLayoutParams();
            layoutParams.height = ToolBarUtils.getActionBarSizeHeight(context) + ToolBarUtils.getStatusBarHeight(context);
            toolbar.setLayoutParams(layoutParams);
            toolbar.setPadding(0, ToolBarUtils.getStatusBarHeight(context), 0, 0);
            toolbar.setFitsSystemWindows(false);
        }
    }


}
