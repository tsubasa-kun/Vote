package com.love_cookies.vote.View.Activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.love_cookies.vote.Presenter.RegisterPresenter;
import com.love_cookies.vote.R;
import com.love_cookies.vote.Utils.ToastUtils;
import com.love_cookies.vote.View.Interface.IRegister;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_register)
public class RegisterActivity extends BaseActivity implements IRegister {

    @ViewInject(R.id.title_text)
    TextView pageTitle;
    @ViewInject(R.id.left_btn)
    ImageView leftBtn;
    @ViewInject(R.id.user_nickname)
    EditText nicknameET;
    @ViewInject(R.id.user_account)
    EditText accountET;
    @ViewInject(R.id.user_password)
    EditText passwordET;
    @ViewInject(R.id.user_re_password)
    EditText repasswordET;
    @ViewInject(R.id.register_btn)
    TextView registerBtn;

    RegisterPresenter registerPresenter = new RegisterPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    public void initView() {
        pageTitle.setText(R.string.register_title);
        leftBtn.setImageResource(R.drawable.title_btn_back);
        leftBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.left_btn:
                finish();
                break;
            case R.id.register_btn:
                doRegister();
                break;
            default:
                break;
        }
    }

    @Override
    public void doRegister() {
        if(TextUtils.isEmpty(nicknameET.getText().toString())) {
            ToastUtils.show(this, R.string.user_nickname_hint);
        } else if(TextUtils.isEmpty(accountET.getText().toString())) {
            ToastUtils.show(this, R.string.user_account_hint);
        } else if(TextUtils.isEmpty(passwordET.getText().toString())) {
            ToastUtils.show(this, R.string.user_password_hint);
        } else if(TextUtils.isEmpty(repasswordET.getText().toString())) {
            ToastUtils.show(this, R.string.user_re_password_hint);
        } else if(!passwordET.getText().toString().equals(repasswordET.getText().toString())) {
            ToastUtils.show(this, R.string.re_password_error);
        } else {
            registerPresenter.doRegister(accountET.getText().toString(), passwordET.getText().toString(), nicknameET.getText().toString());
        }
    }

    @Override
    public void registerSuccess() {
        ToastUtils.show(this, R.string.register_success);
        finish();
    }

    @Override
    public void registerFailed(String mag) {
        ToastUtils.show(this, mag);
    }
}
