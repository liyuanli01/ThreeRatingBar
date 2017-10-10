package com.yuanli.threeratingbar.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yuanli.threeratingbar.R;


/**
 * 项目名称：esparclientios
 * 创建人：李元利
 * 创建时间：2017/9/20 16:10
 * 描述：三级评价样式
 */

public class ThreeLevelRatingBarView extends LinearLayout {

    private String TAG = "RatingBar";

    //点击回调接口，监听用户设置的数量
    private OnRatingBarChangeListener onRatingBarChangeListener;

    public interface OnRatingBarChangeListener {
        void onRatingChanged(float RatingScore);
    }

    public void setOnRatingBarChangeListener(OnRatingBarChangeListener onRatingBarChangeListener) {
        this.onRatingBarChangeListener = onRatingBarChangeListener;
    }

    private boolean mClickable = true;

    //设置可否点击
    public void setClickable(boolean clickable) {
        this.mClickable = clickable;
    }

    //默认间距、大小、数量
    private static final int DEFAULT_DIVIDER_WIDTH = 0;
    private static final int DEFAULT_STAR_SIZE = 50;
    private static final int DEFAULT_STAR_COUNT = 5;

    private int mDividerWidth = DEFAULT_DIVIDER_WIDTH;
    private float starImageSize = DEFAULT_STAR_SIZE;
    private int starCount = DEFAULT_STAR_COUNT;
    private float mStarCount;

    private float rating;

    private Drawable starDrawable;
    private Drawable starDrawablehalf;

    private Drawable selection_icon_bad;
    private Drawable selection_icon_good;
    private Drawable selection_icon_half_bad;
    private Drawable selection_icon_half_good;
    private Drawable default_icon;

    public ThreeLevelRatingBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.HORIZONTAL);
        //获取自定义属性集
        TypedArray typedArray = context.obtainStyledAttributes(
                attrs, R.styleable.ThirdRatingBarView);
        //从TypedArray取出对应的值来为要设置的属性赋值
        default_icon = typedArray.getDrawable(R.styleable.ThirdRatingBarView_default_icon);
        selection_icon_bad = typedArray.getDrawable(R.styleable.ThirdRatingBarView_selection_icon_bad);
        selection_icon_good = typedArray.getDrawable(R.styleable.ThirdRatingBarView_selection_icon_good);
        selection_icon_half_bad = typedArray.getDrawable(R.styleable.ThirdRatingBarView_selection_icon_half_bad);
        selection_icon_half_good = typedArray.getDrawable(R.styleable.ThirdRatingBarView_selection_icon_half_good);
        mDividerWidth = typedArray.getDimensionPixelSize(R.styleable.ThirdRatingBarView_dividerWidth, DEFAULT_DIVIDER_WIDTH);
        starImageSize = typedArray.getFloat(R.styleable.ThirdRatingBarView_starImageSize, DEFAULT_STAR_SIZE);//星星大小
        starCount = typedArray.getInteger(R.styleable.ThirdRatingBarView_starCount, DEFAULT_STAR_COUNT);//星星总数
        rating = typedArray.getFloat(R.styleable.ThirdRatingBarView_rating, 0);
        typedArray.recycle();

        //画几个星星？
        for (int i = 0; i < starCount; ++i) {
            StarView imageView = getStarImageView(context,i);
            imageView.setOnStarViewChangeListener(new StarView.OnStarViewChangeListener() {
                @Override
                public void onViewChanged(float RatingScore, int size) {
                    if (mClickable) {//是否可点击
                        //获取子view的位置
                        mStarCount = size + RatingScore;//带小数的rating
                        setRating(mStarCount);

                        if (onRatingBarChangeListener != null) {
                            onRatingBarChangeListener.onRatingChanged(mStarCount);
                        }
                    }
                }
            });

//            imageView.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mClickable) {//是否可点击
//                        //获取子view的位置
//                        mStarCount = indexOfChild(v) + 1;//只能是整数rating
//                        setRating(mStarCount);
//
//                        if (onRatingBarChangeListener != null) {
//                            onRatingBarChangeListener.onRatingChanged(mStarCount);
//                        }
//                    }
//                }
//            });
            addView(imageView);
        }

        setRating(rating);
    }


    //画一个星星
    private StarView getStarImageView(Context context,int i) {
        StarView imageView = new StarView(context,i);
        ViewGroup.LayoutParams para = new ViewGroup.LayoutParams(
                Math.round(starImageSize), Math.round(starImageSize));
        imageView.setLayoutParams(para);
        imageView.setPadding(0, 0, mDividerWidth, 0);
        imageView.setImageDrawable(default_icon);
        imageView.setMaxWidth(24);
        imageView.setMaxHeight(24);
        return imageView;
    }

    //设置星星数
    public void setRating(float starCount) {
        //大于总数用自己，小于0用0（写完了发现网上有例子，优化下）
        starCount = starCount > this.starCount ? this.starCount : starCount;
        starCount = starCount < 0 ? 0 : starCount;

        rating = starCount;

        float decimals_count = starCount - (int) (starCount); //计算分数的小数部分
        int interger_count = (int) starCount; //计算分数的整数部分

        if (starCount <= 1) {
            starDrawable = selection_icon_bad;
            starDrawablehalf=selection_icon_half_bad;
        } else {
            starDrawable = selection_icon_good;
            starDrawablehalf=selection_icon_half_good;
        }


        //这里为了设置半星的图而准备,现在没有半星的图片，先这样，逻辑已有，到时候微调一下

        //先把整数外的其他行星设为空
        for (int i = this.starCount - 1; i >= interger_count; --i) {
            ((ImageView) getChildAt(i)).setImageDrawable(default_icon);
        }

        if (decimals_count > 0) {//如果有小数
            //整数设为全图
            for (int i = 0; i < interger_count; ++i) {
                ((ImageView) getChildAt(i)).setImageDrawable(starDrawable);
            }
            //最后一个设为半星的(四舍五入)
            if (decimals_count < 0.5)
                ((ImageView) getChildAt(interger_count)).setImageDrawable(starDrawablehalf);
            else
                ((ImageView) getChildAt(interger_count)).setImageDrawable(starDrawable);
        } else {//如果是整数，直接上背景
            for (int i = 0; i < starCount; ++i) {
                ((ImageView) getChildAt(i)).setImageDrawable(starDrawable);
            }

        }


    }

    //以下均可由代码设置
    public float getStarCount() {
        return mStarCount;
    }

    public void setSelection_icon_bad(Drawable selection_icon_bad) {
        this.selection_icon_bad = selection_icon_bad;
    }

    public void setSelection_icon_good(Drawable selection_icon_good) {
        this.selection_icon_good = selection_icon_good;
    }

    public void setDefault_icon(Drawable default_icon) {
        this.default_icon = default_icon;
    }

    public void setStarCount(int startCount) {
        this.starCount = startCount;
    }

    public void setStarImageSize(float starImageSize) {
        this.starImageSize = starImageSize;
    }

    public float getRating() {
        return rating;
    }
}