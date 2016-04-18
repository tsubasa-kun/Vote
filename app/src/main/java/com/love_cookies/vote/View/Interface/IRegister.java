package com.love_cookies.vote.View.Interface;

/**
 * Created by xiekun on 2016/1/11 0011.
 */
public interface IRegister {
    void initView();
    void doRegister();
    void registerSuccess();
    void registerFailed(String msg);
}
