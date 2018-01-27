package com.example.aniketkumar.test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.bumptech.glide.request.RequestOptions;

public class My_deals extends AppCompatActivity {
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private String[] activityTitles;
    private Handler mHandler;
    SharedPreferences sp;
    private static final String TAG_My_ACCOUNT = "my_account";
    private static final String TAG_DEALS = "my_deal";
    private static final String TAG_APP_TUTORIAL = "app_tutorial";
    private static final String TAG_LOGOUT = "logout";
    private static final String TAG_FEEDBACK = "feedback";
    public static String CURRENT_TAG = TAG_My_ACCOUNT;
    private static final String urlNavHeaderBg = "R.drawable.tutorial";
    private static final String urlProfileImg = "R.drawable.account";
    public static int navItemIndex = 0;

    @Override
    protected void onPostResume() {
        super.onPostResume();
//        MenuItem mn;
//        mn=navigationView.getMenu().getItem(2);
//        mn.setChecked(true);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//For FullScreen
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_deals);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mHandler = new Handler();
    }
//        navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navHeader = navigationView.getHeaderView(0);
//        txtName = (TextView) navHeader.findViewById(R.id.name);
//        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
//        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);
//        MenuItem mn;
//        mn=navigationView.getMenu().getItem(2);
//        mn.setChecked(true);

//        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {
//
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
//                super.onDrawerClosed(drawerView);
//            }
//
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
//                super.onDrawerOpened(drawerView);
//            }
//        };
//
//        //Setting the actionbarToggle to drawer layout
//        drawer.setDrawerListener(actionBarDrawerToggle);
//
//        //calling sync state is necessary or else your hamburger icon wont show up
//        actionBarDrawerToggle.syncState();
//        // load nav menu header data
//        loadNavHeader();
//
//        // initializing navigation menu
//        setUpNavigationView();
//    }
//
//    private void loadNavHeader() {
//        // name, website
//        sp=getApplicationContext().getSharedPreferences("Shared",MODE_PRIVATE);
//        String logi=sp.getString("name",null);
//        if(logi!=null)
//            txtName.setText(logi);
//
//        // loading header background image
////        Glide.with(this).load(urlNavHeaderBg)
////                .crossFade()
////                .diskCacheStrategy(DiskCacheStrategy.ALL)
////                .into(imgNavHeaderBg);
//
////
////        Glide
////                .with(this)
////                .load(R.drawable.image_default_profile_picture)
////                .into(mUserImage);
////        // Loading profile image
////        Glide.with(this).load(urlProfileImg)
////                .crossFade()
////                .thumbnail(0.5f)
////                .bitmapTransform(new CircleTransform(this))
////                .diskCacheStrategy(DiskCacheStrategy.ALL)
////                .into(imgProfile);
//        // showing dot next to notifications label
//        navigationView.getMenu().getItem(4).setActionView(R.layout.menu);
//        MenuItem mn;
//        mn=navigationView.getMenu().getItem(1);
//        mn.setChecked(true);
//
//    }


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
}