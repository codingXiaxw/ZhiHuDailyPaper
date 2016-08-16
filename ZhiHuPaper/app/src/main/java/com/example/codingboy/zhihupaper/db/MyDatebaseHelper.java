package com.example.codingboy.zhihupaper.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by codingBoy on 16/8/2.
 */
public class MyDatebaseHelper extends SQLiteOpenHelper
{

    public static final String TABLE_SQL="create table favoriteNews(" +
            "_id integer primary key autoincrement," +
            "newsID,image,title,type,time)";

    public MyDatebaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
