package com.aefyr.mgupp.api;

import android.os.Handler;
import android.os.Looper;

import okhttp3.OkHttpClient;

/**
 * Created by Aefyr on 06.10.2018.
 */
public class Rk {
    private static Rk instance;
    private OkHttpClient mHttpClient;
    private Handler mHandler;

    public static Rk getInstance() {
        return instance == null ? new Rk() : instance;
    }

    private Rk() {
        mHandler = new Handler(Looper.getMainLooper());
        mHttpClient = new OkHttpClient();

        instance = this;
    }

    public OkHttpClient getHttpClient() {
        return mHttpClient;
    }

    public Handler getHandler() {
        return mHandler;
    }
}
