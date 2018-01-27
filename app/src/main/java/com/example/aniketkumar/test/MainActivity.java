package com.example.aniketkumar.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.StatementEvent;

public class MainActivity extends AppCompatActivity {
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite,textsr;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private String[] activityTitles;
    private Handler mHandler;
    private ImageButton imageButton;
    private  Button sell;
    Snackbar snackbar;
    LinearLayout linearLayoutprogress;
    CoordinatorLayout coordinatorLayout;
    ProgressBar spinner;
    int log;
    String logi;
    ImageView refresh_button;
    LinearLayout linearLayout,refresh;
    SharedPreferences sp;
    // index to identify current nav menu item
    public static int navItemIndex = 0;
    RecyclerView recyclerView;
    // tags used to attach the intent
    private static final String TAG_My_ACCOUNT = "my_account";
    private static final String TAG_DEALS = "my_deal";
    private static final String TAG_APP_TUTORIAL = "app_tutorial";
    private static final String TAG_LOGOUT = "logout";
    private static final String TAG_FEEDBACK = "feedback";
    public static String CURRENT_TAG = TAG_My_ACCOUNT;
    private static final String urlNavHeaderBg = "https://api.androidhive.info/images/glide/medium/deadpool.jpg";
    private static final String urlProfileImg = "https://static.pexels.com/photos/46710/pexels-photo-46710.jpeg";
    @Override
    protected void onPostResume() {
        super.onPostResume();
        MenuItem mn;
        mn=navigationView.getMenu().getItem(0);
        new BackgroundTask1().execute();
        mn.setChecked(true);

    }

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
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//For FullScreen
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mHandler = new Handler();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        sp = getApplicationContext().getSharedPreferences("Shared", MODE_PRIVATE);
        logi = sp.getString("name", null);
        if (logi == null) {
            navigationView.getMenu().clear(); //clear old inflated items.
            navigationView.inflateMenu(R.menu.nav_drawer_logged_out);
        }
        else
        {
            navigationView.getMenu().clear(); //clear old inflated items.
            navigationView.inflateMenu(R.menu.activity_drawer);
        }

        coordinatorLayout = findViewById(R.id.coordinate);
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);
        sell = findViewById(R.id.sell_button);
        linearLayout = findViewById(R.id.layoutitem);
        textsr = findViewById(R.id.sr);
        linearLayout.setVisibility(View.GONE);
        linearLayoutprogress = findViewById(R.id.progress_layout);
        spinner = findViewById(R.id.progressBar);
        refresh = findViewById(R.id.refresh);
        recyclerView = findViewById(R.id.recyclerview);
        refresh_button = findViewById(R.id.refresh_button);
        refresh_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
                BackgroundTask1 bc = new BackgroundTask1();
                bc.execute();
            }
        });
        refresh.setVisibility(View.GONE);


        spinner = (ProgressBar) findViewById(R.id.progressBar);
        spinner.getProgressDrawable().setColorFilter(
                Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        spinner.setVisibility(View.VISIBLE);


        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp = getApplicationContext().getSharedPreferences("Shared", MODE_PRIVATE);
                String logi = sp.getString("email", null);
                if (logi == null) {
                    Toast.makeText(getApplicationContext(), "You need to login first", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Login_Activity.class));
                    finish();
                } else {
                    startActivity(new Intent(getApplicationContext(), Selling_Activity.class));
                }
            }
        });

        BackgroundTask1 bc = new BackgroundTask1();
        bc.execute();
        // load toolbar titles from string resources


        recyclerView.addOnItemTouchListener(new CustomRVItemTouchListener(this, recyclerView, new RecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {

                sp = getApplicationContext().getSharedPreferences("Shared", MODE_PRIVATE);
                String logi = sp.getString("name", null);
                if (logi != null) {
                    Intent i = new Intent(getApplicationContext(), Product_info.class);
                    i.putExtra("sr", ((TextView) view.findViewById(R.id.sr)).getText().toString());
                    i.putExtra("id", ((TextView) view.findViewById(R.id.id)).getText().toString());
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "You need to login first", Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(), Login_Activity.class));

                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        if (logi != null) {
            activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);


            MenuItem mn;
            mn = navigationView.getMenu().getItem(0);
            mn.setChecked(true);
            ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

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
        else
        {
            MenuItem mn;
            mn = navigationView.getMenu().getItem(0);
            mn.setChecked(true);
            navigationView.setItemIconTintList(null);

            ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {


                @Override
                public void onDrawerClosed(View drawerView) {
                    // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                    super.onDrawerClosed(drawerView);
                }

                @Override
                public void onDrawerOpened(View drawerView) {
                    drawerView.bringToFront();
                    // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                    super.onDrawerOpened(drawerView);
                }
            };
            actionBarDrawerToggle.syncState();

            //Setting the actionbarToggle to drawer layout
            drawer.setDrawerListener(actionBarDrawerToggle);

            //calling sync state is necessary or else your hamburger icon wont show up
            actionBarDrawerToggle.syncState();
            // load nav menu header data
            loadNavHeader1();

            // initializing navigation menu
            setUpNavigationView1();
        }
    }

    private void loadNavHeader() {
        // name, website
        sp=getApplicationContext().getSharedPreferences("Shared",MODE_PRIVATE);
        String logi=sp.getString("name",null);
        if(logi!=null)
        txtName.setText(logi);
       // txtWebsite.setText("www.aniket.com");




//        Glide.with(this)
//                .load(urlNavHeaderBg).crossFade()
//                .into(imgNavHeaderBg);

        // loading header background image
        Glide.with(getApplicationContext()).load(R.drawable.wooden).crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);
        // Loading profile image
        Glide.with(this).load(R.drawable.man)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);
        // showing dot next to logout label
        navigationView.getMenu().getItem(4).setActionView(R.layout.menu);
    }
    private void loadNavHeader1()
    {
        Glide.with(getApplicationContext()).load(R.drawable.wooden).crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);
        // Loading profile image
        Glide.with(this).load(R.drawable.man)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);
    }



    private void setUpNavigationView() {
        MenuItem mn;
        mn=navigationView.getMenu().getItem(0);
        mn.setChecked(true);
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        navItemIndex = 0;
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_account:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_My_ACCOUNT;
                        menuItem.setChecked(false);
                        startActivity(new Intent(MainActivity.this, My_account.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_deals:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_DEALS;
                        startActivity(new Intent(MainActivity.this, My_deals.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_app_tutorial:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_APP_TUTORIAL;

                        break;
                    case R.id.nav_logout:
                        navItemIndex = 4;
                        sp=getApplicationContext().getSharedPreferences("Shared",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.clear();
                        editor.commit();
                        txtName.setText("user");
                        drawer.closeDrawers();
                        finish();
                        startActivity(new Intent(getApplicationContext(),Login_Activity.class));
//                        navigationView.getMenu().clear(); //clear old inflated items.
//                        navigationView.inflateMenu(R.menu.nav_drawer_logged_out);
                        Toast.makeText(getApplicationContext(),"LoggedOut Successfully",Toast.LENGTH_SHORT).show();
                        return true;
                        //break;
                    case R.id.nav_feedback:
                        navItemIndex = 5;
                        CURRENT_TAG = TAG_FEEDBACK;
                        startActivity(new Intent(MainActivity.this, Feedback.class));
                        drawer.closeDrawers();
                        return true;
//abc//cghsx

                    case R.id.nav_about_us:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(MainActivity.this, About_us.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_rateus:
                        String url = "https://play.google.com/store/apps/details?id=com.mnnit.athleticmeet&hl=en";
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        drawer.closeDrawers();
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
                        } catch(Exception e) {
                            //e.toString();
                        }
                        drawer.closeDrawers();
                        return  true;

                    default:
                        navItemIndex = 0;
                }
                //Checking if the item is in checked state or not, if not make it in checked state

//                if (menuItem.isChecked()) {
//                    menuItem.setChecked(false);
//                } else {
//                    menuItem.setChecked(true);
//                }
                //menuItem.setChecked(true);


                return true;
            }
        });

    }











    private void setUpNavigationView1() {
        MenuItem mn;
        mn=navigationView.getMenu().getItem(0);
        mn.setChecked(true);
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {


            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        navItemIndex = 0;
                        drawer.closeDrawers();
                        break;
                    case R.id.nav_app_tutorial:
                        navItemIndex = 3;
                        drawer.closeDrawers();
                        CURRENT_TAG = TAG_APP_TUTORIAL;
                        break;
                    case R.id.nav_logged_in:
                        startActivity(new Intent(MainActivity.this,Login_Activity.class));
                        Toast.makeText(getApplicationContext(),"Login Click",Toast.LENGTH_SHORT).show();
                        drawer.closeDrawers();
                        break;

                    case R.id.nav_feedback:
                        navItemIndex = 5;
                        CURRENT_TAG = TAG_FEEDBACK;
                        startActivity(new Intent(getApplicationContext(), Feedback.class));
                        drawer.closeDrawers();
                        //return true;
                        break;
//abc//cghsx

                    case R.id.nav_about_us:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(getApplicationContext(), About_us.class));
                        drawer.closeDrawers();
                       // return true;
                        break;
                    case R.id.nav_rateus:
                        String url = "https://play.google.com/store/apps/details?id=com.mnnit.athleticmeet&hl=en";
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        drawer.closeDrawers();
                        startActivity(i);
                       // return true;
                        break;
                    case R.id.nav_share:
                        try {
                            Intent i1 = new Intent(Intent.ACTION_SEND);
                            i1.setType("text/plain");
                            i1.putExtra(Intent.EXTRA_SUBJECT, "SELL-C");
                            String sAux = "\nDownload the Sell-C App for Selling and Buying Cycle At MNNIT Campus \n\n";
                            sAux = sAux + "https://play.google.com/store/apps/details?id=com.mnnit.athleticmeet&hl=en \n\n";
                            i1.putExtra(Intent.EXTRA_TEXT, sAux);
                            startActivity(Intent.createChooser(i1, "choose one"));
                        } catch(Exception e) {
                            //e.toString();
                        }
                        drawer.closeDrawers();
                       // return  true;
                        break;

                    default:
                        navItemIndex = 0;
                }
                //Checking if the item is in checked state or not, if not make it in checked state

//                if (menuItem.isChecked()) {
//                    menuItem.setChecked(false);
//                } else {
//                    menuItem.setChecked(true);
//                }
                //menuItem.setChecked(true);


                return true;
            }
        });

    }












    public class BackgroundTask1 extends AsyncTask<String,Void,Void> {

        String res;
        String ID;
        HashMap<String, String> contact = new HashMap<>();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            refresh.setVisibility(View.GONE);
            linearLayoutprogress.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.VISIBLE);

        }

        @Override
        protected Void doInBackground(String... params) {

          //  String id=params[0];
            //ID=id;
              String user_url="http://192.168.43.210/test_connection/getData.php";

          //  Log.d("TAGG::",id);
            try {
                URL url =new URL(user_url);
                HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
//                httpURLConnection.setRequestMethod("POST");
//                httpURLConnection.setDoOutput(true);
         //       OutputStream os=httpURLConnection.getOutputStream();
//                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
//               // String data= URLEncoder.encode("ID","UTF-8")+"="+ URLEncoder.encode(id,"UTF-8");
//                Log.d("TAGG","data="+data);
//                bufferedWriter.write(data);
//                bufferedWriter.flush();
//                bufferedWriter.close();
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
            linearLayout.setVisibility(View.VISIBLE);
            if(res!=null) {

                //tv.setText(res);
                if(snackbar!=null)
                snackbar.dismiss();
                Log.e("RESULT", res);
                try {
                    JSONObject jsonObject=new JSONObject(res);
                    Log.d("TAGG","Error 1");
                    String check;
                    check=jsonObject.getString("success");
                    if(check.equals("1")) {


                        JSONArray jsonArray = jsonObject.getJSONArray("cycles");
                        Log.d("TAGG", "Error 2");
                        List<ItemData> data = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jobject = jsonArray.getJSONObject(i);
                            String name = jobject.getString("cycle_name");
                            String des = jobject.getString("brand");
                            String sr=jobject.getString("sr");
                            String price = jobject.getString("price");
                            String im = jobject.getString("image_url");
                            String id=jobject.getString("id");
                            im="http://192.168.43.210/test_connection/"+im;
                            String rupee=getResources().getString(R.string.Rs);




                            data.add(new ItemData(im, name, des,rupee+" "+price,sr,id));

                        }


                        RecyclerviewAdapter adapter = new RecyclerviewAdapter(data, getApplication());
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"No Data Found on server",Toast.LENGTH_SHORT).show();
                    }


                    //parse


                } catch (Exception e) {

                   // showRefresh();
                    //  Toast.makeText(getApplicationContext(),"Winners not yet announced",Toast.LENGTH_SHORT).show();
                    snackbar = Snackbar.make(coordinatorLayout,"Reload the page",Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction("Close", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            snackbar.dismiss();

                        }
                    }).show();

                    e.printStackTrace();

                }
            }
            else
            {
                showRefresh();
                snackbar = Snackbar.make(coordinatorLayout,"No internet Connection or \nServer not responding ",Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("Close", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                }).setActionTextColor(Color.RED).show();
            }


        }
        private void showRefresh(){

            linearLayout.setVisibility(View.GONE);
            linearLayoutprogress.setVisibility(View.GONE);
            refresh.setVisibility(View.VISIBLE);

        }




    }

}

