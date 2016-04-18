package com.love_cookies.vote.Model.Biz.Interface;

/**
 * Created by xiekun on 2016/01/11 0011.
 */
public interface CallBack<T> {
    void getSuccess(T result);
    void getFailed(String msg);
}
