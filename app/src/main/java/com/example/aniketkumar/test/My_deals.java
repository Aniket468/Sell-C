package com.example.aniketkumar.test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
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
//import com.bumptech.glide.request.RequestOptions;

public class My_deals extends AppCompatActivity {
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    LinearLayout linearLayoutprogress,container,refresh;
    ProgressBar spinner;
    ImageView refresh_button;
    RecyclerView recyclerView;
    private String[] activityTitles;
    LinearLayout li;
    private Handler mHandler;
    SharedPreferences sp;
    private static final String TAG_My_ACCOUNT = "my_account";
    private static final String TAG_DEALS = "my_deal";
    private static final String TAG_APP_TUTORIAL = "app_tutorial";
    private static final String TAG_LOGOUT = "logout";
    Snackbar snackbar;
    private static final String TAG_FEEDBACK = "feedback";
    public static String CURRENT_TAG = TAG_My_ACCOUNT;
    private static final String urlNavHeaderBg = "R.drawable.tutorial";
    private static final String urlProfileImg = "R.drawable.account";
    public static int navItemIndex = 0;

    @Override
    protected void onPostResume() {
        super.onPostResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//For FullScreen
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_deals);
        refresh_button=findViewById(R.id.refresh_button);
        refresh=findViewById(R.id.refresh);
        container=findViewById(R.id.container);
        spinner=findViewById(R.id.progressBar);
        linearLayoutprogress=findViewById(R.id.progress_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        li=findViewById(R.id.li);
        recyclerView=findViewById(R.id.recyclerview_card);
        refresh.setVisibility(View.GONE);
        container.setVisibility(View.GONE);
        linearLayoutprogress.setVisibility(View.VISIBLE);
        spinner.setVisibility(View.VISIBLE);
  new BackgroundTask1().execute();



    }



    private void setUpNavigationView() {
        MenuItem mn;
        mn=navigationView.getMenu().getItem(2);
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
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                        return true;
                    case R.id.nav_account:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_My_ACCOUNT;
                        startActivity(new Intent(getApplicationContext(), My_account.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_deals:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_DEALS;
                        drawer.closeDrawers();
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
                        } catch(Exception e) {
                            //e.toString();
                        }
                        drawer.closeDrawers();
                        return  true;

                    default:
                        navItemIndex = 0;
                }
                //Checking if the item is in checked state or not, if not make it in checked state


                //menuItem.setChecked(true);


                return true;
            }
        });

    }


    public  class BackgroundTask1 extends AsyncTask<String,Void,Void> {

        String res;
        String ID;
        SharedPreferences sharedpreferences = getApplicationContext().getSharedPreferences("Shared", MODE_PRIVATE);
        String id=sharedpreferences.getString("id",null);
      //  Log.e("Tagg",""+id);

        HashMap<String, String> contact = new HashMap<>();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            refresh.setVisibility(View.GONE);
            container.setVisibility(View.GONE);
            linearLayoutprogress.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.VISIBLE);

        }

        @Override
        protected Void doInBackground(String... params) {

            //  String id=params[0];
            //ID=id;
            String user_url="http://192.168.43.210/test_connection/myDeals.php";

            //  Log.d("TAGG::",id);
            try {
                URL url =new URL(user_url);
                HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod("POST");
                Log.d("TAG","user");
                httpURLConnection.setDoOutput(true);
                Log.d("TAG","user");
                OutputStream os=httpURLConnection.getOutputStream();
                Log.d("TAG","user");
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                Log.d("TAG","user");
                String data= URLEncoder.encode("id","UTF-8")+"="+ URLEncoder.encode(id,"UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();

                InputStream is=new BufferedInputStream(httpURLConnection.getInputStream());
                res=convertStreamToString(is);
                Log.d("results","res="+res);

            } catch (MalformedURLException e) {
                Log.d("TAGG::","error11="+ e.toString());
            } catch (IOException e) {
                Log.d("TAGG::","error22="+ e.toString());
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
            Log.d("TAGGG",res+"a");
            if(res!=null) {

                container.setVisibility(View.VISIBLE);
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
                        List<Item_Data_Card> data = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jobject = jsonArray.getJSONObject(i);
                            String name = jobject.getString("cycle_name");
                            String des = jobject.getString("brand");
                            String price = jobject.getString("price");
                            String im = jobject.getString("image_url");
                            String id=jobject.getString("sr");
                            im="http://192.168.43.210/test_connection/"+im;
                            String rupee=getResources().getString(R.string.Rs);




                            data.add(new Item_Data_Card(im, name, des,rupee+" "+price,id));

                        }


                        RecyclerViewAdapter_card adapter = new RecyclerViewAdapter_card(data, getApplication());
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
                    snackbar = Snackbar.make(li,"Reload the page",Snackbar.LENGTH_INDEFINITE);
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
                snackbar = Snackbar.make(li,"No internet Connection or \nServer not responding ",Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("Close", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                }).setActionTextColor(Color.RED).show();
            }


        }
        private void showRefresh(){
            container.setVisibility(View.GONE);
            linearLayoutprogress.setVisibility(View.GONE);
            refresh.setVisibility(View.VISIBLE);

        }




    }


}