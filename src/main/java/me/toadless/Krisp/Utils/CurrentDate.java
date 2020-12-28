package me.toadless.Krisp.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrentDate {
    private static final String pattern = "HH:mm:ss.SSS";
    private static final SimpleDateFormat patternFormat = new SimpleDateFormat(pattern);
    public static final String date = patternFormat.format(new Date());
}