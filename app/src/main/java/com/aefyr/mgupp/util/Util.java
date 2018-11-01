package com.aefyr.mgupp.util;

import com.aefyr.mgupp.api.model.Day;
import com.aefyr.mgupp.api.model.Schedule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Aefyr on 07.10.2018.
 */
public class Util {

    public static boolean isCurrentWeekOdd() {
        Calendar c = Calendar.getInstance();
        c.set(c.get(Calendar.MONTH) >= Calendar.SEPTEMBER ? c.get(Calendar.YEAR) : c.get(Calendar.YEAR) - 1, Calendar.SEPTEMBER, 1);

        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
            c.add(Calendar.DAY_OF_WEEK, 2);

        int firstWeekNumber = c.get(Calendar.WEEK_OF_YEAR); //The first week in the academic year, counts as odd
        c.setTime(new Date());

        return (c.get(Calendar.WEEK_OF_YEAR) - firstWeekNumber) % 2 == 0;
    }

    public static int getTodayDayIndex(Schedule schedule) {
        ArrayList<Day> days = schedule.days();
        Calendar calendar = Calendar.getInstance();
        int today = calendar.get(Calendar.DAY_OF_WEEK);

        for (int i = 0; i < days.size(); i++) {
            calendar.setTime(days.get(i).weekdayDate());
            if (today == calendar.get(Calendar.DAY_OF_WEEK))
                return i;
        }
        return 0;
    }

}
