package com.yuanli.threeratingbar.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * 项目名称：ThreeRatingBar
 * 创建人：李元利
 * 创建时间：2017/10/9 17:44
 * 描述：获取点击的比例
 */

@SuppressLint("AppCompatCustomView")
public class StarView extends ImageView implements View.OnTouchListener {

    private String TAG = "RatingBar";

    private Context mContext;
    private GestureDetector mGestureDetector;

    private float rating;
    private int size;

    //点击回调接口，监听用户设置的数量
    private StarView.OnStarViewChangeListener onStarViewChangeListener;

    public interface OnStarViewChangeListener {
        void onViewChanged(float RatingScore,int size);
    }

    public void setOnStarViewChangeListener(StarView.OnStarViewChangeListener onStarViewChangeListener) {
        this.onStarViewChangeListener = onStarViewChangeListener;
    }


    public StarView(Context context,int i) {
        super(context);
        this.size=i;
        initData(context);
    }



    private void initData(Context context) {
        this.mContext = context;
        super.setOnTouchListener(this);
        super.setClickable(true);
        super.setLongClickable(true);
        super.setFocusable(true);
        mGestureDetector = new GestureDetector(mContext, new MyGestureListener());
        mGestureDetector.setOnDoubleTapListener(new MyGestureListener());
    }

    /*
       * 当该view上的事件被分发到view上时触发该方法的回调
       * 如果这个方法返回false时,该事件就会被传递给Activity中的onTouchEvent方法来处理
       * 如果该方法返回true时，表示该事件已经被onTouch函数处理玩，不会上传到activity中处理
       * 该方法属于View.OnTouchListening接口
       * */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    /*
    *
    * 手势监听类
    * */
    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        public MyGestureListener() {
            super();
        }


        @Override
        /*
        *每按一下屏幕立即触发
        * */
        public boolean onDown(MotionEvent e) {
            Log.e(TAG, "onDown");
            return false;
        }

        @Override
        /*
        *用户按下屏幕并且没有移动或松开。主要是提供给用户一个可视化的反馈，告诉用户他们的按下操作已经
        * 被捕捉到了。如果按下的速度很快只会调用onDown(),按下的速度稍慢一点会先调用onDown()再调用onShowPress().
        * */
        public void onShowPress(MotionEvent e) {
            Log.e(TAG, "onShowPress");
        }

        @Override
        /*
        *一次单纯的轻击抬手动作时触发
        * */
        public boolean onSingleTapUp(MotionEvent e) {
            Log.e(TAG, "onSingleTapUp");
            //点击的位置比view的宽度来获取rating
            rating=e.getX()/getWidth();
            if(onStarViewChangeListener!=null){
                onStarViewChangeListener.onViewChanged(rating,size);
            }

            return false;
        }


    }
}
