package com.ithei.andy.myapplication;

/**
 * @创建者 AndyYan
 * @创建时间 2018/5/20 22:29
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */
public class User {
    //声明一个接口对象为成员变量
    private OnEatListener mOnEatListener;

    public void eat(String something){
        System.out.println("吃"+something);

        if(mOnEatListener!=null){
            mOnEatListener.onEat(this,something);
        }
    }

    //定义方法，用于设置监听器
    public void setOnEatListener(OnEatListener onEatListener){
        this.mOnEatListener=onEatListener;
    }

    //定义一个内部回调接口
    public interface OnEatListener{
        //回调方法
        public void onEat(User user,String something);
    }
}
