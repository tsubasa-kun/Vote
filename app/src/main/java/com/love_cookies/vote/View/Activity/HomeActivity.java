package com.love_cookies.vote.View.Activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.love_cookies.vote.Presenter.ActivityManager;
import com.love_cookies.vote.Presenter.HomePresenter;
import com.love_cookies.vote.Presenter.MyApplication;
import com.love_cookies.vote.R;
import com.love_cookies.vote.Utils.ToastUtils;
import com.love_cookies.vote.View.Interface.IHome;
import com.love_cookies.vote.View.Wedget.HomeItemView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_home)
public class HomeActivity extends BaseActivity implements IHome {

    @ViewInject(R.id.title_text)
    TextView pageTitle;
    @ViewInject(R.id.left_btn)
    ImageView leftBtn;
    @ViewInject(R.id.user_nickname_tv)
    TextView nicknameTV;
    @ViewInject(R.id.my_public_btn)
    HomeItemView myPublicBtn;
    @ViewInject(R.id.my_join_btn)
    HomeItemView myJoniBtn;
    @ViewInject(R.id.feedback_btn)
    HomeItemView feedbackBtn;
    @ViewInject(R.id.version_btn)
    HomeItemView versionBtn;
    @ViewInject(R.id.about_btn)
    HomeItemView aboutBtn;
    @ViewInject(R.id.update_btn)
    HomeItemView updateBtn;
    @ViewInject(R.id.logout_btn)
    TextView logoutBtn;

    HomePresenter homePresenter = new HomePresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.addActivity(this);
        initView();
    }

    @Override
    public void initView() {
        pageTitle.setText(R.string.home_title);
        leftBtn.setImageResource(R.drawable.title_btn_back);
        leftBtn.setOnClickListener(this);
        nicknameTV.setText(MyApplication.user.getNickname());
        myPublicBtn.setOnClickListener(this);
        myJoniBtn.setOnClickListener(this);
        feedbackBtn.setOnClickListener(this);
        versionBtn.setOnClickListener(this);
        aboutBtn.setOnClickListener(this);
        updateBtn.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.left_btn:
                finish();
                break;
            case R.id.my_public_btn:
                intent = new Intent(this, MyPublicVoteActivity.class);
                startActivity(intent);
                break;
            case R.id.my_join_btn:
                intent = new Intent(this, MyJoinVoteActivity.class);
                startActivity(intent);
                break;
            case R.id.feedback_btn:
                intent = new Intent(this, FeedBackActivity.class);
                startActivity(intent);
                break;
            case R.id.version_btn:
                intent = new Intent(this, VersionActivity.class);
                startActivity(intent);
                break;
            case R.id.about_btn:
                intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.update_btn:
                homePresenter.doUpdate();
                break;
            case R.id.logout_btn:
                homePresenter.doLogout();
            default:
                break;
        }
    }

    @Override
    public void logout() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
        ActivityManager.finishAll();
    }

    @Override
    public void update(String msg) {
        PackageManager manager = this.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if(msg.equals(info.versionName)) {
            ToastUtils.show(this, R.string.no_update_tip);
        } else {
            String res = getResources().getString(R.string.has_update_tip);
            String tip = String.format(res, msg);
            ToastUtils.show(this, tip);
        }
    }

}
