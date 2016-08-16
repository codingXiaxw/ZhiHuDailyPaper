package com.example.codingboy.zhihupaper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.codingboy.zhihupaper.MainActivity;
import com.example.codingboy.zhihupaper.R;
import com.example.codingboy.zhihupaper.asyncTask.ImageLoaderForPager;
import com.example.codingboy.zhihupaper.asyncTask.SplashAsyncTask;
import com.example.codingboy.zhihupaper.http.HttpRequest;
import com.example.codingboy.zhihupaper.util.ConfigUtil;
import com.example.codingboy.zhihupaper.util.NetWorkCheckUtil;


/**
 * Created by codingBoy on 16/8/3.
 */
public class SplashActivity extends Activity
{
    private TextView mTextView;
    private ImageView mImageView;
    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }
    };

    private ConfigUtil mConfigUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mConfigUtil=new ConfigUtil(this);
        if (!mConfigUtil.getConfigBoolean("splash",true))
        {
            startActivity(new Intent(SplashActivity.this,MainActivity.class));
            finish();
            return;
        }
        setTitle("");
        initView();
        initData();
    }

    private void initData()
    {
        if (NetWorkCheckUtil.isNetworkConnected(this))
        {
            SplashAsyncTask task=new SplashAsyncTask(mImageView,mTextView);
            task.execute(HttpRequest.SPLASHINFOURL);
        }else
        {
            NetWorkCheckUtil.showMessage(this);
        }
        mHandler.sendEmptyMessageDelayed(0, 3000);
    }

    private void initView()
    {
        mTextView= (TextView) findViewById(R.id.tv_pic_copyright);
        mImageView= (ImageView) findViewById(R.id.iv_splash);
        Window window=getWindow();
    }

}
