package com.aefyr.mgupp.util;

public class StringUtil {
    public static String capitalize(String s) {
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}
