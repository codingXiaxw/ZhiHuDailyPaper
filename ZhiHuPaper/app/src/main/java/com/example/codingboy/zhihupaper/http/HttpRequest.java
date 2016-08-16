package com.example.codingboy.zhihupaper.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.codingboy.zhihupaper.util.ParseInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by codingBoy on 16/8/2.
 */
public class HttpRequest
{
    public static final String NESLISTURL="http://news-at.zhihu.com/api/4/news/latest";
    public static final String NESBEFORELISTURL="http://news.at.zhihu.com/api/4/news/before/";
    public static final String SPLASHINFOURL="http://news-at.zhihu.com/api/4/start-image/720*1184";

    public static final String NEW_TYPE_TODAY="today";
    public static final String NEW_TYPE_BEFORE="before";

    public static String requestJSONResult(String url)
    {
        HttpURLConnection connection = null;
        try {
            URL mUrl=new URL(url);
            connection=(HttpURLConnection)mUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            InputStream in=connection.getInputStream();
            String result= ParseInputStream.getStringFromStream(in);
            in.close();
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (connection!=null)
            {
                connection.disconnect();
            }
        }
        return null;
    }

    public static Bitmap requestForBitmap(String url)
    {
        HttpURLConnection connection=null;
        try{
            URL mUrl=new URL(url);
            connection=(HttpURLConnection)mUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(8000);
            InputStream in= connection.getInputStream();
            Bitmap bitmap=BitmapFactory.decodeStream(in);
            connection.disconnect();

            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
