package com.love_cookies.vote.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.love_cookies.vote.Presenter.LoginPresenter;
import com.love_cookies.vote.Presenter.MyApplication;
import com.love_cookies.vote.R;
import com.love_cookies.vote.Utils.ToastUtils;
import com.love_cookies.vote.View.Interface.ILogin;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity implements ILogin{

    @ViewInject(R.id.title_text)
    TextView pageTitle;
    @ViewInject(R.id.user_account)
    EditText accountET;
    @ViewInject(R.id.user_password)
    EditText passwordET;
    @ViewInject(R.id.login_btn)
    TextView loginBtn;
    @ViewInject(R.id.register_btn)
    TextView registerBtn;

    LoginPresenter loginPresenter = new LoginPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        if(!TextUtils.isEmpty(MyApplication.sp.getString("account", ""))) {
            autoLogin();
        }
    }

    @Override
    public void initView() {
        pageTitle.setText(R.string.login_title);
        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        accountET.setText(MyApplication.sp.getString("account", ""));
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                doLogin();
                break;
            case R.id.register_btn:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void doLogin() {
        if(TextUtils.isEmpty(accountET.getText().toString())) {
            ToastUtils.show(this, R.string.user_account_hint);
        } else if(TextUtils.isEmpty(passwordET.getText().toString())) {
            ToastUtils.show(this, R.string.user_password_hint);
        } else {
            loginPresenter.doLogin(accountET.getText().toString(), passwordET.getText().toString());
        }
    }

    @Override
    public void turnToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginFailed() {
        ToastUtils.show(this, R.string.login_error);
    }

    @Override
    public void autoLogin() {
        loginPresenter.doLogin(MyApplication.sp.getString("account", ""), MyApplication.sp.getString("password", ""));
    }
}
