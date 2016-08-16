package com.example.codingboy.zhihupaper.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by codingBoy on 16/7/6.
 */
public class MyViewPager extends ViewPager
{

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        Log.e("TAG3","拦截"+ev.getAction());
        return super.dispatchTouchEvent(ev);
    }
}
