package com.ithei.andy.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.Date;

import ZDYview.MyTextView;
import ZDYview.TimeTextView;

/**
 * @创建者 AndyYan
 * @创建时间 2018/3/1 17:02
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */
public class ZDYviewActivity extends Activity {

    private MyTextView   mTv_mytextview;
    private TimeTextView mTv_time_text;
    private ListView     mList_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ady_view);
        initView();
        initData();
    }

    private void initView() {
        mList_view = (ListView) findViewById(R.id.list_view);
        mTv_mytextview = (MyTextView) findViewById(R.id.tv_mytextview);
        mTv_time_text = (TimeTextView) findViewById(R.id.tv_time_text);
    }

    private void initData() {
        //        mTv_mytextview.setTime(1000,10);
        //        mTv_mytextview.startchangeTime();
        MyAdapter myAdapter =new MyAdapter();
        mList_view.setAdapter(myAdapter);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 30;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyViewHolder myViewHolder = null;
            if (convertView == null) {
                convertView = View.inflate(getBaseContext(), R.layout.listview_time_item, null);
                myViewHolder = new MyViewHolder();
                myViewHolder.tv_mytextview = (TimeTextView) convertView.findViewById(R.id.tv_mytextview);
                convertView.setTag(myViewHolder);
            } else {
                myViewHolder = (MyViewHolder) convertView.getTag();
            }
            myViewHolder.tv_mytextview.setTimes(new Date().getTime()+(position*1000)+10000);
            return convertView;
        }
    }

    class MyViewHolder {
        TimeTextView tv_mytextview;
    }
}
