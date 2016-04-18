package com.love_cookies.vote.Presenter;

import com.love_cookies.vote.Model.Biz.FeedBackBiz;
import com.love_cookies.vote.Model.Biz.Interface.CallBack;
import com.love_cookies.vote.View.Interface.IFeedBack;

/**
 * Created by xiekun on 2016/1/14 0014.
 */
public class FeedBackPresenter {

    private FeedBackBiz feedBackBiz;
    private IFeedBack iFeedBack;

    public FeedBackPresenter(IFeedBack iFeedBack) {
        feedBackBiz = new FeedBackBiz();
        this.iFeedBack = iFeedBack;
    }

    public void doFeedBack(int userId, String userName, String feedBack) {
        feedBackBiz.doFeedBack(userId, userName, feedBack, new CallBack<Integer>() {
            @Override
            public void getSuccess(Integer result) {
                iFeedBack.feedBackSuccess();
            }

            @Override
            public void getFailed(String msg) {

            }
        });
    }

}
