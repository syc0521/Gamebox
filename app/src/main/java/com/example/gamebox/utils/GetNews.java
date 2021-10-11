package com.example.gamebox.utils;

import com.example.gamebox.bean.News;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GetNews
{
    private String[] newsTitles = {"Test1", "Test2", "Test3"};
    private String[] newsCategories = {"主机游戏", "PC游戏", "手机游戏"};
    private Date[] newsDates = { new Date(), new Date(), new Date() };
    private int[] newsComments = {5, 6, 8};
    public List<News> getNewsList()
    {
        List<News> newsList = new ArrayList<>();
        for (int i = 0; i < newsTitles.length; i++)
        {
            News news = new News();
            news.setNewsTitle(newsTitles[i]);
            //news.setNewsCategory(newsCategories[i]);
            news.setNewsDate(newsDates[i]);
            news.setCommentCount(newsComments[i]);
            newsList.add(news);
        }
        return newsList;
    }
}
