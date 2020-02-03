package com.example.r2mind;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

public class LocationService extends Service {
    private Thread mThread;
    private Handler handler;
    private double lat;
    private double lng;

    public LocationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int starId) {
        lat = intent.getDoubleExtra("lat", 0);
        lng = intent.getDoubleExtra("lng", 0);

        handler = new Handler();
        if (mThread == null) {
            mThread = new Thread("my Thread") {
                @Override
                public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "위도 = " + lat + "경도 = " + lng, Toast.LENGTH_SHORT).show();
                            }
                        });
                }
            };
            mThread.start();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mThread != null && mThread.isAlive()) {
            mThread.interrupt();
            mThread = null;
        }
    }

}