package com.example.gamebox.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.gamebox.R;
import com.example.gamebox.bean.News;
import com.example.gamebox.utils.Time;

import java.util.Date;
import java.util.List;

public class NewsAdapter extends BaseAdapter
{
    private Context context;
    private List<News> newsList;

    public NewsAdapter(Context context, List<News> newsList)
    {
        this.context = context;
        this.newsList = newsList;
    }

    @Override
    public int getCount()
    {
        return newsList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return newsList;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = new ViewHolder();
        if (convertView == null)
        {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.list_item_news, null, false);
            holder.newsTitle = convertView.findViewById(R.id.news_title);
            holder.newsDesc = convertView.findViewById(R.id.news_desc);
            holder.newsComment = convertView.findViewById(R.id.news_comment_count);
            holder.newsImage = convertView.findViewById(R.id.news_image);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.newsTitle.setText(newsList.get(position).getNewsTitle());
        holder.newsImage.setImageBitmap(newsList.get(position).getImage());
        holder.newsComment.setText(newsList.get(position).getCommentCount());
        Time diffTime = getDiffTime(newsList.get(position).getNewsDate());
        holder.newsDesc.setText(diffTime + " · " + newsList.get(position).getNewsCategory());
        return convertView;
    }

    @NonNull
    private Time getDiffTime(@NonNull Date newsDate)
    {
        Date today = new Date();
        long todayTime = today.getTime();
        long newsTime = newsDate.getTime();
        long diff = today.getTime() - newsDate.getTime();
        long days = diff / (1000 * 60 * 60 * 24);
        long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
        return new Time(days, hours, minutes, newsDate);
    }

    public final class ViewHolder
    {
        public TextView newsTitle, newsDesc, newsComment;
        public ImageView newsImage;
    }
}
