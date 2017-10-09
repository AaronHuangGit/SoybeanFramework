package com.aaron.android.framework.base.ui.actionbar;

import android.os.Bundle;
import android.view.View;

import com.aaron.android.framework.base.ui.swipeback.SwipeBackLayout;
import com.aaron.android.framework.base.ui.swipeback.SwipeBackActivityBase;
import com.aaron.android.framework.base.ui.swipeback.SwipeBackActivityHelper;
import com.aaron.android.framework.utils.ActivityUtils;

/**
 * Created on 2017/6/3.
 *
 * @author aaron.huang
 * @version 1.0.0
 */

public abstract class AppBarSwipeBackActivity extends AppBarActivity implements SwipeBackActivityBase {
    private SwipeBackActivityHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        ActivityUtils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }
}
