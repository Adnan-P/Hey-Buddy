package com.example.heyBuddy.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    public static Date getCurrentDate() {
        return new Date();
    }

    public static String getCurrentDateAsString(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(new Date());
    }


}
