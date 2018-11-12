package com.aefyr.mgupp.themeengine.core;

public interface ThemeChangeObserver {

    void onColorChanged(String colorName, int color);

    String[] getObservedColors();

}
