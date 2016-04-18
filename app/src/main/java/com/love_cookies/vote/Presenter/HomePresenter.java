package com.love_cookies.vote.Presenter;

import com.love_cookies.vote.Model.Biz.Interface.CallBack;
import com.love_cookies.vote.Model.Biz.UpdateBiz;
import com.love_cookies.vote.View.Interface.IHome;

/**
 * Created by xiekun on 2016/1/15 0015.
 */
public class HomePresenter {

    private UpdateBiz updateBiz;
    private IHome iHome;

    public HomePresenter(IHome iHome) {
        updateBiz = new UpdateBiz();
        this.iHome = iHome;
    }

    public void doLogout() {
        MyApplication.editor.clear();
        MyApplication.editor.commit();
        iHome.logout();
    }

    public void doUpdate() {
        updateBiz.doCheck(new CallBack<String>() {
            @Override
            public void getSuccess(String result) {
                iHome.update(result);
            }

            @Override
            public void getFailed(String msg) {

            }
        });
    }
}
