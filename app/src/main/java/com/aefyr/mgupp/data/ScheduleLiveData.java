package com.aefyr.mgupp.data;

import android.content.Context;

import com.aefyr.mgupp.PreferencesHelper;
import com.aefyr.mgupp.api.ApiRequest;
import com.aefyr.mgupp.api.ScheduleGet;
import com.aefyr.mgupp.api.model.Schedule;

/**
 * Created by Aefyr on 11.10.2018.
 */
public class ScheduleLiveData extends StatefulLiveData<Schedule> {
    private Context mContext;

    public ScheduleLiveData(Context c) {
        mContext = c.getApplicationContext();
    }

    @Override
    protected void onActive() {
        if (getState() != State.OK_LIVE && getState() != State.LOADING)
            loadSchedule();
    }

    public void loadSchedule() {
        setState(State.LOADING);
        new ScheduleGet(PreferencesHelper.getInstance(mContext).getScheduleId()).execute(new ApiRequest.Listener<Schedule>() {
            @Override
            public void onSuccess(Schedule schedule) {
                setStateAndValue(State.OK_LIVE, schedule);
            }

            @Override
            public void onError(Exception e) {
                setError(e, 0);
            }
        });
    }
}
