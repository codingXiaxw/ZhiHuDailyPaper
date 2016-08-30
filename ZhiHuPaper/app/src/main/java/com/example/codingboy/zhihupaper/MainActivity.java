package com.example.codingboy.zhihupaper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.codingboy.zhihupaper.activity.FavoriteActivity;
import com.example.codingboy.zhihupaper.activity.NewsDetailActivity;
import com.example.codingboy.zhihupaper.adapter.MyPagerAdapter;
import com.example.codingboy.zhihupaper.adapter.NewsListAdapter;
import com.example.codingboy.zhihupaper.asyncTask.NewsListTask;
import com.example.codingboy.zhihupaper.domin.News;
import com.example.codingboy.zhihupaper.http.HttpRequest;
import com.example.codingboy.zhihupaper.util.ConfigUtil;
import com.example.codingboy.zhihupaper.util.DateFomatUtil;
import com.example.codingboy.zhihupaper.util.NetWorkCheckUtil;
import com.example.codingboy.zhihupaper.view.MyListView;
import com.example.codingboy.zhihupaper.view.MyRefreshLayout;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private String currentDate=null;
    private MyRefreshLayout mMyRefreshLayout;
    private MyListView mListView;
    private NewsListAdapter mAdapter;
    private MenuItem mSettingMenyItem;
    private ConfigUtil mConfigUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        setTitle("首页");
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        mMyRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mMyRefreshLayout.setRefreshing(true);
                NewsListTask task = new NewsListTask(new NewsListTask.OnDataPreparedListener() {
                    @Override
                    public void onDataPrepared(News news) {
                        currentDate = news.date;
                        News.NewsDetail newsDetail = new News().new NewsDetail();
                        newsDetail.type = "1";
                        newsDetail.time = DateFomatUtil.getTime(news.date);
                        news.stories.add(0, newsDetail);
                        if (mAdapter == null) {
                            mAdapter = new NewsListAdapter(news.stories, MainActivity.this, mListView);
                            mListView.setAdapter(mAdapter);
                            MyPagerAdapter myPagerAdapter = new MyPagerAdapter(news.top_stories, MainActivity.this);
                            mListView.setViewPagerAdapter(myPagerAdapter);

                        } else {
                            mAdapter.refreshData(news.stories);
                        }
                        Log.e("TAG5", "刷新20：" + news.stories.toString());
                        mMyRefreshLayout.setRefreshing(false);
                    }
                });
                if (NetWorkCheckUtil.isNetworkConnected(MainActivity.this)) {
                    task.execute(HttpRequest.NEW_TYPE_TODAY);

                } else {
                    NetWorkCheckUtil.showMessage(MainActivity.this);
                }
            }
        });
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.e("TAG6", "lastPosition:" + mListView.getLastVisiblePosition() + ",datacount:" + mListView.getCount());
                if (mListView.getLastVisiblePosition() == mListView.getCount() - 1) {
                    NewsListTask task = new NewsListTask(new NewsListTask.OnDataPreparedListener() {
                        @Override
                        public void onDataPrepared(News news) {
                            currentDate = news.date;
                            News.NewsDetail newsDetail = new News().new NewsDetail();
                            newsDetail.type = "1";
                            newsDetail.time = DateFomatUtil.getTime(news.date);
                            news.stories.add(0, newsDetail);
                            mAdapter.refreshLoadMore(news.stories);
                        }
                    });
                    if (NetWorkCheckUtil.isNetworkConnected(MainActivity.this)) {
                        task.execute(HttpRequest.NEW_TYPE_BEFORE, currentDate);

                    } else {
                        NetWorkCheckUtil.showMessage(MainActivity.this);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                News.NewsDetail detail = null;
                if (firstVisibleItem == 0) {
                    MainActivity.this.setTitle("首页");
                } else if ((detail = (News.NewsDetail) mAdapter.getItem(firstVisibleItem - 1)).type.equals("1")) {
                    String todayTime = DateFomatUtil.getTime(new Date());
                    if (detail.time.equals(todayTime)) {
                        MainActivity.this.setTitle("今日要闻");
                    } else {
                        MainActivity.this.setTitle(detail.time);
                    }
                }
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News.NewsDetail detail = (News.NewsDetail) mAdapter.getItem((int) id);
                NewsDetailActivity.startActivity(MainActivity.this,detail);
            }
        });
    }
    private void initView() {
        mMyRefreshLayout= (MyRefreshLayout) findViewById(R.id.swipRefresh);
        mMyRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        mListView= (MyListView) findViewById(R.id.lv_news);
    }

    private void initData() {
        NewsListTask task=new NewsListTask(new NewsListTask.OnDataPreparedListener() {
            @Override
            public void onDataPrepared(News news) {
                currentDate=news.date;
                News.NewsDetail newsDetail=new News().new NewsDetail();
                newsDetail.type="1";
                newsDetail.time= DateFomatUtil.getTime(news.date);
                news.stories.add(0,newsDetail);
                mAdapter=new NewsListAdapter(news.stories,MainActivity.this,mListView);
                mListView.setAdapter(mAdapter);
                MyPagerAdapter myPagerAdapter=new MyPagerAdapter(news.top_stories,MainActivity.this);
                mListView.setViewPagerAdapter(myPagerAdapter);
            }
        });

        if (NetWorkCheckUtil.isNetworkConnected(this))
        {
            task.execute(HttpRequest.NEW_TYPE_TODAY);
        }else
        {
            NetWorkCheckUtil.showMessage(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        mSettingMenyItem=menu.findItem(R.id.setting);
        mConfigUtil=new ConfigUtil(this);
        if (mConfigUtil.getConfigBoolean("splash", true))
        {
            mSettingMenyItem.setTitle("关闭导航页");
        }else {
            mSettingMenyItem.setTitle("开启导航页");
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.favorite:
                startActivity(new Intent(this,FavoriteActivity.class));
                break;
            case R.id.setting:
                boolean isOpen=mConfigUtil.getConfigBoolean("splash",true);
                if (isOpen)
                {
                    isOpen=!isOpen;
                    mConfigUtil.setConfigBoolean("splash",isOpen);
                    mSettingMenyItem.setTitle("开启导航页");
                }else {
                    isOpen=!isOpen;
                    mConfigUtil.setConfigBoolean("splash",isOpen);
                    mSettingMenyItem.setTitle("关闭导航页");
                }
                break;
        }
        return super.onOptionsItemSelected(item);
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
            Toast.makeText(getApplicationContext(), "再按一次返回键退出", Toast.LENGTH_SHORT).show();
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
