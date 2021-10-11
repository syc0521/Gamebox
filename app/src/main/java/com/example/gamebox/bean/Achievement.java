package com.example.gamebox.bean;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Achievement implements Serializable
{
    private int current, total;

    public Achievement(int current, int total)
    {
        this.current = current;
        this.total = total;
    }

    public int getCurrent()
    {
        return current;
    }

    public int getTotal()
    {
        return total;
    }

    @NonNull
    @Override
    public String toString()
    {
        return current + "/" + total;
    }
}
