package com.aefyr.mgupp.api;

import android.util.Log;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Aefyr on 06.10.2018.
 */
public abstract class ApiRequest<T> {
    private String mUrl;

    public interface Listener<T> {
        void onSuccess(T t);

        void onError(Exception e);
    }

    public ApiRequest(String url) {
        mUrl = url;
    }

    public T executeSync() throws Exception {
        return parse(Rk.getInstance().getHttpClient().newCall(new Request.Builder().url(mUrl).build()).execute());
    }

    public void execute(final Listener<T> listener) {
        new Thread(() -> {
            try {
                final T result = executeSync();
                Rk.getInstance().getHandler().post(() -> listener.onSuccess(result));
            } catch (Exception e) {
                Rk.getInstance().getHandler().post(() -> listener.onError(e));
                Log.w("MGUPP", e);
            }
        }).start();
    }

    protected abstract T parse(Response response) throws Exception;
}
