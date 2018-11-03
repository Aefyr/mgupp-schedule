package com.aefyr.mgupp.utils;

import com.aefyr.mgupp.api.model.Day;
import com.aefyr.mgupp.api.model.Schedule;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Aefyr on 07.10.2018.
 */
public class Utils {

    public static boolean isCurrentWeekOdd(Schedule schedule) {
        LegitCalendar c = new LegitCalendar();
        c.set(c.get(LegitCalendar.MONTH) >= LegitCalendar.SEPTEMBER ? c.get(LegitCalendar.YEAR) : c.get(LegitCalendar.YEAR) - 1, LegitCalendar.SEPTEMBER, 1);

        if (c.get(LegitCalendar.DAY_OF_WEEK) == LegitCalendar.SATURDAY || c.get(LegitCalendar.DAY_OF_WEEK) == LegitCalendar.SUNDAY)
            c.add(LegitCalendar.DAY_OF_WEEK, 2);

        int firstWeekNumber = c.get(LegitCalendar.WEEK_OF_YEAR); //The first week in the academic year, counts as odd
        c.setTime(new Date());
        boolean odd = (c.get(LegitCalendar.WEEK_OF_YEAR) - firstWeekNumber) % 2 == 0;

        //This checks whether actual week has no more days with lessons left and next week should count as current instead
        int todayWeekday = c.get(LegitCalendar.DAY_OF_WEEK);
        c.setTime(schedule.days().get(schedule.days().size()-1).weekdayDate());
        if(todayWeekday >c.get(LegitCalendar.DAY_OF_WEEK)) {
            return !odd;
        }

        return odd;
    }

    public static int getTodayDayIndex(Schedule schedule) {
        ArrayList<Day> days = schedule.days();
        LegitCalendar calendar = new LegitCalendar();
        int today = calendar.get(LegitCalendar.DAY_OF_WEEK);

        for (int i = 0; i < days.size(); i++) {
            calendar.setTime(days.get(i).weekdayDate());
            if (today == calendar.get(LegitCalendar.DAY_OF_WEEK))
                return i;
        }
        return -1;
    }

}
