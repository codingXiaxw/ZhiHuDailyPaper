package com.example.codingboy.zhihupaper.asyncTask;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;

import com.example.codingboy.zhihupaper.http.HttpRequest;

/**
 * Created by codingBoy on 16/8/3.
 */
public class ImageLoaderForPager
{
    private LruCache<String,Bitmap> mCache;
    public ImageLoaderForPager()
    {
        long maxMemory=Runtime.getRuntime().maxMemory();
        mCache=new LruCache<String,Bitmap>((int)(maxMemory/4)){
            @Override
            protected int sizeOf(String key,Bitmap value)
            {
                return value.getByteCount();
            }
        };
    }
    public void loadPagerImage(String url,ImageView imageView)
    {
        Bitmap bitmap=getFromCache(url);
        if (bitmap==null)
        {
            PagerImageAsyncTask task=new PagerImageAsyncTask(url,imageView);
            task.execute(url);
        }else
        {
            imageView.setImageBitmap(bitmap);
        }
    }

    class PagerImageAsyncTask extends AsyncTask<String,Void,Bitmap>
    {
        private final String mUrl;
        private final ImageView mImageView;

        public PagerImageAsyncTask(String url,ImageView imageView)
        {
            mUrl=url;
            mImageView=imageView;
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            String url=params[0];
            Bitmap bitmap= HttpRequest.requestForBitmap(url);

            addToCache(mUrl,bitmap);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (mUrl.equals(mImageView.getTag()))
            {
                mImageView.setImageBitmap(bitmap);
            }
        }
    }
    private void addToCache(String url,Bitmap bitmap)
    {
        if (getFromCache(url)==null)
        {
            mCache.put(url,bitmap);
        }
    }
    private Bitmap getFromCache(String url)
    {
        return mCache.get(url);
    }
}
