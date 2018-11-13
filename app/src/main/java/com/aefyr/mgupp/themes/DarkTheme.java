package com.aefyr.mgupp.themes;

import android.content.Context;
import android.content.res.Resources;

import com.aefyr.mgupp.R;
import com.aefyr.mgupp.themeengine.core.ThemeColor;

public class DarkTheme extends HardcodedTheme {
    private static DarkTheme sInstance;

    private int BACKGROUND;
    private int ACCENT;
    private int TEXT_DARK;
    private int TEXT_LIGHT;

    public static DarkTheme getInstance(Context c){
        return sInstance == null? new DarkTheme(c):sInstance;
    }

    private DarkTheme(Context c){
        Resources res = c.getResources();
        BACKGROUND = res.getColor(R.color.colorAlmostBlack);
        ACCENT = res.getColor(R.color.colorAccent);
        TEXT_DARK = res.getColor(R.color.colorPureWhite);
        TEXT_LIGHT = res.getColor(R.color.colorLightGray);

        sInstance = this;
    }

    @Override
    protected void fillThemeWithColors() {
        setColor(ThemeColor.actionBarBackground, BACKGROUND);
        setColor(ThemeColor.actionBarTitle, TEXT_DARK);
        setColor(ThemeColor.actionBarIconSettings, ACCENT);
        setColor(ThemeColor.actionBarIconDarkMode, ACCENT);

        setColor(ThemeColor.windowBackground, BACKGROUND);

        setColor(ThemeColor.statusBarColor, BACKGROUND);

        setColor(ThemeColor.lessonTitleColor, TEXT_DARK);
        setColor(ThemeColor.lessonTimesColor, TEXT_DARK);
        setColor(ThemeColor.lessonInfoColor, TEXT_LIGHT);

        setColor(ThemeColor.weekdayBackground, BACKGROUND);
        setColor(ThemeColor.weekdayTitle, TEXT_DARK);

        setColor(ThemeColor.dayPickerBackground, BACKGROUND);
        setColor(ThemeColor.dayPickerDayTitleActive, ACCENT);
        setColor(ThemeColor.dayPickerDayTitleInactive, TEXT_DARK);
        setColor(ThemeColor.dayPickerSelectedDayIndicator, ACCENT);
    }
}
