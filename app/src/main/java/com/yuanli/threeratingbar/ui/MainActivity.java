package com.yuanli.threeratingbar.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.yuanli.threeratingbar.R;
import com.yuanli.threeratingbar.widget.ThreeLevelRatingBarView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.ratingTotal)
    ThreeLevelRatingBarView ratingTotal;

    String rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //代码操作与系统控件操作方法一致
        ratingTotal.setRating(2);//代码设置星级
        rating= String.valueOf(ratingTotal.getRating());//获取当前星级
        ratingTotal.setClickable(true);//默认可点击不用设置，false为不可点击


        //点击设置星级
        ratingTotal.setOnRatingBarChangeListener(new ThreeLevelRatingBarView.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(float RatingScore) {
                Toast.makeText(MainActivity.this, RatingScore+"",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
