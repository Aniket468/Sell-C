package com.example.aniketkumar.test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {
   ImageView iv;
    TextView tv;
    private int log;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        iv=(ImageView)findViewById(R.id.img);

        tv=(TextView)findViewById(R.id.sell);

        Handler mhandler =new Handler();



        mhandler.postDelayed(new Runnable() {
            @Override
            public void run() {


                sp=getApplicationContext().getSharedPreferences("Shared",MODE_PRIVATE);
                String logi=sp.getString("email",null);
                if(logi==null) {
                    Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },2000);










        //iv.setAnimation(ani);
        //tv.setAnimation(ani);
//        ani.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                     if(log==0){
//                     finish();
//                     startActivity(new Intent(getApplicationContext(),Login_Activity.class));}
//                else
//                     {
//                         finish();
//                         startActivity(new Intent(getApplicationContext(),MainActivity.class));
//                     }
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
    }
}
