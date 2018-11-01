package com.aefyr.mgupp.api.model;

import com.aefyr.mgupp.api.ApiUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Aefyr on 06.10.2018.
 */
public class Lesson {
    private static SimpleDateFormat sLessonTimesSDF = new SimpleDateFormat("HH:mm", Locale.getDefault());

    private String mName;
    private String mRoom;
    private Date mStart;
    private Date mEnd;
    private String mTeacher;
    private boolean mAlternating;
    private boolean mOdd;

    public Lesson(String name, String room, String rawTimes, String teacher) throws Exception {
        if (name.contains("(II)")) {
            mAlternating = true;
            mOdd = false;
        }
        if (name.contains("(I)")) {
            mAlternating = true;
            mOdd = true;
        }
        mName = ApiUtils.cleanString(name);
        mName = mName.replaceAll("\\(I.*?\\) ", "")
                .substring(1);

        mRoom = ApiUtils.cleanString(room).trim();

        mTeacher = teacher;

        rawTimes = rawTimes.replaceAll(" ", "");
        String[] rawTimesA = rawTimes.split("-");
        mStart = sLessonTimesSDF.parse(rawTimesA[0]);
        mEnd = sLessonTimesSDF.parse(rawTimesA[1]);
    }

    public String name() {
        return mName;
    }

    public boolean alternating() {
        return mAlternating;
    }

    public boolean odd() {
        return mOdd;
    }

    public String room() {
        return mRoom;
    }

    public Date starts() {
        return mStart;
    }

    public Date ends() {
        return mEnd;
    }

    public String teacher() {
        return mTeacher;
    }

}
