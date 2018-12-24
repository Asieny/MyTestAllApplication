package com.ithei.andy.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ZDYview.MyTextView;
import activity.HuaDongXianShiYinCang;
import activity.JieTuActivity;
import activity.MineFloatActionButto;
import activity.MyScrollButtonActivity;
import adapter.TitleAdapter;

public class MainActivity extends AppCompatActivity {

    private Button mBt_online_video;
    private Button mBt_recording;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView Mineacttv = (TextView) findViewById(R.id.mianacttv);

        ImageView img_mine001 = (ImageView) findViewById(R.id.Img_mine001);
        mBt_online_video = (Button) findViewById(R.id.bt_online_video);
        mBt_recording = (Button) findViewById(R.id.bt_recording);

        //        img_mine001.setOnTouchListener(new View.OnTouchListener() {
        //            @Override
        //            public boolean onTouch(View v, MotionEvent event) {
        //                if (event.getAction() == MotionEvent.ACTION_DOWN){
        //                    Toast.makeText(getBaseContext(),"图片按下",Toast.LENGTH_SHORT).show();
        //                }else if (event.getAction() == MotionEvent.ACTION_UP){
        //                    Mineacttv.setText("图片点击后抬起而来");
        //                }
        //
        //                return true;
        //            }
        //        });

        //        img_mine001.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                Intent intent = new Intent(getBaseContext(),WebJSActivity.class);
        //                startActivity(intent);
        //            }
        //        });

        img_mine001.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MediaRecorderActivity.class);
                startActivity(intent);
            }
        });

        ListView listView = (ListView) findViewById(R.id.list_item);

        final List<String> mData = new ArrayList<>();
        mData.add("截图识别Demo");
        mData.add("floatButton");
        mData.add("滑动显示隐藏");
        mData.add("MPAndroidChart");
        mData.add("动画效果界面");
        mData.add("upDataOnline");
        mData.add("ForMQTT");
        mData.add("ForMyScrollButton");
        TitleAdapter titleAdapter = new TitleAdapter(MainActivity.this, mData);
        listView.setAdapter(titleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title = mData.get(position);
                if ("截图识别Demo".equals(title)) {
                    Intent JTintent = new Intent(MainActivity.this, JieTuActivity.class);
                    startActivity(JTintent);
                }
                if ("floatButton".equals(title)) {
                    Intent intent = new Intent(MainActivity.this, MineFloatActionButto.class);
                    startActivity(intent);
                }
                if ("滑动显示隐藏".equals(title)) {
                    Intent intent = new Intent(MainActivity.this, HuaDongXianShiYinCang.class);
                    startActivity(intent);
                }
                if ("MPAndroidChart".equals(title)) {
                    Intent intent = new Intent(MainActivity.this, MyMPAndroidChart.class);
                    startActivity(intent);
                }
                if ("动画效果界面".equals(title)) {//动画测试
                    Intent intent = new Intent(MainActivity.this, MyAnimationActivity.class);
                    startActivity(intent);
                }
                if ("upDataOnline".equals(title)) {//在线更新
                    String url = "http://112.90.178.68:8081/upFiles/upload/files/20181204/smartHox12-04-02_1543921756533.apk";

                }
                if ("ForMyScrollButton".equals(title)) {//在线更新
                    Intent intent = new Intent(MainActivity.this, MyScrollButtonActivity.class);
                    startActivity(intent);
                }
            }
        });
        //        MyAdapter myAdapter = new MyAdapter();
        //        listView.setAdapter(myAdapter);
        //        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        //            @Override
        //            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //                Mineacttv.setText(1 + position + "");
        //                PopupWindow popupWindow = null;
        //                View popwincontentview = LayoutInflater.from(MainActivity.this).inflate(R.layout.popwin, null);
        //                TextView nihaoTV = (TextView) popwincontentview.findViewById(R.id.nihao);
        //                TextView wohaoTV = (TextView) popwincontentview.findViewById(R.id.wohao);
        //                TextView dajiahaohaoTV = (TextView) popwincontentview.findViewById(R.id.dajiahao);
        //
        //                if (popupWindow == null) {
        //                    popupWindow = new PopupWindow(popwincontentview, 200, 500, true);
        //                } else {
        //                    //                    popupWindow.dismiss();
        //                }
        //
        //                nihaoTV.setText("你好" + (position + 1));
        //                wohaoTV.setText("我好" + (position + 1));
        //                dajiahaohaoTV.setText("大家好" + (position + 1));
        //                View rootview = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_main, null);
        //                popupWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);
        //            }
        //        });

        mBt_online_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MyVideoplayerActivity.class);
                startActivity(intent);
            }
        });
        mBt_recording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MediaRecorderActivity.class);
                startActivity(intent);
            }
        });
    }


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 300;
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
                convertView = View.inflate(getBaseContext(), R.layout.listviewitem, null);
                myViewHolder = new MyViewHolder();
                myViewHolder.mTV = (TextView) convertView.findViewById(R.id.itemtext);
                myViewHolder.tv_mytextview = (MyTextView) convertView.findViewById(R.id.tv_mytextview);
                convertView.setTag(myViewHolder);
            } else {
                myViewHolder = (MyViewHolder) convertView.getTag();
            }
            myViewHolder.mTV.setText("这是第" + (position + 1) + "个");
            myViewHolder.tv_mytextview.setTime(100 - position, position);
            myViewHolder.tv_mytextview.startchangeTime();
            return convertView;
        }
    }

    class MyViewHolder {
        TextView   mTV;
        MyTextView tv_mytextview;
    }
}
