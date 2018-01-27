package com.example.aniketkumar.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

public class Search_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_activity);

        Intent i=getIntent();
        String s1=i.getStringExtra("search");
        TextView tv=(TextView)findViewById(R.id.textView_search);
        tv.setText("Searching for "+s1+" Cycles");

    }
}
