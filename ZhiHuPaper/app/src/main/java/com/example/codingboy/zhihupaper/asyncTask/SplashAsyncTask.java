package com.example.codingboy.zhihupaper.asyncTask;

import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.codingboy.zhihupaper.domin.SplashInfo;
import com.example.codingboy.zhihupaper.http.HttpRequest;
import com.google.gson.Gson;

/**
 * Created by codingBoy on 16/8/3.
 */
public class SplashAsyncTask extends AsyncTask<String,Void,SplashInfo> {

    private final ImageView mImageView;
    private final TextView mTextView;

    public SplashAsyncTask(ImageView imageView,TextView textView)
    {
        mImageView=imageView;
        mTextView=textView;
    }

    @Override
    protected SplashInfo doInBackground(String... params) {
        String result= HttpRequest.requestJSONResult(params[0]);
        Gson gson=new Gson();
        SplashInfo splashInfo=gson.fromJson(result,SplashInfo.class);

        return splashInfo;
    }

    @Override
    protected void onPostExecute(SplashInfo splashInfo) {
        super.onPostExecute(splashInfo);
        mImageView.setTag(splashInfo.img);
        new ImageLoaderForPager().loadPagerImage(splashInfo.img, mImageView);
        Log.e("TAG7", "image_url3:" + splashInfo.img);
        mTextView.setText(splashInfo.text);

    }
}
