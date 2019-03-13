package com.example.irprogramtest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private Notification.Builder myNotif;
    public final static int myNotifId = 32657;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myNotif = new Notification.Builder(this);
        myNotif.setAutoCancel(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void onBtnNotifClicked(View v){
        myNotif.setSmallIcon(R.mipmap.ic_launcher);
        myNotif.setTicker("This is my Ticker");
        myNotif.setWhen(System.currentTimeMillis());
        myNotif.setContentTitle("This is my title");
        myNotif.setContentText("This is my text");
        Intent[] i = new Intent[]{new Intent(this,MainActivity.class)};
        PendingIntent pi = PendingIntent.getActivities(this,0,i,
                PendingIntent.FLAG_UPDATE_CURRENT);
        myNotif.setContentIntent(pi);

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(myNotifId,myNotif.build());
    }
}
