package com.aefyr.mgupp.utils;

public class MathUtils {

    public static float lerp(float a, float b, float t) {
        return a + (b - a) * t;
    }

    public static float lerp(int a, int b, float t) {
        return a + (b - a) * t;
    }

}
