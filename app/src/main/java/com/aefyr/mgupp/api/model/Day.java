package com.aefyr.mgupp.api.model;

import com.aefyr.mgupp.util.StringUtil;
import com.aefyr.mgupp.util.Util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Aefyr on 06.10.2018.
 */
public class Day {
    private static SimpleDateFormat sDayNameParseSDF = new SimpleDateFormat("EEEE", new Locale("ru"));
    private static SimpleDateFormat sDayNameFormatSDF = new SimpleDateFormat("EEEE", Locale.getDefault());
    private static SimpleDateFormat sDayNameFormatShortSDF = new SimpleDateFormat("EE", Locale.getDefault());

    private Schedule mSchedule;
    private Date mDate;
    private String mName;
    private ArrayList<Lesson> mLessons;


    public Day(String name, ArrayList<Lesson> lessons) throws Exception {
        mName = name;
        mLessons = lessons;

        mDate = sDayNameParseSDF.parse(name);
    }

    public void setSchedule(Schedule schedule){
        mSchedule = schedule;
    }

    public String name() {
        return StringUtil.capitalize(sDayNameFormatSDF.format(mDate));
    }

    public String shortName() {
        return sDayNameFormatShortSDF.format(mDate).toUpperCase();
    }

    public String rawName() {
        return mName;
    }

    public Date weekdayDate() {
        return mDate;
    }

    public ArrayList<Lesson> allLessons(){
        return mLessons;
    }

    public ArrayList<Lesson> lessons() {
        return lessons(Util.isCurrentWeekOdd(mSchedule));
    }

    public ArrayList<Lesson> lessons(boolean odd) {
        ArrayList<Lesson> lessons = new ArrayList<>();

        for (Lesson l : mLessons) {
            if (l.alternating()) {
                if (odd && l.odd())
                    lessons.add(l);
                if (!odd && !l.odd())
                    lessons.add(l);
            } else {
                lessons.add(l);
            }
        }

        return lessons;
    }
}
