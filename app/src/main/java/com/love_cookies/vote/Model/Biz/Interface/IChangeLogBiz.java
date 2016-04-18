package com.love_cookies.vote.Model.Biz.Interface;

import android.content.Context;

import com.love_cookies.vote.Model.Bean.ChangeLogBean;


/**
 * Created by xiekun on 2016/01/11 0011.
 */
public interface IChangeLogBiz {
    void getChangeLog(Context context, CallBack<ChangeLogBean> callBack);
}
