package com.example.codingboy.zhihupaper.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.codingboy.zhihupaper.MainActivity;
import com.example.codingboy.zhihupaper.R;
import com.example.codingboy.zhihupaper.asyncTask.NewsDetailAsyncTask;
import com.example.codingboy.zhihupaper.db.MyDatebaseHelper;
import com.example.codingboy.zhihupaper.db.MyDbUtil;
import com.example.codingboy.zhihupaper.domin.News;
import com.example.codingboy.zhihupaper.util.NetWorkCheckUtil;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by codingBoy on 16/8/12.
 */
public class NewsDetailActivity extends Activity
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
        Intent intent=getIntent();
        Bundle data=intent.getExtras();
        if (data.containsKey("data"))
        {
            mDetail= (News.NewsDetail) data.getSerializable("data");
            isFavorite= MyDbUtil.isFavoriteNews(this,mDetail.id);
        }else
        {
            mTopNewsDetail= (News.TopNewsDetail) data.getSerializable("data");
            isFavorite=MyDbUtil.isFavoriteNews(this,mTopNewsDetail.id);
        }
        initView();
        android.app.ActionBar actionBar=getActionBar();
        setTitle("");
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
            case R.id.home:
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

    private static boolean isExit=false;
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event)
    {
        if (keyCode==KeyEvent.KEYCODE_BACK)
        {
            exitBy2Click();
        }
        return false;
    }

    private void exitBy2Click() {
        if (isExit==false)
        {
            isExit=true;
            Toast.makeText(getApplicationContext(),"再按一次返回键退出",Toast.LENGTH_SHORT).show();
            Timer timer=new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit=false;
                }
            },2000);
        }else
        {
            finish();
            System.exit(0);
        }
    }
}
