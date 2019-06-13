package com.blackbeast.booklibrary.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtils {
    public static Date addDaysToDate(Date date, Integer days) {

        if(date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, days);
            return calendar.getTime();
        }else
            return null;
    }

    public static Long daysDiff(Date first, Date second) {
        if(first != null && second != null){
            long diffInMillis = Math.abs(second.getTime() - first.getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);

            return diff;
        }else
            return -1L;
    }
}
