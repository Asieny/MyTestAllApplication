package TestView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @创建者 AndyYan
 * @创建时间 2017/11/19 21:16
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */
public class MyTestView extends View {
    public MyTestView(Context context) {
        super(context);
    }

    public MyTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        super.setOnTouchListener(l);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN){

        }

        return super.onTouchEvent(event);
    }

}
