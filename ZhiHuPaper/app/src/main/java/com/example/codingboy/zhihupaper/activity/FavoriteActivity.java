package com.example.codingboy.zhihupaper.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.codingboy.zhihupaper.R;
import com.example.codingboy.zhihupaper.adapter.NewsListAdapter;
import com.example.codingboy.zhihupaper.db.MyDbUtil;
import com.example.codingboy.zhihupaper.domin.News;

import java.util.ArrayList;

/**
 * Created by codingBoy on 16/8/11.
 */
public class FavoriteActivity extends Activity
{
    private ListView mListView;
    private ArrayList<News.NewsDetail> mNewsDetails;
    private NewsListAdapter mAdapter;
    private android.app.ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        mActionBar=getActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        setTitle("我的收藏");
        initView();
        initData();
    }

    private void initData() {
        mNewsDetails= MyDbUtil.getFavoriteNews(this);
        mAdapter=new NewsListAdapter(mNewsDetails,this,mListView);
        mListView.setAdapter(mAdapter);
    }

    private void initView() {

        mListView= (ListView) findViewById(R.id.lv_favorite);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News.NewsDetail detail = (News.NewsDetail) mAdapter.getItem((int) id);
                NewsDetailActivity.startActivity(FavoriteActivity.this, detail);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        ArrayList<News.NewsDetail> tempList=MyDbUtil.getFavoriteNews(this);
        Log.e("TAG9", "newsDetails:" +mNewsDetails.size() + ",tempList:" + tempList.size());
        if (mNewsDetails.size()!=tempList.size())
        {
            Log.e("TAG9","newsDetails1:"+mNewsDetails.toString()+",tempList:"+tempList.toString());
            mNewsDetails.clear();
            mNewsDetails.addAll(tempList);
            Log.e("TAG9", "newsDetails5:" + mNewsDetails.toString());
            mAdapter.notifyDataSetChanged();
        }
    }
}
