package com.example.codingboy.zhihupaper.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.example.codingboy.zhihupaper.R;

/**
 * Created by codingBoy on 16/7/6.
 */
public class MyListView extends ListView
{
    private final MyViewPager viewPager;
    private int currentItem=0;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int pagerCount = viewPager.getAdapter().getCount();

            viewPager.setCurrentItem(currentItem);
            currentItem++;

            if (currentItem > pagerCount-1) {
                currentItem = 0;
            }
            this.sendEmptyMessageDelayed(0, 3000);
//            Log.e("TAG4","current:"+currentItem+"pagercount:"+pagerCount);
            Log.e("TAG4", "currentItem4:" + viewPager.getCurrentItem() + "pagercount:" + pagerCount);

        }
    };
    private final PagerIndicator indicator;

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = LayoutInflater.from(context);
        View viewHeader = inflater.inflate(R.layout.list_header_item, null);
        viewPager = (MyViewPager) viewHeader.findViewById(R.id.vp_header);
        indicator = (PagerIndicator) viewHeader.findViewById(R.id.indicator);
        addHeaderView(viewHeader);
    }

    public void setViewPagerAdapter(PagerAdapter adapter) {
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);
        mHandler.sendEmptyMessageDelayed(0, 2000);//让viewpager轮播
        //当viewpager获得焦点时停止轮播
        viewPager.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mHandler.removeCallbacksAndMessages(null);
                } else {
                    mHandler.sendEmptyMessageDelayed(0, 2000);

                }
            }
        });

    }

}
