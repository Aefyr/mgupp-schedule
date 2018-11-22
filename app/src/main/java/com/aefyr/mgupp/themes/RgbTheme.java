package com.aefyr.mgupp.themes;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;

import com.aefyr.mgupp.R;
import com.aefyr.mgupp.themeengine.core.ThemeColor;

public class RgbTheme extends HardcodedTheme {
    private static RgbTheme sInstance;

    private int BACKGROUND;
    private int ACCENT;
    private int TEXT_DARK;
    private int TEXT_LIGHT;

    private float[] hsv = {0, 0.5f, 1};
    private ValueAnimator mAnimator;

    public static RgbTheme getInstance(Context c) {
        return sInstance == null ? new RgbTheme(c) : sInstance;
    }

    private RgbTheme(Context c) {
        Resources res = c.getResources();
        BACKGROUND = res.getColor(R.color.colorAlmostBlack);
        ACCENT = res.getColor(R.color.colorAccent);
        TEXT_DARK = res.getColor(R.color.colorPureWhite);
        TEXT_LIGHT = res.getColor(R.color.colorLightGray);

        sInstance = this;
    }

    @Override
    public void onApplied() {
        super.onApplied();

        mAnimator = ValueAnimator.ofFloat(0, 360);
        mAnimator.addUpdateListener((a) -> {
            hsv[0] = (float) a.getAnimatedValue();
            int color = Color.HSVToColor(hsv);
            setColorWithoutAnimation(ThemeColor.actionBarIconSettings, color);
            setColorWithoutAnimation(ThemeColor.actionBarIconDarkMode, color);
            setColorWithoutAnimation(ThemeColor.dayPickerDayTitleActive, color);
            setColorWithoutAnimation(ThemeColor.dayPickerSelectedDayIndicator, color);
        });
        mAnimator.setRepeatMode(ValueAnimator.RESTART);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setDuration(8000);
        mAnimator.start();
    }

    @Override
    public void onRemoved() {
        super.onRemoved();
        mAnimator.end();
        mAnimator = null;
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
