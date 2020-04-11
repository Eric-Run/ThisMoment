package com.dgut.moment.Receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.dgut.moment.R;

import androidx.core.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

public class ClockReceiver extends BroadcastReceiver {
    private static final String TAG = "ClockReceiver";
    public static final String EXTRA_EVENT_ID = "extra.event.id";
    public static final String EXTRA_EVENT_REMIND_TIME = "extra.event.remind.time";
    public static final String EXTRA_EVENT = "extra.event";

    @Override
    public void onReceive(Context context, Intent intent) {
        if ("com.example.myapp.RING".equals(intent.getAction())) {

            Log.d(TAG, "onReceive");
//        WakeLockUtil.wakeUpAndUnlock();
            postToClockActivity(context, intent);
        }
    }

    private void postToClockActivity(Context context, Intent intent) {

        Log.d(TAG,"receive a message");
        NotificationManager manager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);

        Notification notification = new NotificationCompat.Builder(context,"messege")
                .setContentTitle("@王嘉尔Jackson 给你发来一条私信")
                .setContentText("点进来！看嘉尔给你的一个小惊喜 :)")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .build();
        manager.notify(1,notification);
    }

    public ClockReceiver() {
        super();
        Log.d(TAG, "ClockReceiver: Constructor");
    }
}
