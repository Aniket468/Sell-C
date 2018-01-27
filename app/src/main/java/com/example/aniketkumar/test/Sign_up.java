package com.example.aniketkumar.test;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

/**
 * Created by MNNIT on 1/14/2018.
 */

public class Sign_up extends AppCompatActivity {

    TextView login;
    private String result;
    private EditText password1, password2, name, email, phone;
    Button sign_up;
    ScrollView scrollView;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        sign_up = (Button) findViewById(R.id.sign_press);
        login = findViewById(R.id.login_link);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.mobile);
        email = findViewById(R.id.email);
        password1 = findViewById(R.id.password);
        password2 = findViewById(R.id.re_password);
        scrollView=findViewById(R.id.scrollViewnew);
        //On clicking the login link
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sign_up.this, Login_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                signUp();
            }
        });
    }

    public void signUp()
    {
        String user_name=name.getText().toString();
        String user_email=email.getText().toString();
        String user_password=password1.getText().toString();
        String user_phone=phone.getText().toString();
        if(!validate())
        {
            sign_up_failed();
           // sign_up.setEnabled(true);
        }
        else
        {
         //   sign_up.setEnabled(false);
            BackgroundTask backgroundTask=new BackgroundTask();
            backgroundTask.execute(user_name,user_email,user_password,user_phone);

        }
    }

    public boolean validate() {
        boolean valid = true;

        String name_user = name.getText().toString();
        String email_user = email.getText().toString();
        String password_user = password1.getText().toString();
        String repassword_user = password2.getText().toString();


        if (name_user.isEmpty() || name.length() < 5) {
            name.setError("at least 5 characters");
            valid = false;
        } else {
            name.setError(null);
        }

        if (email_user.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email_user).matches()) {
            email.setError("enter a valid email address");
            valid = false;
        } else {
            email.setError(null);
        }

        if (password_user.isEmpty() || password_user.length() < 8 || password_user.length() > 20) {
            password1.setError("between 8 and 20 alphanumeric characters");
            valid = false;
        } else {
            password1.setError(null);
        }


        if (!password_user.equals(repassword_user)) {
            password2.setError("Passwords do not match");
            valid = false;
        } else {
            password2.setError(null);
        }

        return valid;
    }

    void sign_up_failed()
    {
        Toast.makeText(getApplicationContext(),"Please checkout for the errors",Toast.LENGTH_LONG).show();
        return;
    }



    public class BackgroundTask extends AsyncTask<String, Void, Void> {


        private boolean flag=true;
        private ProgressBar progressBar;

        protected Void doInBackground(String... strings) {


            String name = strings[0];
            String email = strings[1];
            String password = strings[2];
            String phone = strings[3];
            String connnection = "http://192.168.43.210/test_connection/db_register.php";

            try {
                URL url = new URL(connnection);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                OutputStream out = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                String data = URLEncoder.encode("NAME", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" +
                        URLEncoder.encode("EMAIL", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                        URLEncoder.encode("PASSWORD", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") + "&" +
                        URLEncoder.encode("PHONE", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8");
                Log.d("data", data);
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                result = convertStreamToString(inputStream);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.d("TAG", "URL excp");
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("TAG", "Excep");
                Log.d("Excep", e.toString());
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            super.onPostExecute(aVoid);
            if (result != null) {
                Log.d("TAG", result);
                if(result.contains("Duplicate"))
                {
                    Snackbar snackbar = Snackbar
                            .make(scrollView, result, Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.RED);
                    snackbar.show();
                }
                else if(result.contains("Failed"))
                {

                    Snackbar snackbar = Snackbar
                            .make(scrollView,"Could not retrieve your request ", Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.RED);
                    snackbar.show();

                }
                else if(result.contains("Successful"))
                {
                    Toast.makeText(getApplicationContext(),"Account Created Succesfully :) ",Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(),Login_Activity.class));

                }


            }
            else
            {
                Log.d("Tag","null");
                sign_up.setEnabled(true);
                Snackbar snackbar = Snackbar
                        .make(scrollView, "Error in connection", Snackbar.LENGTH_LONG)
                        .setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                signUp();
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

        }
        public boolean isSuccess()
        {
            return flag;
        }

        String convertStreamToString(InputStream inputStream) throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder("");
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line + "\n");
            }
            inputStream.close();
            return sb.toString();
        }
    }
}