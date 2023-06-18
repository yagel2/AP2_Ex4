package com.example.ap2_ex4;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    public static Context context;

    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
