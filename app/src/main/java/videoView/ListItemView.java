package videoView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ithei.andy.myapplication.R;

/**
 *
 * 视频列表对象
 * Created by xx on 2016/10/27.
 */
public class ListItemView extends FrameLayout{

    private ImageView mIv_play;
    private MyVideoView mVv;

    public ListItemView(Context context) {
        this(context,null);
    }

    public ListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        View view = View.inflate(getContext(), R.layout.list_item, null);
        mIv_play = (ImageView) view.findViewById(R.id.iv_play);
        this.addView(view);
    }


    public ImageView getIvPlay(){
        return mIv_play;
    }

    public void addVideoView(MyVideoView myVideoView) {
        this.mVv = myVideoView;
        this.addView(myVideoView.getRootView());
    }

    public void release() {
        //不仅要把视频停止掉，还要将播放视频的view删除
        this.mVv.release();
        this.removeView(this.mVv.getRootView());
    }
}
