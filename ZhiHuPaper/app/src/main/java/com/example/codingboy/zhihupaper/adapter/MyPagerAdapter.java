package com.example.codingboy.zhihupaper.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.codingboy.zhihupaper.R;
import com.example.codingboy.zhihupaper.activity.NewsDetailActivity;
import com.example.codingboy.zhihupaper.asyncTask.ImageLoaderForPager;
import com.example.codingboy.zhihupaper.db.MyDatebaseHelper;
import com.example.codingboy.zhihupaper.domin.News;

import java.util.ArrayList;

/**
 * Created by codingBoy on 16/8/11.
 */
public class MyPagerAdapter extends PagerAdapter
{

    private ArrayList<News.TopNewsDetail> mData;
    private Context mContext;
    private final LayoutInflater mInflater;
    private final ImageLoaderForPager loader;


    public MyPagerAdapter(ArrayList<News.TopNewsDetail> data,Context context)
    {
        mData=data;
        mContext=context;
        mInflater=LayoutInflater.from(mContext);
        loader=new ImageLoaderForPager();
        Log.e("TAG2", "MyPagerAdapter: " + mData.toString());
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(View container, int position) {
        final News.TopNewsDetail topNewsDetail=mData.get(position);
        View view=mInflater.inflate(R.layout.pager_item, (ViewGroup) container,false);
        TextView pagerTitle= (TextView) view.findViewById(R.id.tv_topnews_title);
        ImageView pagerImage= (ImageView) view.findViewById(R.id.iv_topnews_icon);
        pagerTitle.setText(topNewsDetail.title);
        pagerImage.setTag(topNewsDetail.image);
        loader.loadPagerImage(topNewsDetail.image, pagerImage);
        ((ViewPager)container).addView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        return view;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager)container).removeView((View) object);
    }
}
