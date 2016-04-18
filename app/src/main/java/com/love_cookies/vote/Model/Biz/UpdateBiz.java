package com.love_cookies.vote.Model.Biz;

import android.database.Cursor;

import com.love_cookies.vote.Model.Biz.Interface.CallBack;
import com.love_cookies.vote.Model.Biz.Interface.IUpdateBiz;
import com.love_cookies.vote.Presenter.MyApplication;
import com.love_cookies.vote.R;

/**
 * Created by xiekun on 2016/1/19 0019.
 */
public class UpdateBiz implements IUpdateBiz {

    @Override
    public void doCheck(CallBack<String> callBack) {
        try {
            String version = "0.0.0";
            Cursor cursor = MyApplication.db.query("app_version", null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                version = cursor.getString(0);
            }
            cursor.close();

            callBack.getSuccess(version);
        } catch (Exception ex) {
            callBack.getFailed(MyApplication.getInstance().getResources().getString(R.string.error));
        }
    }

}
