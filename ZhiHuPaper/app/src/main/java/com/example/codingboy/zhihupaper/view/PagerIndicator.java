package com.example.codingboy.zhihupaper.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.codingboy.zhihupaper.R;

/**
 * Created by codingBoy on 16/7/6.
 */
public class PagerIndicator extends View
{

    private int mColor,mHeight,mWidth;
    private float space,radius,indicatorRadius;
    private int indicatorNum=0;
    private int currentItem=0;
    private Paint mPaint;
    private static final String TAG = "PagerIndicator";

    public PagerIndicator(Context context) {
        super(context);
    }

    public PagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta=context.obtainStyledAttributes(attrs, R.styleable.Indicator);
        space=ta.getDimension(R.styleable.Indicator_indicatorSpace, 6);
        radius=ta.getDimension(R.styleable.Indicator_smallRadius, 2);
        indicatorRadius=ta.getDimension(R.styleable.Indicator_bigRadius, 3);
        mColor=ta.getColor(R.styleable.Indicator_indicatorColor,0);
        ta.recycle();
    }

    public PagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec)
    {
        setMeasuredDimension(measureWidth(widthMeasureSpec),measureHeight(heightMeasureSpec));
        mWidth=getMeasuredWidth();
        mHeight=getMeasuredHeight();
        initView();
    }
    public void initView()
    {
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        if (mColor==0)
        {
            mPaint.setColor(Color.parseColor("#eeefebeb"));
        }else
        {
            mPaint.setColor(mColor);
        }
        mPaint.setStyle(Paint.Style.FILL);
    }

    private int measureWidth(int widthMeasureSpec)
    {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int result = 0;
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = 200;
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(size, 200);
            }
        }
        return result;
    }
    private int measureHeight(int heightMeasureSpec)
    {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int result = 0;
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = 50;
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(size, 50);
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        drawIndicator(canvas);
    }
    private void drawIndicator(Canvas canvas)
    {
        float cycleY=mHeight/2;
        float surplusX = mWidth - ((indicatorNum - 1) * (radius * 2 + space) + indicatorRadius * 2);
        float cycleX = surplusX / 2;//让指示器居中显示
        for (int i = 0; i < indicatorNum; i++) {

            if (i == currentItem) {
                cycleX += indicatorRadius;//根据上一次计算的指示器起点的x坐标计算当前圆心的X坐标
                canvas.drawCircle(cycleX, cycleY, indicatorRadius, mPaint);
                cycleX += indicatorRadius + space;//计算下一个指示器的起点x坐标
            } else {
                cycleX += radius;
                canvas.drawCircle(cycleX, cycleY, radius, mPaint);
                cycleX += radius + space;
            }
        }
    }

    public void setViewPager(ViewPager viewPager)
    {
        indicatorNum=viewPager.getAdapter().getCount();
        Log.e(TAG, "setViewPager:2 " + viewPager.getAdapter().getCount());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentItem=position;
                Log.e(TAG, "onPageSelected: " + "position:" + position);
                invalidate();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        invalidate();
    }
}























