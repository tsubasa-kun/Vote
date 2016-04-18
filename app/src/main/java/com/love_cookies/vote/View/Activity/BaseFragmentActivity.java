package com.love_cookies.vote.View.Activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;

import org.xutils.x;

/**
 * Created by xiekun on 2016/01/10 0010.
 */
public abstract class BaseFragmentActivity extends FragmentActivity implements View.OnClickListener {

    // 是否允许全屏
    private boolean mAllowFullScreen = true;

    public abstract void widgetClick(View v);

    public void setAllowFullScreen(boolean allowFullScreen) {
        this.mAllowFullScreen = allowFullScreen;
    }

    @Override
    public void onClick(View v) {
        widgetClick(v);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 竖屏锁定
        if (mAllowFullScreen) {
            requestWindowFeature(Window.FEATURE_NO_TITLE); // 取消标题
        }
        x.view().inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
