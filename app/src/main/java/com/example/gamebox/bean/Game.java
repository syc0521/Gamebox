package com.example.gamebox.bean;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Game implements Serializable
{
    private int id;
    private String name, url;
    private float currentTime, totalTime;
    private Achievement achievement;
    private Bitmap image;
    public static String NAME = "name";
    public static String TOTAL_ACHIEVEMENT = "totalAchievement";
    public static String CURRENT_ACHIEVEMENT = "currentAchievement";
    public static String CURRENT_TIME = "currentHours";
    public static String TOTAL_TIME = "playHours";
    public static String IMAGE = "image";
    public static String ID = "id";
    public static String URL = "url";

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCurrentTime()
    {
        return String.valueOf(currentTime);
    }

    public void setCurrentTime(float currentTime)
    {
        this.currentTime = currentTime;
    }

    public String getTotalTime()
    {
        return String.valueOf(totalTime);
    }

    public void setTotalTime(float totalTime)
    {
        this.totalTime = totalTime;
    }

    public Achievement getAchievement()
    {
        return achievement;
    }

    public void setAchievement(Achievement achievement)
    {
        this.achievement = achievement;
    }

    public Bitmap getImage()
    {
        return image;
    }

    public void setImage(Bitmap image)
    {
        this.image = image;
    }

}
