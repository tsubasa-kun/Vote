package com.love_cookies.vote.Presenter;

import com.love_cookies.vote.Model.Bean.UserBean;
import com.love_cookies.vote.Model.Biz.Interface.CallBack;
import com.love_cookies.vote.Model.Biz.LoginAndRegisterBiz;
import com.love_cookies.vote.View.Interface.ILogin;

/**
 * Created by xiekun on 2016/1/11 0011.
 */
public class LoginPresenter {

    private LoginAndRegisterBiz loginAndRegisterBiz;
    private ILogin iLogin;

    public LoginPresenter(ILogin iLogin) {
        loginAndRegisterBiz = new LoginAndRegisterBiz();
        this.iLogin = iLogin;
    }

    public void doLogin(final String account, final String password) {
        loginAndRegisterBiz.doLogin(account, password, new CallBack<UserBean>() {
            @Override
            public void getSuccess(UserBean result) {
                MyApplication.getInstance().setUser(result);
                MyApplication.editor.putString("account", account);
                MyApplication.editor.putString("password", password);
                MyApplication.editor.commit();
                iLogin.turnToMain();
            }

            @Override
            public void getFailed(String msg) {
                iLogin.loginFailed();
            }
        });
    }
}
