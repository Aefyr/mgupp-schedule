package com.aefyr.mgupp.api.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Aefyr on 06.10.2018.
 */
public class Schedule {
    //Header
    private String mGroup;
    private String mDegree;
    private String mProfile;
    private String mSemester;
    private Date mSemesterStarts;
    private Date mSemesterEnds;
    private ArrayList<Day> mDays;

    public static class Builder {
        private Schedule mSchedule;

        public Builder() {
            mSchedule = new Schedule();
        }

        public Builder groupName(String name) {
            mSchedule.mGroup = name;
            return this;
        }

        public Builder days(ArrayList<Day> days) {
            mSchedule.mDays = days;

            for (Day d : mSchedule.mDays)
                d.setSchedule(mSchedule);

            return this;
        }

        public Schedule build() {
            return mSchedule;
        }
    }


    public String groupName() {
        return mGroup;
    }

    public String semesterName() {
        return mSemester;
    }

    public ArrayList<Day> days() {
        return mDays;
    }
}
