package com.example.aniketkumar.test;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class EditProfile extends AppCompatActivity {
    String name, email, phone, name1, email1, phone1;
    EditText ename, ephone, eemail;
    String id="1";
    LinearLayout ll;
    ImageView circle;
    Button camera;
    Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ename = findViewById(R.id.name1);
        ephone = findViewById(R.id.phone1);
        eemail = findViewById(R.id.email1);
       // camera=findViewById(R.id.camera);
        done = findViewById(R.id.done);
        ll=findViewById(R.id.ll);
        circle=findViewById(R.id.circleView);
        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        phone = getIntent().getStringExtra("mobile");
        ename.setText(name);
        ephone.setText(phone);
        eemail.setText(email);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name1 = ename.getText().toString();
                email1 = eemail.getText().toString();
                phone1 = ephone.getText().toString();

               if(name1.equals("")||email1.isEmpty()||phone1.isEmpty())
               {
                   Toast.makeText(getApplicationContext(), "FILL ALL ENTRIES", Toast.LENGTH_SHORT).show();
               }
               else{
                go();}
               // startActivity(new Intent(getApplicationContext(), My_account.class));
            }
        });

    }

    public void go() {
        SharedPreferences sharedpreferences = getApplicationContext().getSharedPreferences("Shared", MODE_PRIVATE);
        id=sharedpreferences.getString("id",null);
        BackgroundTask13 backgroundTask13 = new BackgroundTask13();
        backgroundTask13.execute(name1, email1, phone1);

    }


    public class BackgroundTask13 extends AsyncTask<String, Void, Void> {
 String name2,email2,phone2;
        private String res;
        SharedPreferences sharedpreferences = getApplicationContext().getSharedPreferences("Shared", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        String name1=sharedpreferences.getString("name",null);
        String email1=sharedpreferences.getString("email",null);
        String phone1=sharedpreferences.getString("mobile",null);


        @Override
        protected Void doInBackground(String... strings) {

             name2 = strings[0];
             email2 = strings[1];
             phone2 = strings[2];
            String connectionUrl = "http://192.168.43.210/test_connection/editprofile.php";

            try {
                URL url = new URL(connectionUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
              //  Toast.makeText(getApplicationContext(),"here",Toast.LENGTH_SHORT).show();
                Log.e("TAGG::","here");
                httpURLConnection.setDoInput(true);
                Log.e("TAGG::","here0");
                OutputStream out = httpURLConnection.getOutputStream();
                Log.e("TAGG::","here2");
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                Log.e("TAGG::","here2");
                String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name2, "UTF-8") + "&" +
                        URLEncoder.encode("new_email", "UTF-8") + "=" + URLEncoder.encode(email2, "UTF-8")
                        + "&" +
                        URLEncoder.encode("new_phone", "UTF-8") + "=" + URLEncoder.encode(phone2, "UTF-8")
                        + "&" +
                        URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8")
                        + "&" +
                        URLEncoder.encode("old_phone", "UTF-8") + "=" + URLEncoder.encode(phone1, "UTF-8")
                        + "&" +
                        URLEncoder.encode("old_email", "UTF-8") + "=" + URLEncoder.encode(email1, "UTF-8");
                Log.d("data", data);
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                res = convertStreamToString(inputStream);
                httpURLConnection.disconnect();
                Log.d("TAG", res + "");
            } catch (MalformedURLException e) {
         //       Toast.makeText(getApplicationContext(),"error1",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
         //       Toast.makeText(getApplicationContext(),"error2",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
         //       Toast.makeText(getApplicationContext(),"error3",Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
           //     Toast.makeText(getApplicationContext(),"error4",Toast.LENGTH_SHORT).show();
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
            //Toast.makeText(getApplicationContext(),res+ "   Successfully Updated",Toast.LENGTH_SHORT).show();
            if(res==null){
                Snackbar snackbar = Snackbar
                        .make(ll, "Error in connection", Snackbar.LENGTH_LONG)
                        .setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                go();
                            }
                        });
                snackbar.show();
            }
            else
            {
                if(res.contains("Duplicate"))
                {
                    Snackbar snackbar = Snackbar
                            .make(ll, res, Snackbar.LENGTH_LONG);

                    snackbar.show();
                }
                else if(res.contains("Success"))
                {
                 editor.putString("name",name2);
                 editor.putString("email",email2);
                 editor.putString("mobile",phone2);
                 editor.commit();
                    Snackbar snackbar = Snackbar
                            .make(ll, "Data Updated Successfully", Snackbar.LENGTH_LONG);

                    snackbar.show();
                }
            }
            Log.d("Tag",res);

        }
    }









}
