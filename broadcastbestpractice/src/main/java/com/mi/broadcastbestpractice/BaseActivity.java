package com.mi.broadcastbestpractice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    private ForceOfflineReceive receive;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: RegisterReceiver---" + getClass().getSimpleName());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.mi.broadcastbestpractice.FORCE_OFFLINE");
        receive = new ForceOfflineReceive();
        registerReceiver(receive, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: UnregisterReceiver---" + getClass().getSimpleName());
        if (receive != null) {
            unregisterReceiver(receive);
            receive = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    class ForceOfflineReceive extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Warning");
            builder.setMessage("You are forced to bo offline. Please try to login again.");
            builder.setCancelable(false); //表示对话框不可取消
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCollector.finishAll();
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                }
            });
            builder.show();
        }
    }
}
