package com.example.database_project_admin.Activities;



import android.Manifest;
import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.database_project_admin.ActioBarAdapter.TitleNavigationAdapter;
import com.example.database_project_admin.Entity.ShopDetails;
import com.example.database_project_admin.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class show_shops_on_map_activity extends AppCompatActivity implements ActionBar.OnNavigationListener ,
        OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 911;
    private GoogleMap mMap;
    String s;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    // action bar
    private ActionBar actionBar;

    // Title navigation Spinner data
    //private ArrayList<SpinnerNavItem> navSpinner;

    // Navigation adapter
    private TitleNavigationAdapter adapter;

    SearchManager searchManager;
    SearchView searchView;

    // Refresh menu item
    private MenuItem refreshMenuItem;

    private Geocoder geo;
    private List<Address> address;
    private DatabaseReference shopReference;
    private ArrayList<ShopDetails> shopArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_shops_on_map_activity);
        actionBar = getActionBar();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        shopReference = FirebaseDatabase.getInstance().getReference("SHOP");
        shopReference.keepSynced(true);
        shopArrayList = new ArrayList<>();

    }
        //action bar this ahead
        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.activity_main_actions, menu);


            // Associate searchable configuration with the SearchView
            searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            searchView = (SearchView) menu.findItem(R.id.action_search)
                    .getActionView();
            assert searchManager != null;
            searchView.setSearchableInfo(searchManager
                    .getSearchableInfo(getComponentName()));
            SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
                public boolean onQueryTextChange(String newText) {
                    // this is your adapter that will be filtered
                    return true;
                }

                public boolean onQueryTextSubmit(String query) {
                    //Here u can get the value "query" which is entered in the search box.
                    searchView.setIconified(true);
                    searchView.onActionViewCollapsed();
                    onMapSearch(query);

                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
            return super.onCreateOptionsMenu(menu);

        }
        /**
         * On selecting action bar icons
         **/
        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            // Take appropriate action for each action item click
            switch (item.getItemId()) {
                case R.id.action_search:
                    // search action
                    return true;
           /* case R.id.action_location_found:
                // location found

                return true;
            case R.id.action_refresh:
                // refresh
                refreshMenuItem = item;
                // load the data from server
                new SyncData().execute();

                return true;
            case R.id.action_help:
                // help action
                return true;
            case R.id.action_check_updates:
                // check for updates action
                return true;*/
                default:
                    return super.onOptionsItemSelected(item);
            }
        }

        /**
         * Actionbar navigation item select listener
         * */
        @Override
        public boolean onNavigationItemSelected ( int itemPosition, long itemId){
            // Action to be taken after selecting a spinner item
            return false;
        }


        /**
         * Async task to load the data from server
         * **/
        private class SyncData extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                // set the progress bar view
                refreshMenuItem.setActionView(R.layout.action_progressbar);
                refreshMenuItem.expandActionView();
            }

            @Override
            protected String doInBackground(String... params) {
                // not making real request in this demo
                // for now we use a timer to wait for sometime
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                refreshMenuItem.collapseActionView();
                // remove the progress bar view
                refreshMenuItem.setActionView(null);
            }
        }
        ;
        @Override
        public void onMapReady (GoogleMap googleMap){
            mMap = googleMap;
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.getUiSettings().setZoomGesturesEnabled(true);

            geo = new Geocoder(show_shops_on_map_activity.this, Locale.getDefault());


            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    buildGoogleApiClient();
                    mMap.setMyLocationEnabled(true);
                }
            } else {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }

        }
        protected synchronized void buildGoogleApiClient () {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API).build();
            mGoogleApiClient.connect();
        }

     @Override
            public void onConnected (Bundle bundle){

                mLocationRequest = new LocationRequest();
                mLocationRequest.setInterval(1000);
                mLocationRequest.setFastestInterval(1000);
                mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                }

            }

        @Override
        public void onConnectionSuspended ( int i){

        }

        @Override
        public void onLocationChanged (Location location){

            mLastLocation = location;
            if (mCurrLocationMarker != null) {
                mCurrLocationMarker.remove();
            }
            //Place current location marker
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Current Position");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mCurrLocationMarker = mMap.addMarker(markerOptions);
//zoom to current location
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();

            // Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
            if (location != null) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                        .zoom(17)                   // Sets the zoom
                        .bearing(90)                // Sets the orientation of the camera to east
                        .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
            //stop location updates
            if (mGoogleApiClient != null) {
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            }

        }
        @Override
        public boolean dispatchTouchEvent (MotionEvent ev){
            if (getCurrentFocus() != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                assert imm != null;
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                searchView.setIconified(true);
                // searchView.onActionViewCollapsed();
            }
            return super.dispatchTouchEvent(ev);
        }
        @Override
        public void onConnectionFailed (ConnectionResult connectionResult){

        }
        public void onMapSearch (String location){

            List<Address> addressList = null;
        /*if(location.isEmpty())
        {
            locationSearch.setError(getResources().getString(R.string.error));
        }*/
            if (!location.isEmpty()) {
                Geocoder geocoder = new Geocoder(this);
                try {
                    addressList = geocoder.getFromLocationName(location, 1);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                Address address = addressList.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(location);
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                // mMap.addMarker(new MarkerOptions().position(latLng).title(location));
                mMap.addMarker(markerOptions);
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        }
    //ahsan
    @Override
    public void onStart()
    {
        super.onStart();
        shopReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                shopArrayList.clear();
                for (DataSnapshot shop : dataSnapshot.getChildren())
                {
                    shopArrayList.add(shop.getValue(ShopDetails.class));
                }
                for(int i=0;i<shopArrayList.size();i++)
                {
                    LatLng coordinates=new LatLng(shopArrayList.get(i).getLatitude(),shopArrayList.get(i).getLongitude());
                    try
                    {
                        address= geo.getFromLocation(coordinates.latitude, coordinates.longitude, 1);
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    mMap.addMarker(new MarkerOptions().position(coordinates).title(address.toString()));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(coordinates));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(show_shops_on_map_activity.this, "error in downloading the data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}