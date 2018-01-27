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
        MenuItem mn;
        mn=navigationView.getMenu().getItem(1);
        mn.setChecked(true);

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
            }
        });
        SharedPreferences sharedpreferences = getApplicationContext().getSharedPreferences("Shared", MODE_PRIVATE);
        name1=sharedpreferences.getString("name",null);
        email1=sharedpreferences.getString("email",null);
        phone1=sharedpreferences.getString("mobile",null);



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
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
       // txtWebsite = (TextView) navHeader.findViewById(R.id.website);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);
        MenuItem mn;
        mn=navigationView.getMenu().getItem(1);
        mn.setChecked(true);

        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
        // load nav menu header data
        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();
    }

    private void loadNavHeader() {
        // name, website
        txtName.setText("Aniket");
       // txtWebsite.setText("www.aniket.com");

        // loading header background image
        Glide.with(this).load(urlNavHeaderBg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);
        // Loading profile image
        Glide.with(this).load(urlProfileImg)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);
        // showing dot next to logout label
        navigationView.getMenu().getItem(4).setActionView(R.layout.menu);
        MenuItem mn;
        mn=navigationView.getMenu().getItem(1);
        mn.setChecked(true);

    }


    private void setUpNavigationView() {
        MenuItem mn;
        mn = navigationView.getMenu().getItem(1);
        mn.setChecked(true);

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        // navItemIndex = 0;
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                        return true;
                    case R.id.nav_account:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_My_ACCOUNT;
                        drawer.closeDrawers();
                        menuItem.setChecked(true);
                        break;
                    case R.id.nav_deals:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_DEALS;
                        startActivity(new Intent(getApplicationContext(), My_deals.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_app_tutorial:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_APP_TUTORIAL;
                        break;
                    case R.id.nav_logout:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_LOGOUT;
                        break;
                    case R.id.nav_feedback:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_FEEDBACK;
                        startActivity(new Intent(getApplicationContext(), Feedback.class));
                        drawer.closeDrawers();
                        return true;

                    case R.id.nav_about_us:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(getApplicationContext(), About_us.class));
                        drawer.closeDrawers();
                        return true;

                    case R.id.nav_rateus:
                        String url = "https://play.google.com/store/apps/details?id=com.mnnit.athleticmeet&hl=en";
                        drawer.closeDrawers();
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);

                        return true;
                    case R.id.nav_share:
                        try {
                            Intent i1 = new Intent(Intent.ACTION_SEND);
                            i1.setType("text/plain");
                            i1.putExtra(Intent.EXTRA_SUBJECT, "SELL-C");
                            String sAux = "\nDownload the Sell-C App for Selling and Buying Cycle At MNNIT Campus \n\n";
                            sAux = sAux + "https://play.google.com/store/apps/details?id=com.mnnit.athleticmeet&hl=en \n\n";
                            i1.putExtra(Intent.EXTRA_TEXT, sAux);
                            startActivity(Intent.createChooser(i1, "choose one"));
                        } catch (Exception e) {
                            //e.toString();
                        }
                        drawer.closeDrawers();
                        return true;


                    default:
                        navItemIndex = 0;
                }
                //Checking if the item is in checked state or not, if not make it in checked state


                //menuItem.setChecked(true);


                return true;
            }
        });
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
