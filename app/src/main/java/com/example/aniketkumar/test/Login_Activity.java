package com.example.aniketkumar.test;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.icu.text.LocaleDisplayNames;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;

/**
* Created by MNNIT on 1/11/2018.
*/

public class Login_Activity extends AppCompatActivity {

    TextView tv;
    Button continuewithoutlogin;
    ScrollView scrollView;
    Button login_button;
    TextView sign_up;
    TextView forgot;
    EditText user_email,password;


    @Override
    public void onBackPressed() {
        //  super.onBackPressed();


        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        builder.setTitle("Warning!!!");
        builder.setMessage("Are you really want to Exit");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }


    @Override
    protected void onCreate(
            @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        login_button= (Button) findViewById(R.id.login_press);
        tv=findViewById(R.id.sign_link);
        tv.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        scrollView=findViewById(R.id.scroll);
        user_email=findViewById(R.id.emailid);
        password=findViewById(R.id.password);
        forgot=findViewById(R.id.forgot);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Sign_up.class));

                finish();

            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),forgot_pass.class));
            }
        });

        continuewithoutlogin= (Button) findViewById(R.id.withoutlogin);
        continuewithoutlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                finish();

            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_check();
            }
        });

        }




        public void login_check()
        {
            String email=user_email.getText().toString();
            String pass=password.getText().toString();
            if(!validate(email,pass))
            {
                Snackbar snackbar=Snackbar.make(scrollView,"Please Fill Out The Fields",Snackbar.LENGTH_SHORT);
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.YELLOW);
                snackbar.show();
                return;
            }
            else
            {
                BackGroundClass backGroundClass=new BackGroundClass();
                backGroundClass.execute(email,pass);
            }
        }

        public boolean validate(String email,String pass)
        {
            boolean flag1=true,flag2=true;
            if(email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
            {
                user_email.setError("Please Fill Email Address in a proper format");
                flag1=false;
            }
            else
            {
                user_email.setError(null);
                flag1=true;
            }
            if(pass.isEmpty())
            {
                password.setError("Please Fill the password Column");
                flag2=false;
            }
            else
            {
                password.setError(null);
                flag2=true;
            }

            return flag1&flag2;
        }

     public class BackGroundClass extends AsyncTask<String,Void,Void>
    {

        private String result;

        @Override
        protected Void doInBackground(String... strings) {

            String email=strings[0];
            String password=strings[1];
            String connectionUrl="http://192.168.43.210/test_connection/retrieve.php";

            try {
                URL url=new URL(connectionUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                OutputStream out = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                String data = URLEncoder.encode("EMAIL", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                        URLEncoder.encode("PASSWORD", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                        Log.d("data", data);
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                result = convertStreamToString(inputStream);
                httpURLConnection.disconnect();
                Log.d("TAG",result+"");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private String convertStreamToString(InputStream inputStream) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder("");
            String line;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if(result==null)
            {
                Log.d("Tag","null");
               // sign_up.setEnabled(true);
                Snackbar snackbar = Snackbar
                        .make(scrollView, "Connection Error!!", Snackbar.LENGTH_LONG)
                        .setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                login_check();
                            }
                        });

                // Changing message text color
                snackbar.setActionTextColor(Color.RED);

                // Changing action button text color
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.YELLOW);
                snackbar.show();
            }
            else if(result.contains("Failed"))
            {
                Log.d("Tag","Failed");
                // sign_up.setEnabled(true);
                Snackbar snackbar = Snackbar
                        .make(scrollView, "Authentication Error!!", Snackbar.LENGTH_LONG);
                // Changing action button text color
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.YELLOW);
                snackbar.show();
            }
            else
            {
                Toast.makeText(getApplicationContext(),result+"",Toast.LENGTH_LONG).show();


              //  Log.d("TAGG","Error 1");
                JSONArray jsonArray= null;
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    jsonArray = jsonObject.getJSONArray("result");
                    JSONObject c=jsonArray.getJSONObject(0);
                    String id=c.getString("id");
                    String name=c.getString("name");
                    String email=c.getString("email");
                    String phone=c.getString("mobile");
                    Log.d("Tag",phone+"");
                    SharedPreferences sharedpreferences = getApplicationContext().getSharedPreferences("Shared", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("id", id);
                    editor.putString("name", name);
                    editor.putString("email", email);
                    editor.putString("mobile",phone);
                    editor.commit();


                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("TAGG","parsing Error");
                }
              //  Log.d("TAGG","Error 2");


                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}