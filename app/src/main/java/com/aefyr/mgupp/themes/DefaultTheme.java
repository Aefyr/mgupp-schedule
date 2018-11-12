package com.aefyr.mgupp.themes;

import com.aefyr.mgupp.themeengine.core.ThemeColor;

public class DefaultTheme extends HardcodedTheme {
    private static final int BACKGROUND = 0xffffffff;
    private static final int ACCENT = 0xffd81b60;
    private static final int TEXT_DARK = 0xff212121;
    private static final int TEXT_LIGHT = 0xff616161;

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
