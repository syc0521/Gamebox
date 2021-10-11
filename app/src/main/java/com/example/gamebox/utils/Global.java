package com.example.gamebox.utils;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

public class Global
{
    public static String DB_PATH;
    public static String DB_NAME = "game.db";
    public static GetSQLite getSQLite;
    public static SQLiteDatabase db;
    public static SharedPreferences initPreferences;
}
