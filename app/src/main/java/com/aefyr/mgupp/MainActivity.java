package com.aefyr.mgupp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.aefyr.mgupp.adapters.DayPickerAdapter;
import com.aefyr.mgupp.adapters.LessonsAdapter;
import com.aefyr.mgupp.animations.ViewChangeAlphaTransition;
import com.aefyr.mgupp.api.model.Day;
import com.aefyr.mgupp.api.model.Schedule;
import com.aefyr.mgupp.data.ScheduleViewModel;
import com.aefyr.mgupp.themeengine.ThemedActivity;
import com.aefyr.mgupp.themeengine.core.ThemeColor;
import com.aefyr.mgupp.themeengine.core.ThemeCore;
import com.aefyr.mgupp.themes.DarkTheme;
import com.aefyr.mgupp.themes.DefaultTheme;
import com.aefyr.mgupp.themes.RgbTheme;
import com.aefyr.mgupp.utils.PreferencesHelper;
import com.aefyr.mgupp.utils.Utils;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends ThemedActivity {

    private ScheduleViewModel mViewModel;

    private DayPickerAdapter mDayPickerAdapter;
    private LessonsAdapter mLessonsAdapter;

    private boolean mAnimationNeededOnFirstChange = false; //And it's needed when there's no data available to show instantly and the placeholder will be shown for some time
    private ViewChangeAlphaTransition mDayChangeTransitionMain;
    private ViewChangeAlphaTransition mDayChangeTransitionDayPicker;
    private ViewChangeAlphaTransition mTitleChangeTransition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (PreferencesHelper.getInstance(this).isDarkModeEnabled())
            ThemeCore.getInstance().setTheme(new DarkTheme());
        else
            ThemeCore.getInstance().setTheme(new DefaultTheme());

        ThemeCore.getInstance().setAnimationEnabled(true);

        RecyclerView lessonsRecycler = findViewById(R.id.rv_weekday_lessons);
        mLessonsAdapter = new LessonsAdapter(MainActivity.this);
        lessonsRecycler.setAdapter(mLessonsAdapter);
        lessonsRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        mViewModel = ViewModelProviders.of(this).get(ScheduleViewModel.class);

        mViewModel.getScheduleLiveData().observe(this, (scheduleLoadableData) -> {
            switch (scheduleLoadableData.getLoadingState()) {
                case LOADING:
                    setTitle(getString(R.string.common_syncing));
                    mAnimationNeededOnFirstChange = true;
                    break;
                case LOADED:
                    dataChanged();
                    break;
                case ERROR:
                    setTitle(getString(R.string.common_error));
                    break;
            }
        });

        findViewById(R.id.ib_settings).setOnClickListener((v) -> {
            setScheduleId();
        });

        findViewById(R.id.ib_dark_mode).setOnClickListener((v -> {
            if (ThemeCore.getInstance().getTheme() instanceof DefaultTheme) {
                ThemeCore.getInstance().setTheme(new DarkTheme());
                PreferencesHelper.getInstance(this).setDarkModeEnabled(true);
            } else {
                ThemeCore.getInstance().setTheme(new DefaultTheme());
                PreferencesHelper.getInstance(this).setDarkModeEnabled(false);
            }
        }));

        findViewById(R.id.ib_dark_mode).setOnLongClickListener((v -> {
            ThemeCore.getInstance().setTheme(new RgbTheme());
            return true;
        }));

        RecyclerView dayPicker = findViewById(R.id.rv_day_picker);
        mDayPickerAdapter = new DayPickerAdapter(this);
        mDayPickerAdapter.setOnItemSelectedListener(item -> {
            if (item.pos() == mViewModel.getSelectedDay())
                return;

            mViewModel.setSelectedDay(item.pos());
            updateSelectedDay();
        });

        dayPicker.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        dayPicker.setAdapter(mDayPickerAdapter);
    }

    private void dataChanged() {
        setTitle(getString(R.string.schedule_title_live));

        if (mViewModel.getSelectedDay() == -1 || mViewModel.getSelectedDay() > getScheduleFromViewModel().days().size()) {
            int todayIndex = Utils.getTodayDayIndex(getScheduleFromViewModel());
            if (todayIndex == -1)
                mViewModel.setSelectedDay(0);
            else
                mViewModel.setSelectedDay(todayIndex);
        }

        updateSelectedDay();
        updateDayPicker();
    }

    private void updateSelectedDay() {
        Day day = getScheduleFromViewModel().days().get(mViewModel.getSelectedDay());

        if (mDayChangeTransitionMain == null) {
            mDayChangeTransitionMain = new ViewChangeAlphaTransition(findViewById(R.id.container_day), 300);

            if (!mAnimationNeededOnFirstChange) {
                ((TextView) findViewById(R.id.tv_weekday_title)).setText(day.name());
                mLessonsAdapter.setDay(day, Utils.isCurrentWeekOdd(getScheduleFromViewModel()));
                return;
            }
        }
        mDayChangeTransitionMain.animateViewChange(() -> {
            ((TextView) findViewById(R.id.tv_weekday_title)).setText(day.name());
            mLessonsAdapter.setDay(day, Utils.isCurrentWeekOdd(getScheduleFromViewModel()));
        });
    }

    private void updateDayPicker() {
        Schedule schedule = getScheduleFromViewModel();

        ArrayList<Day> days = schedule.days();
        ArrayList<DayPickerAdapter.DayPickerItem> items = new ArrayList<>(days.size());

        for (int i = 0; i < days.size(); i++) {
            Day day = days.get(i);
            items.add(new DayPickerAdapter.DayPickerItem(day.shortName(), i));
        }

        if (mDayChangeTransitionDayPicker == null) {
            mDayChangeTransitionDayPicker = new ViewChangeAlphaTransition(findViewById(R.id.rv_day_picker), 300);

            if (!mAnimationNeededOnFirstChange) {
                mDayPickerAdapter.setData(items, mViewModel.getSelectedDay(), Utils.getTodayDayIndex(schedule));
                return;
            }
        }

        mDayChangeTransitionDayPicker.animateViewChange(() -> {
            mDayPickerAdapter.setData(items, mViewModel.getSelectedDay(), Utils.getTodayDayIndex(schedule));
        });
    }

    private void setTitle(String title) {
        ((TextView) findViewById(R.id.tv_schedule_title)).setText(title);
    }

    private Schedule getScheduleFromViewModel() {
        return mViewModel.getScheduleLiveData().getValue().getData();
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

    @Override
    public void onColorChanged(String colorName, int color) {
        switch (colorName) {
            case ThemeColor.statusBarColor:
                setStatusBarColor(color);
                break;
            case ThemeColor.windowBackground:
                findViewById(R.id.container_main).setBackgroundColor(color);
                break;
            case ThemeColor.actionBarBackground:
                findViewById(R.id.toolbar).setBackgroundColor(color);
                break;
            case ThemeColor.actionBarIconSettings:
                ((ImageButton) findViewById(R.id.ib_settings)).setColorFilter(color);
                break;
            case ThemeColor.actionBarIconDarkMode:
                ((ImageButton) findViewById(R.id.ib_dark_mode)).setColorFilter(color);
                break;
            case ThemeColor.actionBarTitle:
                ((TextView) findViewById(R.id.tv_schedule_title)).setTextColor(color);
                break;
            case ThemeColor.weekdayBackground:
                ((CardView) findViewById(R.id.container_lessons)).setCardBackgroundColor(color);
                break;
            case ThemeColor.weekdayTitle:
                ((TextView) findViewById(R.id.tv_weekday_title)).setTextColor(color);
                break;
            case ThemeColor.dayPickerBackground:
                ((CardView) findViewById(R.id.container_day_picker)).setCardBackgroundColor(color);
                break;

        }
    }

    @Override
    public String[] getObservedColors() {
        return new String[]{ThemeColor.actionBarBackground, ThemeColor.actionBarTitle, ThemeColor.actionBarIconSettings, ThemeColor.actionBarIconDarkMode,
                ThemeColor.windowBackground, ThemeColor.statusBarColor, ThemeColor.weekdayBackground, ThemeColor.weekdayTitle, ThemeColor.dayPickerBackground};
    }




    /*@Override
    public void onThemeChanged(ThemeColor color) {
        switch (color){
            case TOOLBAR_BACKGROUND:
                findViewById(R.id.toolbar).setBackgroundColor(ThemeCore.getInstance().getColor(ThemeColor.TOOLBAR_BACKGROUND));
                break;
            case TOOLBAR_TEXT:
                ((TextView)findViewById(R.id.tv_schedule_title)).setTextColor(ThemeCore.getInstance().getColor(ThemeColor.TOOLBAR_TEXT));
                break;
        }
    }

    @Override
    public ThemeColor[] getColors() {
        return new ThemeColor[]{ThemeColor.TOOLBAR_BACKGROUND, ThemeColor.TOOLBAR_TEXT};
    }*/
}
