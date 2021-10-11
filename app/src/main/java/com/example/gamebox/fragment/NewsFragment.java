package com.example.gamebox.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.gamebox.R;
import com.example.gamebox.activity.NewsActivity;
import com.example.gamebox.adapter.NewsAdapter;
import com.example.gamebox.bean.News;
import com.example.gamebox.utils.GetNews;
import com.example.gamebox.utils.GetSQLite;

import java.util.List;

public class NewsFragment extends Fragment
{
    private ListView newsView;
    private List<News> newsList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        newsView = view.findViewById(R.id.news_list);
        GetSQLite getSQLite = new GetSQLite(getContext());
        newsList = getSQLite.getNews(getContext(), "select * from news");
        NewsAdapter newsAdapter = new NewsAdapter(this.getActivity(), newsList);
        newsView.setAdapter(newsAdapter);
        newsView.setOnItemClickListener((parent, view1, position, id) -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), NewsActivity.class);
            String url = newsList.get(position).getUrl();
            intent.putExtra("url", url);
            startActivity(intent);
        });
        return view;
    }
}