package listener;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * @创建者 AndyYan
 * @创建时间 2018/8/22 13:57
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */
public class MyGestureListener1 extends GestureDetector.SimpleOnGestureListener {
    private Button  mButton;
    private Context mContext;
    private int     mOriginButtonTop;

    public MyGestureListener1(Context context, Button button, int originButtonTop) {
        this.mButton = button;
        this.mContext = context;
        this.mOriginButtonTop = originButtonTop;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (Math.abs(distanceY) > Math.abs(distanceX)) {//判断是否竖直滑动
            int buttonTop = mButton.getTop();
            int buttonBottom = mButton.getBottom();

            //是否向下滑动
            boolean isScrollDown = e1.getRawY() < e2.getRawY() ? true : false;

            //根据滑动方向判断是否需要移动Button的位置
            if (!ifNeedScroll(isScrollDown))
                return false;

            if (isScrollDown) {
                //下滑上移Button
                mButton.setTop(buttonTop - (int) Math.abs(distanceY));
                mButton.setBottom(buttonBottom - (int) Math.abs(distanceY));
            } else if (!isScrollDown) {
                //上滑下移Button
                mButton.setTop(buttonTop + (int) Math.abs(distanceY));
                mButton.setBottom(buttonBottom + (int) Math.abs(distanceY));
            }
        }

        return super.onScroll(e1, e2, distanceX, distanceY);
    }

    //写一个方法，根据滑动方向，判断按钮是否应该继续滑动
    private boolean ifNeedScroll(boolean isScrollDown) {
        int nowButtonTop = mButton.getTop();

        //手指向下滑动，应该上移Button，button不能超出原来的上边界
        if (isScrollDown && nowButtonTop <= mOriginButtonTop)
            return false;

        //手指向上滑动的时候，判断按钮是否在屏幕范围内，如果不在，则不需要再移动位置
        if (!isScrollDown) {
            return isInScreen(mButton);
        }

        return true;
    }

    //判断一个控件是否在屏幕范围内
    private boolean isInScreen(View view) {
        int width, height;
        Point p = new Point();
        Activity activity = (Activity) mContext;
        activity.getWindowManager().getDefaultDisplay().getSize(p);
        width = p.x;
        height = p.y;

        Rect rect = new Rect(0, 0, width, height);

        if (!view.getLocalVisibleRect(rect))
            return false;

        return true;
    }
}
