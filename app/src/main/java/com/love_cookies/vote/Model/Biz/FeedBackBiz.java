package com.love_cookies.vote.Model.Biz;

import android.content.ContentValues;

import com.love_cookies.vote.Model.Biz.Interface.CallBack;
import com.love_cookies.vote.Model.Biz.Interface.IFeedBackBiz;
import com.love_cookies.vote.Presenter.MyApplication;
import com.love_cookies.vote.R;

/**
 * Created by xiekun on 2016/1/14 0014.
 */
public class FeedBackBiz implements IFeedBackBiz {

    @Override
    public void doFeedBack(int userId, String userName, String feedBack, CallBack<Integer> callBack) {
        try {
            ContentValues values = new ContentValues();
            values.put("user_id", userId);
            values.put("user_name", userName);
            values.put("feedback", feedBack);
            values.put("reply_status", 0);
            MyApplication.db.insert("app_feedback", null, values);

            callBack.getSuccess(0);
        } catch (Exception ex) {
            callBack.getFailed(MyApplication.getInstance().getResources().getString(R.string.error));
        }
    }

}
