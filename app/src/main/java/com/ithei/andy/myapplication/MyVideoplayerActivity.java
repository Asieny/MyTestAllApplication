package com.ithei.andy.myapplication;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.shuyu.gsyvideoplayer.video.GSYADVideoPlayer;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * @创建者 AndyYan
 * @创建时间 2018/3/13 8:53
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */
public class MyVideoplayerActivity extends AppCompatActivity {

    private JCVideoPlayerStandard mVideo_player;
    private ImageView             mImg_fullscreen;
    private GSYADVideoPlayer      mPower_video_player;
    private ListView              mList_view;
//    private Button                mButton_zanting;
    //    private VideoView mVideo_view;
    private int                   firstVisible;//当前第一个可见的item
    private int                   visibleCount;//当前可见的item个数
    private JCVideoPlayerStandard currPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_videoplayer);
        initView();
        initData();
    }

    private void initView() {
        mVideo_player = (JCVideoPlayerStandard) findViewById(R.id.video_player);
        mImg_fullscreen = (ImageView) mVideo_player.findViewById(R.id.fullscreen);
//        mButton_zanting = (Button) findViewById(R.id.button_zanting);
        //        mPower_video_player = (GSYADVideoPlayer) findViewById(R.id.power_video_player);

        //        mVideo_view = (VideoView) findViewById(R.id.video_view);
        mList_view = (ListView) findViewById(R.id.list_view);
    }

    int currentPosition;

    private void initData() {
        //        getSupportActionBar().hide();
        //        getSupportActionBar().setTitle("jiecaovideoplayer的使用");
        mImg_fullscreen.setVisibility(View.GONE);
        //        mVideo_player.setUp("http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4", JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "嫂子闭眼睛");
        mVideo_player.setUp("http://flv2.bn.netease.com/videolib3/1604/28/fVobI0704/SD/fVobI0704-mobile.mp4", JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "好看吗？");
        mVideo_player.thumbImageView.setImageResource(R.drawable.add_picture);
        mVideo_player.setVisibility(View.GONE);
//        mButton_zanting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //                if (JCMediaManager.instance().mediaPlayer.isPlaying()) {
//                //                    JCMediaManager.instance().mediaPlayer.pause();
//                //                    mVideo_player.startButton.setVisibility(View.VISIBLE);
//                //                    mVideo_player.startButton.setImageResource(R.drawable.jc_click_play_selector);
//                //                    mVideo_player.currentState = 5;
//                //                    currentPosition = JCMediaManager.instance().mediaPlayer.getCurrentPosition();
//                //                } else {
//                //                    JCMediaManager.instance().mediaPlayer.start();
//                //                    mVideo_player.startButton.setVisibility(View.GONE);
//                //                    mVideo_player.currentState = 2;
//                //                }
//                //                System.out.println("" + currentPosition);
//
//            }
//        });


        //        mPower_video_player.setUp("http://flashmedia.eastday.com/newdate/news/2016-11/shznews1125-19.mp4", true, "很好看");
        //        mPower_video_player.release();
        //        mPower_video_player.setLooping(true);
        //        mPower_video_player.startPlayLogic();
        //        GSYVideoViewBridge gsyVideoManager = mPower_video_player.getGSYVideoManager();
        //        gsyVideoManager.getMediaPlayer().start();


        //        mVideo_view.setVideoURI(Uri.parse("http://flashmedia.eastday.com/newdate/news/2016-11/shznews1125-19.mp4"));
        //        mVideo_view.setMediaController(new MediaController(this));
        //        mVideo_view.start();
        MyMarkAdapter myAdapter = new MyMarkAdapter();
        mList_view.setAdapter(myAdapter);
        //停止播放，有效bug，体验不好
        //        oneBUGPlayer();

    }

    boolean getbit = true;
    private JCVideoPlayerStandard mCurrentItem;//记录当前正在播放的条目对象
    String finalImageUrl;

    private class MyMarkAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 5;
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            MyViewHolder myViewHolder = null;
            if (convertView == null) {
                convertView = View.inflate(getBaseContext(), R.layout.list_video_item, null);
                myViewHolder = new MyViewHolder();
                myViewHolder.mVideoplayer = (JCVideoPlayerStandard) convertView.findViewById(R.id.list_video_player);

                //                final ListItemView listItemView = new ListItemView(MyVideoplayerActivity.this);
                //                listItemView.getIvPlay().setOnClickListener(new View.OnClickListener() {
                //                    @Override
                //                    public void onClick(View v) {
                //                        //点击时，如果之前有播放，先释放
                //                        if (mCurrentItem != null) {
                //                            mCurrentItem.release();
                //                        }
                //                        //在当前条目界面中添加一个播放视频的视图，让它播放
                //                        MyVideoView myVideoView = new MyVideoView(MyVideoplayerActivity.this);
                //                        myVideoView.setUrl("http://flv2.bn.netease.com/videolib3/1604/28/fVobI0704/SD/fVobI0704-mobile.mp4", JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "好看吗？");
                //
                //                        myVideoView.playView();
                //                        listItemView.addVideoView(myVideoView);
                //                        mCurrentItem = listItemView;
                //                        listItemView.getIvPlay().setVisibility(View.GONE);
                //                    }
                //                });
                mCurrentItem = myViewHolder.mVideoplayer;
                convertView.setTag(myViewHolder);
            } else {
                myViewHolder = (MyViewHolder) convertView.getTag();
                //                //不仅要进行复用时释放而且要当前正在播放视频条目对象就是当前的convertView复用对象时才释放
                //                if (mCurrentItem != null && myViewHolder.mVideoplayer == mCurrentItem) {
                //                    //停止当前的正在播放的视频
                //                    myViewHolder.mVideoplayer.release();
                //                }
                if (myViewHolder.mVideoplayer.isCurrentJcvd()) {//这个if是无法取代的，否则进入全屏的时候会releaseMediaPlayer
                    myViewHolder.mVideoplayer.release();
                }
            }

            //            FFmpegMediaMetadataRetriever mm=new FFmpegMediaMetadataRetriever();
            //            Bitmap frameAtTime = null;
            //            try {
            //                mm.setDataSource("http://flv2.bn.netease.com/videolib3/1604/28/fVobI0704/SD/fVobI0704-mobile.mp4");
            //                frameAtTime = mm.getFrameAtTime();
            //            }catch (Exception e){
            //            }finally {
            //                mm.release();
            //            }
            //            if (frameAtTime!=null){
            //                myViewHolder.mVideoplayer.thumbImageView.setImageBitmap(frameAtTime);
            //            }
            String imageUrl = "";
            if (position != 2) {
                imageUrl = "http://flv2.bn.netease.com/videolib3/1604/28/fVobI0704/SD/fVobI0704-mobile.mp4";
            } else {
                imageUrl = "http://flashmedia.eastday.com/newdate/news/2016-11/shznews1125-19.mp4";

            }
            myViewHolder.mVideoplayer.setUp(imageUrl, JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "好看吗？" + position);

            final MyViewHolder finalMyViewHolder = myViewHolder;
            finalImageUrl = imageUrl;
            final Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    if (getbit) {
                        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                        //获取网络视频
                        if (position != 2) {
                            finalImageUrl = "http://flv2.bn.netease.com/videolib3/1604/28/fVobI0704/SD/fVobI0704-mobile.mp4";
                        } else {
                            finalImageUrl = "http://flashmedia.eastday.com/newdate/news/2016-11/shznews1125-19.mp4";
                        }
                        retriever.setDataSource(finalImageUrl, new HashMap<String, String>());
                        //获取本地视频
                        //retriever.setDataSource(url);
                        final Bitmap bitmap = retriever.getFrameAtTime();
                        FileOutputStream outStream = null;
                        try {
                            outStream = new FileOutputStream(new File(getExternalCacheDir().getAbsolutePath() + "/" + "好看吗" + ".jpg"));
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, outStream);
                            outStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        retriever.release();
                        if (bitmap != null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    finalMyViewHolder.mVideoplayer.thumbImageView.setImageBitmap(bitmap);
                                    getbit = false;
                                }
                            });
                        }
                    }
                }
            });
            thread.start();
            return convertView;
        }
    }

    public Bitmap getVideoThumbnail(String filePath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }


    //private JCVideoPlayerStandard mCurrentItem ;//记录当前正在播放的条目对象
    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 5;
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

                final JCVideoPlayerStandard jcVideoPlayer = new JCVideoPlayerStandard(MyVideoplayerActivity.this);

                //                convertView = View.inflate(getBaseContext(), R.layout.list_video_item, null);
                //                myViewHolder = new MyViewHolder();
                //                myViewHolder.mVideoplayer = (JCVideoPlayerStandard) convertView.findViewById(R.id.list_video_player);
                //                convertView.setTag(myViewHolder);
                jcVideoPlayer.setUp("http://flashmedia.eastday.com/newdate/news/2016-11/shznews1125-19.mp4", JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, "好看吗？");
                jcVideoPlayer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击时，如果之前有播放，先释放
                        //                        if (mCurrentItem != null) {
                        //                            mCurrentItem.release();
                        //                        }
                        //在当前条目界面中添加一个播放视频的视图，让它播放
                        JCVideoPlayerStandard jcVideoPlayer2 = new JCVideoPlayerStandard(MyVideoplayerActivity.this);
                        jcVideoPlayer2.setUp("http://flashmedia.eastday.com/newdate/news/2016-11/shznews1125-19.mp4", JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, "好看吗？");
                        jcVideoPlayer2.startVideo();

                        //                        mCurrentItem = jcVideoPlayer;
                    }
                });
                convertView = jcVideoPlayer;
            } else {
                myViewHolder = (MyViewHolder) convertView.getTag();
            }
            //            myViewHolder.mVideoplayer.setUp("http://flashmedia.eastday.com/newdate/news/2016-11/shznews1125-19.mp4", JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, "好看吗？");
            //            myViewHolder.mVideoplayer.thumbImageView.setImageResource(R.drawable.add_picture);

            return convertView;
        }
    }

    class MyViewHolder {
        JCVideoPlayerStandard mVideoplayer;
    }

    private void oneBUGPlayer() {
        mList_view.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        break;

                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        //滑动停止自动播放视频
                        autoPlayVideo(view);
                        break;

                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisible == firstVisibleItem) {
                    return;
                }

                firstVisible = firstVisibleItem;
                visibleCount = visibleItemCount;
            }
        });
    }

    /**
     * 滑动停止自动播放视频
     */
    private void autoPlayVideo(AbsListView view) {

        for (int i = 0; i < visibleCount; i++) {
            if (view != null && view.getChildAt(i) != null && view.getChildAt(i).findViewById(R.id.list_video_player) != null) {
                currPlayer = (JCVideoPlayerStandard) view.getChildAt(i).findViewById(R.id.list_video_player);
                Rect rect = new Rect();
                //获取当前view 的 位置
                currPlayer.getLocalVisibleRect(rect);
                int videoheight = currPlayer.getHeight();
                if (rect.top == 0 && rect.bottom == videoheight) {
                    if (currPlayer.currentState == JCVideoPlayer.CURRENT_STATE_NORMAL
                            || currPlayer.currentState == JCVideoPlayer.CURRENT_STATE_ERROR) {
                        //                        currPlayer.startButton.performClick();
                    }
                    return;
                }
            }
        }
        //释放其他视频资源
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (JCVideoPlayer.backPress()) {
            return;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //点击物理返回键时 判断视频是否全屏播放 是的话销毁全屏
            if (JCVideoPlayer.backPress()) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}

