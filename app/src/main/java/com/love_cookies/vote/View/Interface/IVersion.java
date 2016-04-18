package com.love_cookies.vote.View.Interface;

import com.love_cookies.vote.Model.Bean.ChangeLogBean;

/**
 * Created by xiekun on 2016/01/11 0011.
 */
public interface IVersion {
    void initListView();
    String getAppVersion();
    void initChangeLog(ChangeLogBean changeLogBean);
    void onFailed();
}
