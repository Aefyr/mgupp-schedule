package com.aefyr.mgupp.data;

import android.content.Context;

import com.aefyr.mgupp.PreferencesHelper;
import com.aefyr.mgupp.api.ApiRequest;
import com.aefyr.mgupp.api.ScheduleGet;
import com.aefyr.mgupp.api.model.Schedule;

import androidx.lifecycle.LiveData;

/**
 * Created by Aefyr on 11.10.2018.
 */
public class ScheduleLiveData extends LiveData<LoadableData<Schedule>> {
    private Context mContext;

    public ScheduleLiveData(Context c) {
        mContext = c.getApplicationContext();
        setValue(new LoadableData<>());
    }

    @Override
    protected void onActive() {
        if (getValue().getLoadingState() != LoadableData.LoadingState.LOADING && getValue().getDataState() != LoadableData.DataState.OK_LIVE)
            loadSchedule();
    }

    @SuppressWarnings("unchecked")
    void loadSchedule() {
        setValue(getValue()
                .setLoadingState(LoadableData.LoadingState.LOADING));

        new ScheduleGet(PreferencesHelper.getInstance(mContext).getScheduleId()).execute(new ApiRequest.Listener<Schedule>() {
            @Override
            public void onSuccess(Schedule schedule) {
                setValue(getValue()
                        .setLoadingState(LoadableData.LoadingState.LOADED)
                        .setDataState(LoadableData.DataState.OK_LIVE)
                        .setData(schedule));
            }

            @Override
            public void onError(Exception e) {
                setValue(getValue()
                        .setLoadingState(LoadableData.LoadingState.ERROR));
            }
        });
    }
}
