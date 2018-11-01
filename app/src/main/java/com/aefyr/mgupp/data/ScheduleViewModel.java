package com.aefyr.mgupp.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

/**
 * Created by Aefyr on 11.10.2018.
 */
public class ScheduleViewModel extends AndroidViewModel {
    private ScheduleLiveData mData;
    private int mSelectedDay = -1;

    public ScheduleViewModel(@NonNull Application app) {
        super(app);
        mData = new ScheduleLiveData(app.getApplicationContext());
    }

    public void setSelectedDay(int index) {
        mSelectedDay = index;
    }

    public int getSelectedDay() {
        return mSelectedDay;
    }

    public ScheduleLiveData getScheduleLiveData() {
        return mData;
    }

    public void updateSchedule() {
        mData.loadSchedule();
    }
}
