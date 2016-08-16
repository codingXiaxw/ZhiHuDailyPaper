package com.example.codingboy.zhihupaper.domin;

import android.support.v4.view.PagerTabStrip;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by codingBoy on 16/8/2.
 */
public class News implements Serializable
{
    public String date;
    public ArrayList<NewsDetail> stories;
    public ArrayList<TopNewsDetail> top_stories;

    @Override
    public String toString() {
        return "News{" +
                "data='" + date + '\'' +
                ", stories=" + stories +
                ", top_stories=" + top_stories +
                '}';
    }

    public class NewsDetail implements Serializable{
        public String ga_prefix;
        public String id;
        public String[] images;
        public String title;
        public String type;
        public String time;

        @Override
        public String toString() {
            return "NewsDetail{" +
                    "title='" + title + '\'' +
                    ", type='" + type + '\'' +
                    ", time='" + time + '\'' +
                    '}';
        }
    }
    public class TopNewsDetail implements Serializable{
        public String ga_prefix;
        public String id;
        public String image;
        public String title;
        public String type;
        public String time;

        @Override
        public String toString() {
            return "TopNewsDetail{" +
                    "ga_prefix='" + ga_prefix + '\'' +
                    ", id='" + id + '\'' +
                    ", image='" + image + '\'' +
                    ", title='" + title + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }
}
