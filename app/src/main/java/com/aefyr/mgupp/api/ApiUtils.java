package com.aefyr.mgupp.api;

/**
 * Created by Aefyr on 07.10.2018.
 */
public class ApiUtils {

    public static String cleanString(String s) {
        return s.replaceAll("\\s+", " ")
                .replaceAll("<br>", "")
                .replaceAll("</br>", "")
                .replaceAll("\n", "")
                .replaceAll("\"", "");
    }

}
