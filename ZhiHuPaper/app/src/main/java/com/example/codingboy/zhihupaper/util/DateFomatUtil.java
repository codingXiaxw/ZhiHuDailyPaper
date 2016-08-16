package com.example.codingboy.zhihupaper.util;

import android.support.v4.view.PagerTabStrip;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by codingBoy on 16/8/11.
 */
public class DateFomatUtil
{
    private static SimpleDateFormat sFormat=new SimpleDateFormat("MM月dd日 E", Locale.CHINA);
    private static SimpleDateFormat sFormat2=new SimpleDateFormat("yyyyMMdd",Locale.CHINA);

    public static String getTime(Date date)
    {
        return sFormat.format(date);
    }
    public static String getTime(String date)
    {
        Date d=null;
        try
        {
            d=sFormat2.parse(date);
            Log.e("TAG5", d.toString());
        }catch (ParseException e)
        {
            e.printStackTrace();
        }
        return getTime(d);
    }
}
