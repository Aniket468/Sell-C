package com.example.aniketkumar.test;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONObject;

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


public class Product_info extends AppCompatActivity {
 String id,sr;
 ProgressBar spinner;
 LinearLayout progress;
    String phone,email;
    LinearLayout container;
    TextView tvcname,tvbrand,tvprice,tvhostel,tvfeature,tvyear;
 ImageView tvim1,tvim2,tvim3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);
        spinner=findViewById(R.id.progressBar12);
        progress=findViewById(R.id.progress12);
        container=findViewById(R.id.container);
        container.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
        tvcname=findViewById(R.id.name);
        spinner.getProgressDrawable().setColorFilter(
                Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        spinner.setVisibility(View.VISIBLE);

        tvbrand=findViewById(R.id.brand);
        tvprice=findViewById(R.id.price);
        tvhostel=findViewById(R.id.hostel);
        tvim1=findViewById(R.id.image1);
        tvim2=findViewById(R.id.image2);
        tvim3=findViewById(R.id.image3);
        tvfeature=findViewById(R.id.features);
        tvyear=findViewById(R.id.year);



        Intent i=getIntent();
        id=i.getStringExtra("id");
        sr=i.getStringExtra("sr");
        new BackgroundTask1().execute();
    }






    public class BackgroundTask1 extends AsyncTask<String,Void,Void> {

        String res;
        String ID;
        String  user_url="http://192.168.43.210/test_connection/getProductDetails.php";
    //    HashMap<String, String> contact = new HashMap<>();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            spinner.setVisibility(View.VISIBLE);

        }

        @Override
        protected Void doInBackground(String... params) {



          //  Log.d("TAGG::",id);
            try {
                URL url =new URL(user_url);
                HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String data= URLEncoder.encode("sr","UTF-8")+"="+ URLEncoder.encode(sr,"UTF-8");
                Log.d("TAGG","data="+data);
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream is=new BufferedInputStream(httpURLConnection.getInputStream());
                res=convertStreamToString(is);
                Log.d("results","res="+res);

            } catch (MalformedURLException e) {
                Log.d("TAGG::","error1="+ e.toString());
            } catch (IOException e) {
                Log.d("TAGG::","error2="+ e.toString());
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
            spinner.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(),res+"",Toast.LENGTH_LONG).show();
            if(res!=null) {

                //tv.setText(res);
                Log.e("RESULT", res);
                try {

                    JSONObject jsonObject=new JSONObject(res);
                    Log.d("TAGG","Error 1");

                    Log.d("TAGG","Error 2");
                    String cname=jsonObject.getString("cycle_name");
                    String brand=jsonObject.getString("brand");
                    String price=jsonObject.getString("price");
                    String hostel=jsonObject.getString("hostel");
                    String year=jsonObject.getString("used_year");
                    String im1=jsonObject.getString("image1");
                    String im2=jsonObject.getString("image2");
                    String im3=jsonObject.getString("image3");
                    phone=jsonObject.getString("phone");
                    email=jsonObject.getString("email");
                    String features=jsonObject.getString("features");
                    container.setVisibility(View.VISIBLE);
                    tvcname.setText(cname);
                    tvbrand.setText(brand);
                    tvprice.setText(price);
                    tvhostel.setText(hostel);
                    tvyear.setText(year);
                    tvfeature.setText(features);

                    im1="http://192.168.43.210/test_connection/"+im1;
                    im2="http://192.168.43.210/test_connection/"+im2;
                    im3="http://192.168.43.210/test_connection/"+im3;

                    Glide.with(getApplicationContext()).load(im1).crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(tvim1);
                    Glide.with(getApplicationContext()).load(im2).crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(tvim2);
                    Glide.with(getApplicationContext()).load(im3).crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(tvim3);



                }
                catch (Exception e) {

                   Toast.makeText(getApplicationContext(),"Parsing Error ", Toast.LENGTH_LONG).show();

                }
            }
            else
            {
                Toast.makeText(getApplicationContext(),"No internet Connection !!",Toast.LENGTH_SHORT).show();

            }


        }


    }










}
