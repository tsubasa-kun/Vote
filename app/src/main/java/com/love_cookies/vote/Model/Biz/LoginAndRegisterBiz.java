package com.love_cookies.vote.Model.Biz;

import android.content.ContentValues;
import android.database.Cursor;

import com.love_cookies.vote.Model.Bean.UserBean;
import com.love_cookies.vote.Model.Biz.Interface.CallBack;
import com.love_cookies.vote.Model.Biz.Interface.ILoginAndRegisterBiz;
import com.love_cookies.vote.Presenter.MyApplication;
import com.love_cookies.vote.R;

/**
 * Created by xiekun on 2016/1/11 0011.
 */
public class LoginAndRegisterBiz implements ILoginAndRegisterBiz{

    @Override
    public void doLogin(String account, String password, CallBack<UserBean> callBack) {
        try {
            String sql = "SELECT * FROM user WHERE account = ? AND password = ?";
            Cursor cursor = MyApplication.db.rawQuery(sql, new String[]{account, password});
            if(cursor.moveToFirst()){
                UserBean userBean = new UserBean();
                userBean.setId(cursor.getInt(cursor.getColumnIndex("user_id")));
                userBean.setAccount(cursor.getString(cursor.getColumnIndex("account")));
                userBean.setNickname(cursor.getString(cursor.getColumnIndex("nickname")));
                callBack.getSuccess(userBean);
            } else {
                callBack.getFailed(MyApplication.getInstance().getResources().getString(R.string.login_error));
            }
            cursor.close();
        } catch (Exception ex) {
            callBack.getFailed(MyApplication.getInstance().getResources().getString(R.string.error));
        }
    }

    @Override
    public void doRegister(String account, String password, String nickname, CallBack<Integer> callBack) {
        try {
            String sql = "SELECT * FROM user WHERE account = ?";
            Cursor cursor = MyApplication.db.rawQuery(sql, new String[]{account});
            if(cursor.moveToFirst()){
                callBack.getFailed(MyApplication.getInstance().getResources().getString(R.string.re_account_exist));
            } else {
                ContentValues values = new ContentValues();
                values.put("account", account);
                values.put("password", password);
                values.put("nickname", nickname);
                MyApplication.db.insert("user", null, values);
                callBack.getSuccess(0);
            }
            cursor.close();
        } catch (Exception ex) {
            callBack.getFailed(MyApplication.getInstance().getResources().getString(R.string.error));
        }
    }
}
