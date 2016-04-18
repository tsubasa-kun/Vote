package com.love_cookies.vote.Model.Biz.Interface;

import com.love_cookies.vote.Model.Bean.UserBean;

/**
 * Created by xiekun on 2016/1/11 0011.
 */
public interface ILoginAndRegisterBiz {
    void doLogin(String account, String password, CallBack<UserBean> callBack);
    void doRegister(String account, String password, String nickname, CallBack<Integer> callBack);
}
