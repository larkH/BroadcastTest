package com.mi.broadcasttest2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //private NetworkChangeReceiver networkChangeReceiver;
    private LocalBroadcastManager localBroadcastManager;
    private LocalReceive localReceive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //动态注册广播接收器
        IntentFilter intentFilter = new IntentFilter();
        /*intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, intentFilter);*/

        localBroadcastManager = LocalBroadcastManager.getInstance(this);//获取本地广播管理者实例
        //注册本地广播接收器
        intentFilter.addAction("com.mi.broadcasttest.LOCAL_BROADCAST");
        localReceive = new LocalReceive();
        localBroadcastManager.registerReceiver(localReceive, intentFilter);


        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent("com.mi.broadcasttest.MY_BROADCAST");
                //intent.putExtra("data", "Android8.0");

                //解决2个module之间发送广播接受不到广播的问题
                /*if (Build.VERSION.SDK_INT >= 26) {
                    intent.addFlags(0x01000000); //FLAG_RECEIVER_INCLUDE_BACKGROUND
                }*/

                //指定广播接收器所在的包名
                //intent.setPackage("com.mi.broadcasttest");
                //sendBroadcast(intent);

                //发送广播到BroadcastReceive2
                //intent.setPackage("com.mi.broadcasttest2");
                //sendBroadcast(intent);

                //发送有序广播
                //sendOrderedBroadcast(intent, null);

                Intent intent = new Intent("com.mi.broadcasttest.LOCAL_BROADCAST");
                localBroadcastManager.sendBroadcast(intent);//发送本地广播
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //unregisterReceiver(networkChangeReceiver);

        localBroadcastManager.unregisterReceiver(localReceive);
    }

    /*class NetworkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //Toast.makeText(context, "network changes", Toast.LENGTH_SHORT).show();

            ConnectivityManager connectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                Toast.makeText(context, "network is available", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "network is unavailable", Toast.LENGTH_SHORT).show();
            }
        }
    }*/

    class LocalReceive extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "received local broadcast", Toast.LENGTH_SHORT).show();
        }
    }
}