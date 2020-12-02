package com.shaen.weatherclockwidget.rotateanimation;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import com.shaen.weatherclockwidget.R;
import java.util.Random;

public class RotateAnimationActivity extends AppCompatActivity {

        Button btn,btn1;
        ImageView target,arrow;
        Random rr=new Random();
        int degree =0,degreeold=0;
        RotateAnimation rotate;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_rotate_animation);

            btn=findViewById(R.id.btn);
            btn1=findViewById(R.id.btn1);
            target=findViewById(R.id.target);
            arrow=findViewById(R.id.arrow);

            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle("轉盤");

        }

        public void onResume(){
            super.onResume();

            target.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewrun();
                }});

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewrun();
                }});


            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rotate.setDuration(0);
                }});

        }

        public void viewrun(){
        degreeold = degree%360;
        degree = rr.nextInt(3600)+720;
        rotate = new RotateAnimation
                (degreeold,degree,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        rotate.setDuration(3600);/*旋轉的週期時間*/
        rotate.setFillAfter(true);/*則此動畫執行的轉換將在完成後持續*/
        rotate.setInterpolator(new DecelerateInterpolator());/*new DecelerateInterpolator()越来越慢*/
        rotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }});
        target.startAnimation(rotate);/*rotate執行動畫*/

    }
        }



