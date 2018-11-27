package videoView;

import android.content.Context;
import android.util.AttributeSet;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * 专门用来播放视频的管理类
 * Created by xx on 2016/10/27.
 */
public class MyVideoView extends JCVideoPlayerStandard{
    private Context               mContext;
    private MyVideoView           mView;//播放视频的view 界面
    private JCVideoPlayerStandard mVideo_view;
    private String                mUrl;

    public MyVideoView(Context context) {
        super(context);
        this.mContext = context;
    }


    public MyVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //    public MyVideoView(Context context) {
//        this.mContext = context;
//        mView = View.inflate(context, R.layout.video_view_item, null);
//        mVideo_view = (JCVideoPlayerStandard) mView.findViewById(R.id.video_view);
//    }


    public MyVideoView getRootView() {
        return this;
    }

    public void setUrl(String url,int kind,String title) {
        this.mUrl = url;
        this.setUp(mUrl, kind,title);
    }

    public void playView() {
        this.startVideo();
    }

    public void release() {
        this.release();//设置视频播放释放操作
    }
}
