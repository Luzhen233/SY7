package com.example.sy7;

import androidx.appcompat.app.AppCompatActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

public class BroadConnectivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad_connectivity);
        IntentConnectionReceiver tt = new IntentConnectionReceiver();
//        IntentFilter ifilter = new IntentFilter(Intent.ACTION_CONFIGURATION_CHANGED);
//        IntentFilter ifilter = new IntentFilter();
//        ifilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
//        Intent IntentStatus = registerReceiver(tt, ifilter);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        filter.addAction("android.net.wifi.STATE_CHANGE");
        registerReceiver(tt,filter);
//

    }

    public class IntentConnectionReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
//            检查当前活动的网络类型，并确定它是否可以连接到互联网
            ConnectivityManager cm =
                    (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
//            判断是否有网络连接
            if(isConnected){
                Log.d("onReceive: ", "有网");
                //判断当前网络是流量还是WIFI
                boolean isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
                boolean isMobile = activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE;
                if(isWiFi){
                    Log.i("当前网络状态: ", "WIFI");
                }else  if(isMobile){
                    Log.i("当前网络状态: ", "数据流量");
                }
            }else {
                Log.i("onReceive: ","没网");
            }
        }

    }
}