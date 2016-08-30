package com.example.codingboy.zhihupaper.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.codingboy.zhihupaper.domin.News;

import java.util.ArrayList;

/**
 * Created by codingBoy on 16/8/2.
 */
public class MyDbUtil {
    public static boolean saveFavoriteNews(Context context,News.NewsDetail newsDetail)
    {
        MyDatebaseHelper myDatebaseHelper=new MyDatebaseHelper(context,"news.db",null,1);
        SQLiteDatabase db=myDatebaseHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("newsID",newsDetail.id);
        values.put("image",newsDetail.images[0]);
        values.put("title",newsDetail.title);
        values.put("type",newsDetail.type);
        values.put("time",newsDetail.time);
        long rowId=db.insert("favoriteNews",null,values);
        if (rowId!=-1)
        {
            return true;
        }else {
            return false;
        }

    }
    public static boolean saveFavoriteNews(Context context,News.TopNewsDetail topNewsDetail){
        MyDatebaseHelper myDatebaseHelper = new MyDatebaseHelper(context,"news.db",null,1);
        SQLiteDatabase db = myDatebaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("newsID",topNewsDetail.id);
        values.put("image",topNewsDetail.image);
        values.put("title",topNewsDetail.title);
        values.put("type",topNewsDetail.type);
        values.put("time",topNewsDetail.time);
        Long rowId = db.insert("favoriteNews",null,values);
        if(rowId!=-1){
            return true;
        }else {
            return false;
        }
    }

    public static boolean isFavoriteNews(Context context,String newsID)
    {
        MyDatebaseHelper myDatebaseHelper=new MyDatebaseHelper(context,"news.db",null,1);
        SQLiteDatabase db=myDatebaseHelper.getWritableDatabase();
        Cursor cursor=db.query("favoriteNews", null, "newsID=?", new String[]{newsID}, null, null, null);
        if (cursor.moveToFirst())
        {
            return true;
        }else {
            return false;
        }


    }

    public static ArrayList<News.NewsDetail> getFavoriteNews(Context context)
    {
        MyDatebaseHelper myDatebaseHelper=new MyDatebaseHelper(context,"news.db",null,1);
        SQLiteDatabase db=myDatebaseHelper.getWritableDatabase();
        Cursor cursor=db.query("favoriteNews", null, null, null, null, null, null);
        ArrayList<News.NewsDetail> newsList=new ArrayList<>();
        while(cursor.moveToNext())
        {
            News.NewsDetail newsDetail=new News().new NewsDetail();
            newsDetail.id=cursor.getString(cursor.getColumnIndex("newsID"));
            newsDetail.images=new String[]{cursor.getString(cursor.getColumnIndex("image"))};
            newsDetail.title=cursor.getString(cursor.getColumnIndex("title"));
            newsDetail.type=cursor.getColumnName(cursor.getColumnIndex("type"));
            newsDetail.time=cursor.getString(cursor.getColumnIndex("time"));
            newsList.add(newsDetail);
        }
        return newsList;
    }

    public static boolean removeFavorityNews(Context context,String newsID)
    {
        MyDatebaseHelper myDatebaseHelper=new MyDatebaseHelper(context,"news.db",null,1);
        SQLiteDatabase db=myDatebaseHelper.getWritableDatabase();
        int raw=db.delete("favoriteNews","newsID=?",new String[]{newsID});
        if (raw==1)
        {
            return true;
        }else {
            return false;
        }
    }

}
