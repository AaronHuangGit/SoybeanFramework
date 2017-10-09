package com.aaron.android.framework.base.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.aaron.android.framework.base.eventbus.BaseMessage;
import com.aaron.android.framework.utils.PopupUtils;
import com.aaron.common.utils.LogUtils;
import com.umeng.analytics.MobclickAgent;

import de.greenrobot.event.EventBus;

/**
 * Created on 2017/6/1.
 *
 * @author aaron.huang
 * @version 1.0.0
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected final String TAG = getClass().getName();

    private static boolean isAppToBackground = false; //应用是否在后台的标识
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d(TAG, TAG + "----onCreate");
        if (isEventTarget() && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        isAppToBackground = false;
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.d(TAG, TAG + "----onResume");
        MobclickAgent.onPageStart(TAG);
        MobclickAgent.onResume(this);
        isAppToBackground = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.d(TAG, TAG + "----onPause");
        MobclickAgent.onPageEnd(TAG);
        MobclickAgent.onPause(this);
        isAppToBackground = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.d(TAG, TAG + "----onStop");
        isAppToBackground = true;
    }

    protected void postEvent(BaseMessage object) {
        EventBus.getDefault().post(object);
    }

    protected void startActivity(Class<?> cls) {
        startActivity(new Intent(this, cls));
    }

    @Override
    protected void onDestroy() {
        if (isEventTarget() && EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        LogUtils.d(TAG, TAG + "----onDestroy");
        isAppToBackground = false;
        super.onDestroy();
    }

    protected boolean isEventTarget() {
        return false;
    }

    public static boolean isAppToBackground() {
        return isAppToBackground;
    }

    public void showToast(String message) {
        PopupUtils.showToast(this, message);
    }

    public void showToast(int resId) {
        PopupUtils.showToast(this, resId);
    }
}
