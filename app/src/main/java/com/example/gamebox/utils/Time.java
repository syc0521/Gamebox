package com.example.gamebox.utils;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Time
{
    private long day, hour, minute;
    private Date newsDate;

    public Time(long day, long hour, long minute, Date newsDate)
    {
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.newsDate = newsDate;
    }

    @NonNull
    @Override
    public String toString()
    {
        if (day == 0 && hour == 0 && minute <= 3)
            return "刚刚";
        else if (day == 0 && hour == 0)
            return minute + "分钟前";
        else if (day == 0)
            return hour + "小时前";
        else
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("M-dd", Locale.CHINESE);
            return dateFormat.format(newsDate);
        }
    }
}
