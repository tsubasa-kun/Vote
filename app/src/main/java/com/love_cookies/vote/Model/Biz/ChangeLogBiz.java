package com.love_cookies.vote.Model.Biz;

import android.content.Context;

import com.google.gson.Gson;
import com.love_cookies.vote.Model.Bean.ChangeLogBean;
import com.love_cookies.vote.Model.Biz.Interface.CallBack;
import com.love_cookies.vote.Model.Biz.Interface.IChangeLogBiz;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by xiekun on 2016/01/11 0011.
 */
public class ChangeLogBiz implements IChangeLogBiz {

    Gson gson = new Gson();

    @Override
    public void getChangeLog(Context context, CallBack<ChangeLogBean> callBack) {
        String changeLog;
        try {
            InputStream inputStream = context.getResources().getAssets().open("change_log.json");
            byte [] buffer = new byte[inputStream.available()] ;
            inputStream.read(buffer);
            changeLog = new String(buffer,"utf-8");

            callBack.getSuccess(gson.fromJson(changeLog, ChangeLogBean.class));
        } catch (IOException e) {
            e.printStackTrace();
            callBack.getFailed(e.getMessage());
        }
    }
}
