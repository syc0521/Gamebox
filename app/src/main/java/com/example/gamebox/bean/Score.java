package com.example.gamebox.bean;

import java.io.Serializable;
import java.util.Date;

public class Score implements Serializable
{
    private int score;
    private Date date;

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }
}
