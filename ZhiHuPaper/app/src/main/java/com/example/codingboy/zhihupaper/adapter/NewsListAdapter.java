package com.example.codingboy.zhihupaper.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.codingboy.zhihupaper.R;
import com.example.codingboy.zhihupaper.asyncTask.ImageLoader;
import com.example.codingboy.zhihupaper.domin.News;
import com.example.codingboy.zhihupaper.util.DateFomatUtil;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by codingBoy on 16/8/11.
 */
public class NewsListAdapter extends BaseAdapter
{

    private final ArrayList<News.NewsDetail> mData;
    private Context mContext;
    private final LayoutInflater mInflater;
    private final ImageLoader mImageLoader;

    public NewsListAdapter(ArrayList<News.NewsDetail> data,Context context,ListView listView)
    {
        mData=data;
        mContext=context;
        mInflater=LayoutInflater.from(mContext);
        mImageLoader=new ImageLoader(listView);
    }

    public void refreshData(ArrayList<News.NewsDetail> data)
    {
        mData.clear();
        mData.addAll(data);
        this.notifyDataSetChanged();
    }
    public void refreshLoadMore(ArrayList<News.NewsDetail> data)
    {
        mData.addAll(data);
        this.notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        News.NewsDetail detail= (News.NewsDetail) getItem(position);
        if (detail.type.equals("0"))
        {
            ViewHolder viewHolder=null;
            if ((convertView!=null&&convertView.getTag()==null)||convertView==null)
            {
                convertView=mInflater.inflate(R.layout.list_item,parent,false);
                viewHolder=new ViewHolder();
                viewHolder.mTextView= (TextView) convertView.findViewById(R.id.tv_title);
                viewHolder.mImageView= (ImageView) convertView.findViewById(R.id.iv_itemicon);
                convertView.setTag(viewHolder);
            }else {
                viewHolder= (ViewHolder) convertView.getTag();
            }
            viewHolder.mTextView.setText(detail.title);
            String imageUrl=detail.images[0];
            Log.e("TAG1", "posion: " + position + ",url:" + imageUrl);
            viewHolder.mImageView.setTag(imageUrl);
            mImageLoader.loadImage(imageUrl,viewHolder.mImageView);
        }else
        {
            convertView=mInflater.inflate(R.layout.list_tab,parent,false);
            TextView tab= (TextView) convertView.findViewById(R.id.tv_list_tab_item);
            String todayTime= DateFomatUtil.getTime(new Date());
            if (todayTime.equals(detail.time))
            {
                tab.setText("今日热闻");
            }else {
                tab.setText(detail.time);
            }
        }
        return convertView;
    }


    class ViewHolder{
        public ImageView mImageView;
        public TextView mTextView;
    }
}
