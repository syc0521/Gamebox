package com.example.gamebox.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import androidx.annotation.NonNull;

import com.example.gamebox.R;
import com.example.gamebox.bean.Achievement;
import com.example.gamebox.bean.Game;
import com.example.gamebox.bean.News;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GetSQLite
{
    public GetSQLite(@NonNull Context context)
    {
        Global.initPreferences = context.getSharedPreferences("dbInfo", Context.MODE_PRIVATE);
        initDB(context);
        boolean isInitDB = Global.initPreferences.getBoolean("initDB", false);
        if (!isInitDB)
        {
            Global.initPreferences.edit().putString("dbPath", Global.DB_PATH).apply();
            Global.initPreferences.edit().putBoolean("initDB", true).apply();
        }
        else
        {
            Global.DB_PATH = Global.initPreferences.getString("dbPath", "");
        }

    }

    private void initDB(@NonNull Context context)
    {
        File externalDir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        String dbPath = externalDir.getAbsolutePath() + File.separator + "db";
        File dir = new File(dbPath);
        if (!dir.exists())
        {
            dir.mkdir();
        }
        File file = new File(dir.getPath(), Global.DB_NAME);
        try
        {
            file.createNewFile();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        copyFile(context, R.raw.game, file);

        Global.DB_PATH = file.getAbsolutePath();
    }

    public void copyFile(Context context, int resId, File file)
    {
        InputStream is = null;
        FileOutputStream fos = null;
        try
        {
            is = context.getResources().openRawResource(resId);
            fos = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int hasRead = 0;
            while ((hasRead = is.read(buffer)) != -1)
            {
                fos.write(buffer, 0, hasRead);
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                assert fos != null;
                fos.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            try
            {
                assert is != null;
                is.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public SQLiteDatabase getDataBase(@NonNull Context context)
    {
        return Global.db = context.openOrCreateDatabase(Global.DB_PATH, Context.MODE_PRIVATE, null);
    }

    public List<Game> getGames(Context context, String sql)
    {
        List<Game> games = new ArrayList<>();
        SQLiteDatabase db = getDataBase(context);
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext())
        {
            Game game = new Game();
            game.setId(cursor.getInt(cursor.getColumnIndex(Game.ID)));
            game.setName(cursor.getString(cursor.getColumnIndex(Game.NAME)));
            int current = cursor.getInt(cursor.getColumnIndex(Game.CURRENT_ACHIEVEMENT));
            int total = cursor.getInt(cursor.getColumnIndex(Game.TOTAL_ACHIEVEMENT));
            game.setAchievement(new Achievement(current, total));
            game.setTotalTime(cursor.getInt(cursor.getColumnIndex(Game.TOTAL_TIME)));
            game.setCurrentTime(cursor.getInt(cursor.getColumnIndex(Game.CURRENT_TIME)));
            game.setUrl(cursor.getString(cursor.getColumnIndex(Game.URL)));
            int index = cursor.getColumnIndex(Game.IMAGE);
            byte[] data = cursor.getBlob(index);
            game.setImage(readImage(data));
            games.add(game);
        }
        cursor.close();
        db.close();
        return games;
    }

    public List<News> getNews(Context context, String sql)
    {
        List<News> news = new ArrayList<>();
        SQLiteDatabase db = getDataBase(context);
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext())
        {
            News aNews = new News();
            aNews.setNewsTitle(cursor.getString(cursor.getColumnIndex(News.NEWS_TITLE)));
            aNews.setCommentCount(cursor.getInt(cursor.getColumnIndex(News.COMMENT_COUNT)));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:ss", Locale.CHINESE);
            String dateFormatted = cursor.getString(cursor.getColumnIndex(News.NEWS_DATE));
            Date date;
            try
            {
                date = sdf.parse(dateFormatted);
            } catch (ParseException e)
            {
                date = new Date();
            }
            aNews.setNewsDate(date);
            aNews.setNewsCategory(cursor.getInt(cursor.getColumnIndex(News.NEWS_CATEGORY)));
            aNews.setUrl(cursor.getString(cursor.getColumnIndex(News.URL)));
            int index = cursor.getColumnIndex(News.IMAGE);
            byte[] data = cursor.getBlob(index);
            aNews.setImage(readImage(data));
            news.add(aNews);
        }
        cursor.close();
        db.close();
        return news;
    }

    public Bitmap readImage(byte[] data)
    {
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }

}
