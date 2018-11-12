package com.aefyr.mgupp.themes;

import android.graphics.Color;

import com.aefyr.mgupp.themeengine.core.CustomTheme;
import com.aefyr.mgupp.themeengine.core.ThemeCore;

import java.util.HashMap;
import java.util.Map;

abstract class HardcodedTheme extends CustomTheme {
    private HashMap<String, Integer> mColors;

    public HardcodedTheme() {
        ThemeCore.getInstance().setAnimationEnabled(true);
        mColors = new HashMap<>();
        fillThemeWithColors();
    }

    @Override
    public int getColor(String colorName) {
        if (!mColors.containsKey(colorName))
            return Color.MAGENTA;

        return mColors.get(colorName);
    }

    public void setColor(String colorName, int color) {
        int oldColor = getColor(colorName);
        mColors.put(colorName, color);
        notifyColorChanged(colorName, oldColor, color);
    }

    public void setColorWithoutAnimation(String colorName, int color) {
        mColors.put(colorName, color);
        notifyColorChanged(colorName, color);
    }

    @Override
    public Map<String, Integer> getAllColors() {
        return mColors;
    }

    protected abstract void fillThemeWithColors();
}
