package ZDYview;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @创建者 AndyYan
 * @创建时间 2018/3/1 11:01
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */
public class MyTextView extends TextView {
    long mTime;
    int  mPostion;

    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTime(long time, int postion) {
        this.mTime = time;
        this.mPostion = postion;
    }


    public void startchangeTime() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                handler.postDelayed(this, 1000);//递归执行，一秒执行一次
                if (mTime > 0) {
                    mTime--;
                    setText("" + mTime);
                } else {
                    setText("结束");
                    handler.removeCallbacks(this);
                }
            }
        }, 1000);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
