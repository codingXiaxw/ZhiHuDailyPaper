package com.example.codingboy.zhihupaper.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by codingBoy on 16/8/3.
 */
public class NetWorkCheckUtil
{
    public static boolean isNetworkConnected(Context context)
    {
        if (context!=null)
        {
            ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
            if (networkInfo!=null)
            {
                return networkInfo.isAvailable();
            }
        }
        return false;

    }
    public static void showMessage(Context context)
    {
        Toast.makeText(context,"网络不可用，请检查连接是否正常",Toast.LENGTH_SHORT);
    }
}
