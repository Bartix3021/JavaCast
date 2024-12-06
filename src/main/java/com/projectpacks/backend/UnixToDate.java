package com.projectpacks.backend;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UnixToDate {

    public static String convertUnixTimestampToDate(long unixTimestamp, String format) {

        Date date = new Date(unixTimestamp * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
}
