package com.love_cookies.vote.Model.Biz.Interface;

/**
 * Created by xiekun on 2016/1/14 0014.
 */
public interface IFeedBackBiz {
    void doFeedBack(int userId, String userName, String feedBack, CallBack<Integer> callBack);
}
