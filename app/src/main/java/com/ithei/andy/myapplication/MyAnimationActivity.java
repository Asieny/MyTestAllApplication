package com.ithei.andy.myapplication;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * @创建者 AndyYan
 * @创建时间 2018/12/5 15:31
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */
public class MyAnimationActivity extends Activity implements View.OnClickListener {
    private Button         startBtn;
    private Button         bt_py;
    private Button         bt_sf;
    private Button         bt_xz;
    private Button         bt_tm;
    private ObjectAnimator animatorT;
    private ObjectAnimator animatorSc;
    private ObjectAnimator animatorXz;
    private ObjectAnimator animatorTm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_animation);
        initView();
        initData();
    }

    private void initView() {
        startBtn = (Button) findViewById(R.id.startBtn);
        bt_py = (Button) findViewById(R.id.bt_py);
        bt_sf = (Button) findViewById(R.id.bt_sf);
        bt_xz = (Button) findViewById(R.id.bt_xz);
        bt_tm = (Button) findViewById(R.id.bt_tm);
    }

    private void initData() {
        animatorT = ObjectAnimator.ofFloat(startBtn, "translationX", 0f, 10f, 50f, 100f, 200f, 0f);
        PropertyValuesHolder scx = PropertyValuesHolder.ofFloat("scaleX", 2.0f, 1f);
        PropertyValuesHolder scy = PropertyValuesHolder.ofFloat("scaleY", 2.0f,3f,1f);
        animatorSc = ObjectAnimator.ofPropertyValuesHolder(startBtn, scx, scy);
        animatorXz = ObjectAnimator.ofFloat(startBtn, "rotation",0f,90f, 180f, 0f);
        animatorTm = ObjectAnimator.ofFloat(startBtn, "alpha",1f,0.5f, 0.1f, 1f);

        Animator animator = AnimatorInflater.loadAnimator(MyAnimationActivity.this, R.animator.set_animator);
        // 载入XML动画
        animator.setTarget(startBtn);
        // 设置动画对象
        animator.start();
        // 启动动画

        bt_py.setOnClickListener(this);
        bt_sf.setOnClickListener(this);
        bt_xz.setOnClickListener(this);
        bt_tm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_py:
                animatorT.setDuration(2000);
                animatorT.start();
                break;
            case R.id.bt_sf:
                animatorSc.setDuration(2000);
                animatorSc.start();
                break;
            case R.id.bt_xz:
                animatorXz.setDuration(2000);
                animatorXz.start();
                break;
            case R.id.bt_tm:
                animatorTm.setDuration(2000);
                animatorTm.start();
                break;
        }
    }
}
