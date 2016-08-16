package com.example.codingboy.zhihupaper.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by codingBoy on 16/7/6.
 */
public class MyRefreshLayout extends SwipeRefreshLayout
{
    private float mPreX;
    private int mTouchSlop;
    public MyRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //触发移动事件的最短距离，如果小于这个距离就不触发移动事件
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }
    //重写拦截事件

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPreX = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float detalX = Math.abs(ev.getX() - mPreX);
                //若水平滑动距离大于设置的最小移动距离，让下拉刷新在竖直方向才可以触发
                if (detalX > mTouchSlop + 60) {
                    //让swipRefresh不要拦截事件
                    return false;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
