package com.aefyr.mgupp.themes;

import android.animation.ValueAnimator;
import android.graphics.Color;

import com.aefyr.mgupp.themeengine.core.ThemeColor;

public class RgbTheme extends HardcodedTheme {
    private static final int BACKGROUND = 0xff212121;
    private static final int ACCENT = 0xffd81b60;
    private static final int TEXT_DARK = 0xffffffff;
    private static final int TEXT_LIGHT = 0xff616161;

    private float[] hsv = {0, 0.5f, 1};

    @Override
    public void onApplied() {
        ValueAnimator rgbAnimator = ValueAnimator.ofFloat(0, 360);
        rgbAnimator.addUpdateListener((a) -> {
            hsv[0] = (float) a.getAnimatedValue();
            int color = Color.HSVToColor(hsv);
            setColorWithoutAnimation(ThemeColor.actionBarIconSettings, color);
            setColorWithoutAnimation(ThemeColor.actionBarIconDarkMode, color);
            setColorWithoutAnimation(ThemeColor.dayPickerDayTitleActive, color);
            setColorWithoutAnimation(ThemeColor.dayPickerSelectedDayIndicator, color);
        });
        rgbAnimator.setRepeatMode(ValueAnimator.RESTART);
        rgbAnimator.setRepeatCount(ValueAnimator.INFINITE);
        rgbAnimator.setDuration(8000);
        rgbAnimator.start();
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
