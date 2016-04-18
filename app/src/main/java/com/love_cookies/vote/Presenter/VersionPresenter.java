package com.love_cookies.vote.Presenter;

import android.content.Context;

import com.love_cookies.vote.Model.Bean.ChangeLogBean;
import com.love_cookies.vote.Model.Biz.ChangeLogBiz;
import com.love_cookies.vote.Model.Biz.Interface.CallBack;
import com.love_cookies.vote.View.Interface.IVersion;


/**
 * Created by xiekun on 2016/01/11 0011.
 */
public class VersionPresenter {

    private ChangeLogBiz changeLogBiz;
    private IVersion iVersion;

    public VersionPresenter(IVersion iVersion) {
        changeLogBiz = new ChangeLogBiz();
        this.iVersion = iVersion;
    }

    public void getChangeLog(Context context) {
        changeLogBiz.getChangeLog(context, new CallBack<ChangeLogBean>() {
            @Override
            public void getSuccess(ChangeLogBean result) {
                iVersion.initChangeLog(result);
            }

            @Override
            public void getFailed(String msg) {
                iVersion.onFailed();
            }
        });
    }

}
