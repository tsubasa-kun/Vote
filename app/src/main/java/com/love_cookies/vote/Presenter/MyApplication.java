package com.love_cookies.vote.Presenter;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.love_cookies.vote.Model.Bean.UserBean;
import com.love_cookies.vote.R;
import com.love_cookies.vote.Utils.ToastUtils;

import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by xiekun on 2015/11/25 0025.
 */
public class MyApplication extends Application {

    public static final String DATABASE_NAME = "vote.db";
    public static final String DATABASE_PATH = "/data/data/com.love_cookies.vote/databases";
    public static SQLiteDatabase db;

    private static Context context;
    private static MyApplication instance;

    public static UserBean user;

    public static SharedPreferences sp;
    public static SharedPreferences.Editor editor;

    /**
     * 获取当前的Application
     *
     * @return Application
     */
    public static MyApplication getInstance() {
        if (instance == null) {
            instance = new MyApplication();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = getApplicationContext();
        checkNetwork();
        getDB();
        getSP();
        getSPEditor();
        x.Ext.init(this);
        x.Ext.setDebug(true);
    }

    //检查网络
    public boolean checkNetwork() {
        if ((!isNetworkConnected(context)) && (!isWifiConnected(context)) && (!isMobileConnected(context))) {
            ToastUtils.show(context, R.string.network_disabled);
            return false;
        } else {
            return true;
        }
    }

    //判断是否有网络连接
    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    //判断WIFI网络是否可用
    public boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    //判断MOBILE网络是否可用
    public boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public void getDB() {
        String databaseFilename = DATABASE_PATH + "/" + DATABASE_NAME;
        File dir = new File(DATABASE_PATH);
        if (!dir.exists())
            dir.mkdir();
        if (!(new File(databaseFilename)).exists()) {
            InputStream is = getResources().openRawResource(R.raw.vote);
            try {
                FileOutputStream fos = new FileOutputStream(databaseFilename);
                byte[] buffer = new byte[8192];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        db = SQLiteDatabase.openOrCreateDatabase(databaseFilename, null);
    }

    public void getSP() {
        sp = getSharedPreferences("data", MODE_PRIVATE);
    }

    public void getSPEditor() {
        editor = getSharedPreferences("data", MODE_PRIVATE).edit();
    }

    public void setUser(UserBean userBean) {
        user = userBean;
    }

}
