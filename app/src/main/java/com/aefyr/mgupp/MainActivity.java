package com.aefyr.mgupp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.aefyr.mgupp.adapter.DayPickerRecyclerAdapter;
import com.aefyr.mgupp.adapter.LessonsRecyclerAdapter;
import com.aefyr.mgupp.animation.ViewChangeAlphaTransition;
import com.aefyr.mgupp.api.model.Day;
import com.aefyr.mgupp.api.model.Schedule;
import com.aefyr.mgupp.data.ScheduleViewModel;
import com.aefyr.mgupp.util.Util;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private ScheduleViewModel mViewModel;

    private DayPickerRecyclerAdapter mDayPickerAdapter;
    private LessonsRecyclerAdapter mLessonsAdapter;

    private boolean mAnimationNeededOnFirstChange = false; //And it's needed when there's no data available to show instantly and the placeholder will be shown for some time
    private ViewChangeAlphaTransition mDayChangeTransitionMain;
    private ViewChangeAlphaTransition mDayChangeTransitionDayPicker;
    private ViewChangeAlphaTransition mTitleChangeTransition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView lessonsRecycler = findViewById(R.id.rv_weekday_lessons);
        mLessonsAdapter = new LessonsRecyclerAdapter(MainActivity.this);
        lessonsRecycler.setAdapter(mLessonsAdapter);
        lessonsRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        mViewModel = ViewModelProviders.of(this).get(ScheduleViewModel.class);
        mViewModel.getScheduleLiveData().observe(this, new Observer<Schedule>() {
            @Override
            public void onChanged(Schedule schedule) {
                switch (mViewModel.getScheduleLiveData().getState()) {
                    case OK_LIVE:
                        setTitle(getString(R.string.schedule_title_live));
                        if (mViewModel.getSelectedDay() == -1 || mViewModel.getSelectedDay() > schedule.days().size())
                            mViewModel.setSelectedDay(Util.getTodayDayIndex(schedule));

                        updateSelectedDay();
                        updateDayPicker();
                        break;
                    case LOADING:
                        setTitle(getString(R.string.common_syncing));
                        mAnimationNeededOnFirstChange = true;
                        break;
                    default:
                        setTitle(getString(R.string.common_error));
                        break;
                }
            }
        });

        findViewById(R.id.ib_settings).setOnClickListener((v) -> {
            setScheduleId();
        });

        RecyclerView dayPicker = findViewById(R.id.rv_day_picker);
        mDayPickerAdapter = new DayPickerRecyclerAdapter(this);
        mDayPickerAdapter.setOnItemSelectedListener(item -> {
            if (item.pos() == mViewModel.getSelectedDay())
                return;

            mViewModel.setSelectedDay(item.pos());
            updateSelectedDay();
        });

        dayPicker.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        dayPicker.setAdapter(mDayPickerAdapter);
    }

    private void updateSelectedDay() {
        Day day = mViewModel.getScheduleLiveData().getValue().days().get(mViewModel.getSelectedDay());

        if (mDayChangeTransitionMain == null) {
            mDayChangeTransitionMain = new ViewChangeAlphaTransition(findViewById(R.id.container_day), 300);

            if(!mAnimationNeededOnFirstChange) {
                ((TextView) findViewById(R.id.tv_weekday_title)).setText(day.name());
                mLessonsAdapter.setDay(day);
                return;
            }
        }
        mDayChangeTransitionMain.animateViewChange(() -> {
            ((TextView) findViewById(R.id.tv_weekday_title)).setText(day.name());
            mLessonsAdapter.setDay(day);
        });
    }

    private void updateDayPicker() {
        Schedule schedule = mViewModel.getScheduleLiveData().getValue();

        ArrayList<Day> days = schedule.days();
        ArrayList<DayPickerRecyclerAdapter.DayPickerItem> items = new ArrayList<>(days.size());

        for (int i = 0; i < days.size(); i++) {
            Day day = days.get(i);
            items.add(new DayPickerRecyclerAdapter.DayPickerItem(day.shortName(), i));
        }

        if (mDayChangeTransitionDayPicker == null) {
            mDayChangeTransitionDayPicker = new ViewChangeAlphaTransition(findViewById(R.id.rv_day_picker), 300);

            if(!mAnimationNeededOnFirstChange) {
                mDayPickerAdapter.setData(items, mViewModel.getSelectedDay(), Util.getTodayDayIndex(schedule));
                return;
            }
        }

        mDayChangeTransitionDayPicker.animateViewChange(()->{
                mDayPickerAdapter.setData(items, mViewModel.getSelectedDay(), Util.getTodayDayIndex(schedule));
        });
    }

    private void setTitle(String title) {
        ((TextView) findViewById(R.id.tv_schedule_title)).setText(title);
    }

    private void setScheduleId() {
        final AlertDialog dialog = new AlertDialog.Builder(this).setTitle("Enter Schedule ID").setView(R.layout.edit_text_dialog_view).setPositiveButton("ok", null).setNegativeButton("cancel", null).create();
        dialog.show();
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredText = ((EditText) dialog.findViewById(R.id.dialogEditText)).getText().toString();
                PreferencesHelper.getInstance(MainActivity.this).setScheduleId(enteredText);
                mViewModel.updateSchedule();
                dialog.dismiss();
            }
        });
    }


}
