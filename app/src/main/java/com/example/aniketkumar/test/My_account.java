package com.example.aniketkumar.test;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class My_account extends AppCompatActivity {
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private String[] activityTitles;
    private Handler mHandler;
    int check1=0;
    SharedPreferences sp;
    Button upload;
    int check2=0;
    private static final String TAG_My_ACCOUNT = "my_account";
    private static final String TAG_DEALS = "my_deal";
    private static final String TAG_APP_TUTORIAL = "app_tutorial";
    private static final String TAG_LOGOUT = "logout";
    private static final String TAG_FEEDBACK = "feedback";
    public static String CURRENT_TAG = TAG_My_ACCOUNT;
    private static final String urlNavHeaderBg = "R.drawable.tutorial";
    private static final String urlProfileImg = "R.drawable.account";
    public static int navItemIndex = 0;
    TextView name2,email2,phone2;
    ImageView circle;
    String name1,phone1,email1;
    Bitmap b;
    LinearLayout container,linearLayout;


    Button edit,camera;

    @Override
    protected void onPostResume() {
        super.onPostResume();
//        MenuItem mn;
//        mn=navigationView.getMenu().getItem(1);
//        mn.setChecked(true);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//For FullScreen
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.back);
        Bitmap circularBitmap = ImageConverter.getRoundedCornerBitmap(bitmap, 100);

        upload=findViewById(R.id.upload);
        container=findViewById(R.id.container);
        linearLayout=findViewById(R.id.linearLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        name2=findViewById(R.id.name);
        email2=findViewById(R.id.email);
        camera=findViewById(R.id.camera);
        phone2=findViewById(R.id.phone);
        circle=findViewById(R.id.circleView);
        edit=findViewById(R.id.edit);



        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(check1>0||check2>0){
                    BackgroundTask14 backgroundTask14=new BackgroundTask14();
                    backgroundTask14.execute();

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Plz Select the images",Toast.LENGTH_SHORT).show();
                }
            }
        });
        SharedPreferences sharedpreferences = getApplicationContext().getSharedPreferences("Shared", MODE_PRIVATE);
        name1=sharedpreferences.getString("name",null);
        email1=sharedpreferences.getString("email",null);
        phone1=sharedpreferences.getString("mobile",null);
        String p=sharedpreferences.getString("url",null);
        Log.d("TAG",p+"");
        Toast.makeText(getApplicationContext(),""+p,Toast.LENGTH_SHORT).show();
        if(p!=null)
        {
            Glide.with(getApplicationContext()).load(p)
                    .crossFade()
                    .thumbnail(0.5f)
                    .bitmapTransform(new CircleTransform(getApplicationContext()))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(circle);
        }



        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder builder = new AlertDialog.Builder(My_account.this,R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                builder.setTitle("Select upload option..");
                builder.setMessage("Choose one......")
                        .setCancelable(true)
                        .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent, 0);
                            }
                        })
                        .setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                                Intent intent = new Intent();

                                intent.setType("image/*");

                                intent.setAction(Intent.ACTION_GET_CONTENT);

                                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);



                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();


            }
        });


        name2.setText(name1);
        email2.setText(email1);
        phone2.setText(phone1);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAGG",""+phone1);
                Intent i=new Intent(getApplicationContext(),EditProfile.class);
                i.putExtra("name",name1);
                i.putExtra("email",email1);
                i.putExtra("mobile",phone1);
                startActivity(i);
            }
        });
        setSupportActionBar(toolbar);
        mHandler = new Handler();


       // txtWebsite = (TextView) navHeader.findViewById(R.id.website);
//        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
//        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);
//        MenuItem mn;
//        mn=navigationView.getMenu().getItem(1);
//        mn.setChecked(true);

    }








    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("TAG1",""+resultCode);
        if(data!=null&&resultCode==RESULT_OK&&requestCode==0) {
            super.onActivityResult(requestCode, resultCode, data);
            b = (Bitmap) data.getExtras().get("data");
            circle.setImageBitmap(b);
            check1++;

        }
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {


                 b = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                circle.setImageBitmap(b);
                check2++;

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    private class BackgroundTask14 extends AsyncTask<Void, Void, Void> {
        String res;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            container.setVisibility(View.GONE);
//            progress.setVisibility(View.VISIBLE);
//            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(Void... voids) {
  SharedPreferences sp;
            sp=getApplicationContext().getSharedPreferences("Shared",MODE_PRIVATE);
          String id=sp.getString("id",null);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            ByteArrayOutputStream stream1 = new ByteArrayOutputStream();

            ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.JPEG, 25, stream);
            //bit1.recycle();
            // bit1=null;




            byte[] array = stream.toByteArray();
            String encoded_string = Base64.encodeToString(array, 0);
            Log.d("TAG",encoded_string);



            String user_url="http://192.168.43.210/test_connection/uploadProfile.php";

            try {
                URL url =new URL(user_url);
                Log.d("TAG","user");
                HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                Log.d("TAG","user");
                httpURLConnection.setDoOutput(true);
                Log.d("TAG","user");
                OutputStream os=httpURLConnection.getOutputStream();
                Log.d("TAG","user");
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                Log.d("TAG","user");


                String data= URLEncoder.encode("encoded_string","UTF-8")+"="+ URLEncoder.encode(encoded_string,"UTF-8")+"&"+

                        URLEncoder.encode("id","UTF-8")+"="+ URLEncoder.encode(id,"UTF-8");




                Log.d("TAG",id);
                Log.d("TAGG","data="+data);

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream is=new BufferedInputStream(httpURLConnection.getInputStream());
                res=convertStreamToString(is);
                Log.d("results","res="+res);

            } catch (MalformedURLException e) {
                Log.d("TAGG::","error1="+ e.toString());
            } catch (Exception e) {
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
        protected void onPostExecute(Void aVoid) {

//            container.setVisibility(View.VISIBLE);
//            progress.setVisibility(View.GONE);
//            progressBar.setVisibility(View.GONE);
//            features="";
            Log.d("TAG",""+res);
            if(res!=null)
            {
                if(res.contains("Success")) {
                    Toast.makeText(getApplicationContext(), "Data Updated Successfully",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Data Failed to upload",Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Snackbar.make(linearLayout,"No internet Connection or \nServer not responding ",Snackbar.LENGTH_LONG).setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new BackgroundTask14().execute();
                    }
                }).setActionTextColor(Color.RED).show();
            }
        }
    }



}
