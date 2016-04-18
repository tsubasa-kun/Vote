package com.love_cookies.vote.Presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.love_cookies.vote.R;
import com.love_cookies.vote.Utils.ToastUtils;

/**
 * Created by xiekun on 2016/01/11 0011.
 */
public class NetWorkReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobileInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo activeInfo = manager.getActiveNetworkInfo();
        if(activeInfo != null) {
//            Toast.makeText(context, "mobile:" + mobileInfo.isConnected() + "\n" + "wifi:" + wifiInfo.isConnected()
//                    + "\n" + "active:" + activeInfo.getTypeName(), Toast.LENGTH_SHORT).show();
        }else{
            ToastUtils.show(context, R.string.network_disabled);
        }
    }  //activeInfo为null即是无网络

}