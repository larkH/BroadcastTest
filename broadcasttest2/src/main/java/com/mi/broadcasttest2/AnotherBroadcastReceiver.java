package com.mi.broadcasttest2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AnotherBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "received in AnotherBroadcastReceive", Toast.LENGTH_SHORT).show();

        //截断广播，后面的广播接收器将接收不到这条广播
        abortBroadcast();
    }
}