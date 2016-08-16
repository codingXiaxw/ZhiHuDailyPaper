package com.example.codingboy.zhihupaper.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by codingBoy on 16/8/3.
 */
public class ConfigUtil
{
    private final SharedPreferences mPre;
    private final SharedPreferences.Editor mEditor;

    public ConfigUtil(Context context)
    {
        mPre=context.getSharedPreferences("config",Context.MODE_PRIVATE);
        mEditor=mPre.edit();
    }

    public void setConfigBoolean(String key,boolean value)
    {
        mEditor.putBoolean(key,value).commit();
    }
    public boolean getConfigBoolean(String key,boolean defaultValue)
    {
        return mPre.getBoolean(key,defaultValue);
    }
}
