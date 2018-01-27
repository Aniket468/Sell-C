package com.example.aniketkumar.test;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by MNNIT on 1/14/2018.
 */

public class forgot_pass extends AppCompatActivity {
    EditText email,phone;
    TextView forgot;
    String email1,phone1,forgot1;
    Button getpassword;
    ScrollView scrollView;
    TextView login,getForgot;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_pass);
        login= findViewById(R.id.login_link);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.mobile);
        forgot=findViewById(R.id.forgot);
        getForgot=findViewById(R.id.forgot);
        scrollView=findViewById(R.id.sc);
        getpassword=findViewById(R.id.getpassword);

        //On clicking the login link
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(forgot_pass.this,Login_Activity.class);
                startActivity(intent);
                finish();
            }
        });
        getpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email1=email.getText().toString();
                phone1=phone.getText().toString();
                if(email1.equals("")||phone1.equals("")){
                    Toast.makeText(getApplicationContext(),"Fill all the entries",Toast.LENGTH_LONG).show();
                }
                else{
                    go();
                }
            }
        });


    }
    private void go()
    {
        String user_email=email.getText().toString();
        String user_phone=phone.getText().toString();
      //  if(validate(user_email,user_phone)) {
            BackgroundTask12 backgroundTask12 = new BackgroundTask12();
            backgroundTask12.execute(email1, phone1);
      //  }
        return;
    }

    public boolean validate(String user_email,String user_phone)
    {
        boolean flag1=true;
        if(user_email.isEmpty()|| !Patterns.EMAIL_ADDRESS.matcher(user_email).matches())
        {
            flag1=false;
            email.setError("Please Fill Proper Email Address");
        }
        else
        {
            email.setError(null);
        }

        if(user_phone.isEmpty())
        {
            phone.setError("Please Fill out this field");
            flag1=false;
        }
        else
        {
            phone.setError(null);
        }
        return flag1;
    }


    public class BackgroundTask12 extends AsyncTask<String, Void, Void> {
        String user_url = "http://192.168.43.210/test_connection/forgotpassword.php";
        String res;
        String ID;
        Spinner spinner;
        int getRequestCode=0;
        HashMap<String, String> contact = new HashMap<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //spinner.setVisibility(View.VISIBLE);

        }

        @Override
        protected Void doInBackground(String... params) {

            String id = params[0];
            ID = id;

            Log.d("TAGG::", "2");
            try {
                URL url = new URL(user_url);
                Log.d("TAGG::", "3");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String data = URLEncoder.encode("EMAIL", "UTF-8") + "=" + URLEncoder.encode(email1, "UTF-8")+"&"+
                        URLEncoder.encode("PHONE", "UTF-8") + "=" + URLEncoder.encode(phone1, "UTF-8");
                Log.d("TAGG", "data=" + data);
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream is = new BufferedInputStream(httpURLConnection.getInputStream());
                res = convertStreamToString(is);
                Log.d("results", "res=" + res);

            } catch (MalformedURLException e) {
                Log.d("TAGG::", "error1=" + e.toString());
            } catch (IOException e) {
                Log.d("TAGG::", "error2=" + e.toString());
            }

            return null;
        }
        private String convertStreamToString(InputStream is) {

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Log.e("TAGG::",res+"");

            if(res==null)
            {
                Snackbar snackbar = Snackbar
                        .make(scrollView, "Can't Connect to the server!!", Snackbar.LENGTH_LONG)
                        .setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                go();
                            }
                        });
                snackbar.show();
            }
            else if(res.contains("wrong"))
            {
                Snackbar snackbar = Snackbar
                        .make(scrollView, "Cannot Find The Details You entered", Snackbar.LENGTH_LONG)
                        .setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                go();
                            }
                        });
                snackbar.show();
            }
            else
                getForgot.setText("Your password is : "+res);
        }
    }

}
