package com.love_cookies.vote.Presenter;

import com.love_cookies.vote.Model.Biz.Interface.CallBack;
import com.love_cookies.vote.Model.Biz.LoginAndRegisterBiz;
import com.love_cookies.vote.View.Interface.IRegister;

/**
 * Created by xiekun on 2016/1/11 0011.
 */
public class RegisterPresenter {

    private LoginAndRegisterBiz loginAndRegisterBiz;
    private IRegister iRegister;

    public RegisterPresenter(IRegister iRegister) {
        loginAndRegisterBiz = new LoginAndRegisterBiz();
        this.iRegister = iRegister;
    }

    public void doRegister(String account, String password, String nickname) {
        loginAndRegisterBiz.doRegister(account, password, nickname, new CallBack<Integer>() {
            @Override
            public void getSuccess(Integer result) {
                iRegister.registerSuccess();
            }

            @Override
            public void getFailed(String msg) {
                iRegister.registerFailed(msg);
            }
        });
    }

}
