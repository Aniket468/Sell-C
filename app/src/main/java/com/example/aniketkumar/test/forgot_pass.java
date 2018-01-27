package com.example.aniketkumar.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by MNNIT on 1/14/2018.
 */

public class forgot_pass extends AppCompatActivity {

    TextView login;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_pass);
        login= findViewById(R.id.login_link);

        //On clicking the login link
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(forgot_pass.this,Login_Activity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}
