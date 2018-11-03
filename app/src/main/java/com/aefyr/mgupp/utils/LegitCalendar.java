package com.aefyr.mgupp.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class LegitCalendar extends GregorianCalendar {

    public static final int MONDAY = 1;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY = 4;
    public static final int FRIDAY = 5;
    public static final int SUNDAY = 6;
    public static final int SATURDAY = 7;

    public LegitCalendar(){
        setFirstDayOfWeek(Calendar.MONDAY);
    }

    @Override
    public int get(int field) {
        int value = super.get(field);
        if(field == DAY_OF_WEEK){
            value--;
            if (value == 0)
                return 7;
            return value;
        }

        return super.get(field);
    }
}
