package com.example.codingboy.zhihupaper.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;

import com.example.codingboy.zhihupaper.R;
import com.example.codingboy.zhihupaper.asyncTask.NewsDetailAsyncTask;
import com.example.codingboy.zhihupaper.db.MyDbUtil;
import com.example.codingboy.zhihupaper.domin.News;
import com.example.codingboy.zhihupaper.util.NetWorkCheckUtil;

import java.util.ArrayList;

/**
 * Created by codingBoy on 16/8/12.
 */
public class NewsDetailActivity extends AppCompatActivity
{
    private static final String rootURL="http://news-at.zhihu.com/api/4/news/";
    private WebView mWebView;
    private News.NewsDetail mDetail=null;
    private News.TopNewsDetail mTopNewsDetail=null;
    private boolean isFavorite;
    private MenuItem mFavoriteItem;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsdetail);
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        if(data.containsKey("data")){
            mDetail = (News.NewsDetail) data.getSerializable("data");
            isFavorite = MyDbUtil.isFavoriteNews(this,mDetail.id);
        }else{
            mTopNewsDetail = (News.TopNewsDetail) data.getSerializable("topnews");
            isFavorite = MyDbUtil.isFavoriteNews(this,mTopNewsDetail.id);
        }
//        Log.e("TAG6", "接收的数据" + detail.toString());
        initView();
        ActionBar actionBar =getSupportActionBar();
        setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void initView() {
        mWebView= (WebView) findViewById(R.id.webView);
        NewsDetailAsyncTask task=new NewsDetailAsyncTask(mWebView,NewsDetailActivity.this);
        if (NetWorkCheckUtil.isNetworkConnected(NewsDetailActivity.this))
        {
            if (mDetail!=null)
            {
                task.execute(rootURL+mDetail.id);
            }else if (mTopNewsDetail!=null)
            {
                task.execute(rootURL+mTopNewsDetail.id);
            }
        }else
        {
            NetWorkCheckUtil.showMessage(NewsDetailActivity.this);
        }

    }
    public static void startActivity(Context context,News.NewsDetail detail)
    {
        Intent intent=new Intent(context, NewsDetailActivity.class);
        Bundle data=new Bundle();
        data.putSerializable("data",detail);
        intent.putExtras(data);
        context.startActivity(intent);
    }
    public static void startActivity(Context context,News.TopNewsDetail detail)
    {
        Intent intent=new Intent(context,NewsDetailActivity.class);
        Bundle data=new Bundle();
        data.putSerializable("topnews",detail);
        intent.putExtras(data);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
            case R.id.favorite:
                if (isFavorite)
                {
                    if (mDetail!=null)
                    {
                        MyDbUtil.removeFavorityNews(this,mDetail.id);
                    }else
                    {
                        MyDbUtil.removeFavorityNews(this,mTopNewsDetail.id);
                    }
                    isFavorite=!isFavorite;
                    mFavoriteItem.setIcon(R.mipmap.fav_normal);
                }else
                {
                    if (mDetail!=null)
                    {
                        MyDbUtil.saveFavoriteNews(this,mDetail);
                    }else
                    {
                        MyDbUtil.saveFavoriteNews(this,mTopNewsDetail);
                    }
                    isFavorite=!isFavorite;
                    mFavoriteItem.setIcon(R.mipmap.fav_active);
                }

                ArrayList<News.NewsDetail> list=MyDbUtil.getFavoriteNews(this);
                Log.e("TAG8", "收藏9：" + list.toString());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        mFavoriteItem=menu.findItem(R.id.favorite);
        Log.e("TAG8","创建菜单项");
        if (isFavorite)
        {
            mFavoriteItem.setIcon(R.mipmap.fav_active);
        }
        return super.onCreateOptionsMenu(menu);
    }


}
