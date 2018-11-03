package com.aefyr.mgupp.utils;

public class StringUtils {
    public static String capitalize(String s) {
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}
