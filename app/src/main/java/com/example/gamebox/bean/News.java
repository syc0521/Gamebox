package com.example.gamebox.bean;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;

public class News implements Serializable
{
    private String newsTitle, url;
    private int newsCategory;
    private Date newsDate;
    private int commentCount;
    private Bitmap image;
    private HashMap<Integer, String> category = new HashMap<>();
    public static String NEWS_TITLE = "newsTitle";
    public static String NEWS_CATEGORY = "newsCategory";
    public static String NEWS_DATE = "newsDate";
    public static String COMMENT_COUNT = "commentCount";
    public static String URL = "url";
    public static String IMAGE = "image";

    public News()
    {
        category.put(0, "主机游戏");
        category.put(1, "PC游戏");
        category.put(2, "手机游戏");
        category.put(3, "杂谈");
        category.put(4, "其他");
    }

    public Bitmap getImage()
    {
        return image;
    }

    public void setImage(Bitmap image)
    {
        this.image = image;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getCommentCount()
    {
        return String.valueOf(commentCount);
    }

    public void setCommentCount(int commentCount)
    {
        this.commentCount = commentCount;
    }

    public String getNewsTitle()
    {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle)
    {
        this.newsTitle = newsTitle;
    }

    public String getNewsCategory()
    {
        return category.get(newsCategory);
    }

    public void setNewsCategory(int newsCategory)
    {
        this.newsCategory = newsCategory;
    }

    public Date getNewsDate()
    {
        return newsDate;
    }

    public void setNewsDate(Date newsDate)
    {
        this.newsDate = newsDate;
    }
}
