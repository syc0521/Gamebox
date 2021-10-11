package com.example.gamebox.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gamebox.R;
import com.example.gamebox.bean.Game;
import com.example.gamebox.view.AchievementView;

import java.util.List;

public class GameAdapter extends BaseAdapter
{
    private Context context;
    private List<Game> gameList;

    public GameAdapter(Context context, List<Game> gameList)
    {
        this.context = context;
        this.gameList = gameList;
    }

    @Override
    public int getCount()
    {
        return gameList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return gameList;
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
            convertView = LayoutInflater.from(this.context).inflate(R.layout.list_item_game, null, false);
            holder.gameTitle = convertView.findViewById(R.id.game_title);
            holder.gameHour = convertView.findViewById(R.id.game_hour);
            holder.gameHourRecent = convertView.findViewById(R.id.game_hour_recent);
            holder.gameAchievement = convertView.findViewById(R.id.game_achievement);
            holder.achievementBar = convertView.findViewById(R.id.game_achievement_bar);
            holder.gameImage = convertView.findViewById(R.id.game_image);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.gameTitle.setText(gameList.get(position).getName());
        holder.gameHour.setText(gameList.get(position).getTotalTime() + "h");
        holder.gameHourRecent.setText(gameList.get(position).getCurrentTime() + "h");
        holder.gameAchievement.setText(gameList.get(position).getAchievement().toString());
        holder.achievementBar.setAchievement(gameList.get(position).getAchievement());
        holder.gameImage.setImageBitmap(gameList.get(position).getImage());
        return convertView;
    }

    public final class ViewHolder
    {
        public TextView gameTitle, gameHour, gameHourRecent, gameAchievement;
        public ImageView gameImage;
        public AchievementView achievementBar;
    }
}
