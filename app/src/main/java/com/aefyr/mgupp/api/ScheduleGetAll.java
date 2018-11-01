package com.aefyr.mgupp.api;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Response;

/**
 * Created by Aefyr on 06.10.2018.
 */
public class ScheduleGetAll extends ApiRequest<String> {

    public ScheduleGetAll() {
        super("http://www.mgupp.ru/obuchayushchimsya/raspisanie/");
    }

    @Override
    protected String parse(Response response) throws Exception {
        String s = response.body().string();
        long start = System.currentTimeMillis();
        Pattern p = Pattern.compile(".*?<select class=\"soflow\" id=\"Years\" name=\"Years\" >(.+?)</select>.*?");
        Matcher m = p.matcher(s);
        m.find();
        Log.d("MGUPP", "regex in " + (System.currentTimeMillis() - start));
        if (s.contains("2018/2019"))
            Log.d("MGUPP", "OPA");

        return m.group(1);
    }
}
