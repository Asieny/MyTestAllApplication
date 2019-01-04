package activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.ithei.andy.myapplication.R;

/**
 * @创建者 AndyYan
 * @创建时间 2018/12/13 15:31
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */
public class MyScrollButtonActivity extends Activity implements View.OnClickListener {
    private RelativeLayout rlt_title;
    private ScrollView     scrollView;
    private Button         startBtn;
    private Button         hideBtn;
    private String TAG = "距离：";
    private ObjectAnimator animatorT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_show_hide_title);
        initView();
        initData();
    }

    private void initView() {
        rlt_title = (RelativeLayout) findViewById(R.id.rlt_title);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        startBtn = (Button) findViewById(R.id.startBtn);
        hideBtn = (Button) findViewById(R.id.hideBtn);
    }

    int downLeft;
    int downTop;
    int moveLeft;
    int moveTop;
    int viewLeft;
    int viewTop;

    int finalLeft;
    int finalTop;
    int finalRight;
    int finalBottom;

    int winWidh;
    int winHeigth;

    private void initData() {
        animatorT = ObjectAnimator.ofFloat(hideBtn, "translationX", 0f, 10f, 50f, 100f, 200f);
        startBtn.setOnClickListener(this);

        //通过Resources获取屏幕宽高
        DisplayMetrics dm = getResources().getDisplayMetrics();
        winHeigth = dm.heightPixels;
        winWidh = dm.widthPixels;
        //获取状态栏高度
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        winHeigth = winHeigth - result;
        startBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 获取Action
                int ea = event.getAction();
                switch (ea) {
                    case MotionEvent.ACTION_DOWN: // 按下

                        viewLeft = v.getLeft();//获取按钮的左坐标(在屏幕上的位置)
                        viewTop = v.getTop();//获取按钮的上坐标

                        downLeft = (int) event.getRawX();//获取点击位置(距离屏幕原点横坐标)
                        downTop = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE: // 移动
                        // 移动中动态设置位置
                        moveLeft = (int) event.getRawX();
                        moveTop = (int) event.getRawY();

                        //view最终左坐标(移动后)
                        finalLeft = viewLeft + (moveLeft - downLeft);
                        //view最终上坐标
                        finalTop = viewTop + (moveTop - downTop);

                        //判断，不使按钮移出屏幕外
                        if (finalLeft <= 0) {
                            finalLeft = 0;
                        }
                        if (finalTop <= 0) {
                            finalTop = 0;
                        }
                        finalRight = finalLeft + v.getWidth();
                        finalBottom = finalTop + v.getHeight();
                        if (finalRight >= winWidh) {
                            finalRight = winWidh;
                            finalLeft = finalRight - v.getWidth();
                        }
                        if (finalBottom >= winHeigth) {
                            finalBottom = winHeigth;
                            finalTop = finalBottom - v.getHeight();
                        }
                        Log.i(TAG, "left" + finalLeft + ",top" + finalTop + ",right" + finalRight + ",bottom" + finalBottom);

                        //重置按钮的位置
                        v.layout(finalLeft, finalTop, finalRight, finalBottom);
                        hideBtn.layout(finalLeft, finalTop, finalRight, finalBottom);
                        //重新设置初始移动位置
                        downLeft = moveLeft;
                        downTop = moveTop;
                        //设置移动后，获取view的左坐标
                        viewLeft = v.getLeft();
                        viewTop = v.getTop();
                        break;
                    case MotionEvent.ACTION_UP: //抬起
                        // 向四周吸附
                        //                  int dx1 = (int) event.getRawX() - lastX;
                        //                  int dy1 = (int) event.getRawY() - lastY;
                        //                  int left1 = v.getLeft() + dx1;
                        //                  int top1 = v.getTop() + dy1;
                        //                  int right1 = v.getRight() + dx1;
                        //                  int bottom1 = v.getBottom() + dy1;
                        //                  if (left1 < (screenWidth / 2)) {
                        //                      if (top1 < 100) {
                        //                          v.layout(left1, 0, right1, btnHeight);
                        //                      } else if (bottom1 > (screenHeight - 200)) {
                        //                          v.layout(left1, (screenHeight - btnHeight), right1, screenHeight);
                        //                      } else {
                        //                          v.layout(0, top1, btnHeight, bottom1);
                        //                      }
                        //                  } else {
                        //                      if (top1 < 100) {
                        //                          v.layout(left1, 0, right1, btnHeight);
                        //                      } else if (bottom1 > (screenHeight - 200)) {
                        //                          v.layout(left1, (screenHeight - btnHeight), right1, screenHeight);
                        //                      } else {
                        //                          v.layout((screenWidth - btnHeight), top1, screenWidth, bottom1);
                        //                      }
                        //                  }
                        //                  break;
                }
                return false;
            }
        });
        //        scrollView.setOnTouchListener(new View.OnTouchListener() {
        //            @Override
        //            public boolean onTouch(View v, MotionEvent event) {
        //                switch (event.getAction()) {
        //                    case MotionEvent.ACTION_DOWN:
        //                        scDownY = (int) event.getRawY();
        //                        break;
        //                    case MotionEvent.ACTION_MOVE:
        //                        scMoveY = (int) event.getRawY();
        //                        rltTop = rltTop + (scMoveY - scDownY);
        //                        rltBot = rltTop + rlt_title.getHeight();
        //
        //                        if (rltTop >= 0) {
        //                            rltTop = 0;
        //                            rltBot = rltTop + rlt_title.getHeight();
        //                        }
        //                        if (rltBot <= 0) {
        //                            rltBot = 0;
        //                            rltTop = rltBot - rlt_title.getHeight();
        //                        }
        //
        //                        rlt_title.layout(0, rltTop, rlt_title.getWidth(), rltBot);
        //                        scDownY = (int) event.getRawY();
        //                        rltTop = rlt_title.getTop();
        //                        break;
        //                    case MotionEvent.ACTION_UP:
        //                        break;
        //                }
        //                return false;
        //            }
        //        });
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        scDownY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        scMoveY = (int) event.getRawY();
                        rltTop = rltTop + (scMoveY - scDownY);
                        rltBot = rltTop + rlt_title.getHeight();

                        if (rltTop >= 0) {
                            rltTop = 0;
                            rltBot = rltTop + rlt_title.getHeight();
                        }
                        if (rltBot <= 0) {
                            rltBot = 0;
                            rltTop = rltBot - rlt_title.getHeight();
                        }

                        rlt_title.layout(0, rltTop, rlt_title.getWidth(), rltBot);

                        alpha = (rlt_title.getHeight() - Math.abs(rltTop)) * 255 / rlt_title.getHeight();
                        rlt_title.getBackground().mutate().setAlpha(alpha);

                        scDownY = (int) event.getRawY();
                        rltTop = rlt_title.getTop();
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return false;
            }
        });
    }

    int alpha;
    int scDownY;
    int scMoveY;
    int rltTop;
    int rltBot;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startBtn:
                animatorT.setDuration(2000);
                animatorT.start();
                break;
        }
    }
}
