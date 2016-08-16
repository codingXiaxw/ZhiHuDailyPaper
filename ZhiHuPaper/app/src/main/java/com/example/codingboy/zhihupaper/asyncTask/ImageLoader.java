package com.example.codingboy.zhihupaper.asyncTask;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.codingboy.zhihupaper.http.HttpRequest;

/**
 * Created by codingBoy on 16/8/6.
 */
public class ImageLoader {
    private LruCache<String,Bitmap> mCache;
    private ListView mListView;
    public ImageLoader(ListView listView)
    {
        mListView=listView;

        long maxMemory=Runtime.getRuntime().maxMemory();
        mCache=new LruCache<String,Bitmap>((int)(maxMemory/4))
        {
            @Override
            protected int sizeOf(String key,Bitmap value)
            {
                return value.getByteCount();
            }
        };
    }
    public void loadImage(String url,ImageView imageView)
    {
        Bitmap bitmap=getFromCache(url);
        if (bitmap== null)
        {
            ImageAsyncTask task=new ImageAsyncTask(url);
            task.execute(url);
        }else{
            imageView.setImageBitmap(bitmap);
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

    class ImageAsyncTask extends AsyncTask<String,Void,Bitmap>
    {
        private String mUrl;

        public ImageAsyncTask(String url)
        {
            mUrl=url;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String url=params[0];
            Bitmap bitmap= HttpRequest.requestForBitmap(url);

            addToCache(mUrl,bitmap);
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ImageView imageView= (ImageView) mListView.findViewWithTag(mUrl);
            if (bitmap!=null&&imageView!=null)
            {
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
