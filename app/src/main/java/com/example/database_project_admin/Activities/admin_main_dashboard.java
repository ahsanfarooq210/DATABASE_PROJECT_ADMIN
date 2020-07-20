package com.example.database_project_admin.Activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.location.LocationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.database_project_admin.ProfileActivities.activity_Edit_Profile;
import com.example.database_project_admin.ProfileActivities.activity_View_Profile;
import com.example.database_project_admin.ProfileActivities.notification_Activity;
import com.example.database_project_admin.ProfileActivities.setting_activity;

import com.example.database_project_admin.R;
import com.example.database_project_admin.Target.activities.add_target_activity;
import com.example.database_project_admin.Target.activities.target_details_activity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class admin_main_dashboard extends AppCompatActivity
{
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 911;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    private FloatingActionButton fab;

    // urls to load navigation header background image
    // and profile image
    private static final String urlNavHeaderBg = "https://api.androidhive.info/images/nav-menu-header-bg.jpg";
    private static final String urlProfileImg = "https://lh3.googleusercontent.com/eCtE_G34M9ygdkmOpYvCag1vBARCmZwnVS6rS5t4JLzJ6QgQSBquM0nuTsCpLhYbKljoyS-txg";

    // index to identify current nav menu item
    public static int navItemIndex = 0;


    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;


    private ScrollView scrollView;
    private Handler handler=new Handler();
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            scrollView.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_dashboard);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView  = (NavigationView) findViewById(R.id.nav_view);

        // view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);

        // load nav menu header data


        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = -1;
            load();
        }

        scrollView=findViewById(R.id.dashboard_scroll_view);
        handler.postDelayed(runnable,1000);
        loadNavHeader();
    }
    private void loadNavHeader() {
        // name, website
        //assert user != null;
        txtName.setText("Admin");
        txtWebsite.setText("ADMIN@admin.com");//user.getEmail().trim());
        // loading header background image
        Glide.with(this).load(urlNavHeaderBg)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);

        // Loading profile image
       /* Glide.with(this).load(urlProfileImg)

                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);
*/
        // showing dot next to notifications label
        navigationView.getMenu().getItem(2).setActionView(R.layout.menu_dot);
    }
    private void load() {
        // selecting appropriate nav menu item
        // selectNavMenu();
        getActivity();
        //Closing drawer on item click
        drawer.closeDrawers();
        // refresh toolbar menu
        invalidateOptionsMenu();
    }
    private void getActivity() {
        switch (navItemIndex) {

            case 0:
                // activity_View_Profile
                Intent view_profile=new Intent(admin_main_dashboard.this, activity_View_Profile.class);
                startActivity(view_profile);
                break;
            case 1:
                // activity_Edit_Profile
                Intent edit_profile=new Intent(admin_main_dashboard.this, activity_Edit_Profile.class);
                startActivity(edit_profile);
                break;
            case 2:
                // notifications
                Intent notification=new Intent(admin_main_dashboard.this,notification_Activity.class);
               // Toast.makeText(admin_main_dashboard.this,"notification",Toast.LENGTH_LONG).show();
                startActivity(notification);
                break;
            case 3:
                // settings
                 Intent setting =new Intent(admin_main_dashboard.this, setting_activity.class);
                startActivity(setting);


                break;
            case 4:
                //  fragment
                //  startActivity(new Intent(salesname_main_dashboard.this,edit_order_rv_activity.class));
                break;

            default:
                break;

        }
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    // This method will trigger on item Click of navigation menu
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        //Check to see which item was being clicked and perform appropriate action
                        switch (menuItem.getItemId()) {
                            //Replacing the main content with ContentFragment Which is our Inbox View;
                            case R.id.Nav_View_profile:
                                navItemIndex = 0;
                                break;
                            case R.id.nav_Edit_Profile:
                                navItemIndex = 1;
                                break;
                            case R.id.nav_notifications:
                                navItemIndex = 2;
                                break;
                            case R.id.nav_settings:
                                navItemIndex =3;
                                break;
                            default:
                                navItemIndex = 0;
                        }

                        //Checking if the item is in checked state or not, if not make it in checked state
                        if (menuItem.isChecked()) {
                            menuItem.setChecked(false);
                        } else {
                            menuItem.setChecked(true);
                        }
                        menuItem.setChecked(true);

                        load();

                        return true;
                    }
                });


        ActionBarDrawerToggle actionBarDrawerToggle =
                new ActionBarDrawerToggle(admin_main_dashboard.this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

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
    } @Override
public void onBackPressed() {
    if (drawer.isDrawerOpen(GravityCompat.START)) {
        drawer.closeDrawers();
        return;
    }

    // This code loads home fragment when back key is pressed
    // when user is in other fragment than home
    if (shouldLoadHomeFragOnBackPress) {
        // checking if user is on other navigation menu
        // rather than home
        if (navItemIndex != 0) {
            navItemIndex = -1;
            load();
            return;
        }
    }

    //super.onBackPressed();
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // show menu only when home fragment is selected
        if (navItemIndex == -1||navItemIndex == 0||navItemIndex == 1||navItemIndex ==3 ) {
            getMenuInflater().inflate(R.menu.main, menu);
        }
        // when fragment is notifications, load the menu created for notifications
        if (navItemIndex == 2) {
            getMenuInflater().inflate(R.menu.notifications, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent=new Intent(admin_main_dashboard.this, MainActivity.class);
            startActivity(intent);
            return true;
        }

        // user is in notifications fragment
        // and selected 'Mark all as Read'
        if (id == R.id.action_mark_all_read) {
            Toast.makeText(getApplicationContext(), "All notifications marked as read!", Toast.LENGTH_LONG).show();
            navigationView.getMenu().getItem(2).setActionView(R.layout.menu_layout_notication);

        }

        // user is in notifications fragment
        // and selected 'Clear All'
        if (id == R.id.action_clear_notifications) {
            Toast.makeText(getApplicationContext(), "Clear all notifications!", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }


    public void addSalesman(View view)
    {
        startActivity(new Intent(admin_main_dashboard.this,add_salesman_activity.class));
    }

    public void showOrder(View view)
    {

        startActivity(new Intent(admin_main_dashboard.this,show_order_activity.class));
    }
    private boolean isLocationEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return LocationManagerCompat.isLocationEnabled(locationManager);
    }
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);

        return result == PackageManager.PERMISSION_GRANTED ;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, },MY_PERMISSIONS_REQUEST_LOCATION);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION:
                if (grantResults.length > 0) {
                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (locationAccepted ) {
                        startActivity(new Intent(admin_main_dashboard.this,show_shops_on_map_activity.class));
                        //Snackbar.make(getCurrentFocus(), "Permission Granted, Now you can access location data and camera.", Snackbar.LENGTH_LONG).show();
                    }else {

                        // Snackbar.make(getCurrentFocus(), "Permission Denied, You cannot access location data and camera.", Snackbar.LENGTH_LONG).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel("You need to allow the permissions access to Location",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(admin_main_dashboard.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    public void showShopsOnMap(View view)
    { if(isLocationEnabled(this)) {

        if (checkPermission()) {
            startActivity(new Intent(admin_main_dashboard.this,show_shops_on_map_activity.class));
        } else {

            requestPermission();
        }
    }else{
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }

    }
    public void addTargets(View view)
    {
        startActivity(new Intent(admin_main_dashboard.this, add_target_activity.class));
    }
    public void targetDetails(View view)
{
    Toast.makeText(admin_main_dashboard.this,"Coming soon",Toast.LENGTH_LONG).show();
   // startActivity(new Intent(admin_main_dashboard.this, target_details_activity.class));
}public void editOrder(View view)
{
   // startActivity(new Intent(admin_main_dashboard.this,target_details_activity.class));
}

    public void showSalesman(View view)
    {
        startActivity(new Intent(admin_main_dashboard.this,Show_salesman_activity.class));
    }
}
