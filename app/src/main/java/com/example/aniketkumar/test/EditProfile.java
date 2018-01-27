package com.example.aniketkumar.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditProfile extends AppCompatActivity {
    String name,email,phone;
    EditText ename,ephone,eemail;
    Button done;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ename=findViewById(R.id.name1);
        ephone=findViewById(R.id.phone1);
        eemail=findViewById(R.id.email1);
        done=findViewById(R.id.done);
        name=getIntent().getStringExtra("name");
        email=getIntent().getStringExtra("email");
        phone=getIntent().getStringExtra("phone");
        ename.setText(name);
        ephone.setText(phone);
        eemail.setText(email);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),My_account.class));
            }
        });

    }
}
