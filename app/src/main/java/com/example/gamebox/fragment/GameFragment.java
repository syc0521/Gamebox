package com.example.gamebox.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.gamebox.R;
import com.example.gamebox.activity.NewsActivity;
import com.example.gamebox.adapter.GameAdapter;
import com.example.gamebox.bean.Game;
import com.example.gamebox.utils.GetSQLite;

import java.util.List;

public class GameFragment extends Fragment
{
    private ListView gameView;
    private List<Game> gameList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        gameView = view.findViewById(R.id.game_list);
        GetSQLite getSQLite = new GetSQLite(getActivity());
        gameList = getSQLite.getGames(getActivity(), "select * from game");
        GameAdapter gameAdapter = new GameAdapter(this.getActivity(), gameList);
        gameView.setAdapter(gameAdapter);
        gameView.setOnItemClickListener((parent, view1, position, id) -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), NewsActivity.class);
            intent.putExtra("url", gameList.get(position).getUrl());
            startActivity(intent);
        });
        return view;
    }
}
