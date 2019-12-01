package com.bigO.via;

import java.util.List;
import java.util.Locale;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.util.Log;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import com.mapbox.mapboxsdk.Mapbox;

import io.indoorlocation.gps.GPSIndoorLocationProvider;

import io.mapwize.mapwizesdk.api.Venue;
import io.mapwize.mapwizesdk.core.MapwizeConfiguration;


import io.mapwize.mapwizesdk.api.Floor;
import io.mapwize.mapwizesdk.api.MapwizeObject;
import io.mapwize.mapwizesdk.map.FollowUserMode;
import io.mapwize.mapwizesdk.map.MapOptions;
import io.mapwize.mapwizesdk.map.MapwizeMap;
import io.mapwize.mapwizeui.MapwizeFragment;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        MapwizeFragment.OnFragmentInteractionListener,
        MapwizeMap.OnVenueEnterListener, MapwizeMap.OnVenueExitListener, SharedPreferences.OnSharedPreferenceChangeListener{

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    private MapwizeFragment mapwizeFragment;
    private MapwizeMap mapwizeMap;
    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 0;

    private SharedPreferences sharedPreferences;
    private boolean listViewDefault;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        Mapbox.getInstance(this, "pk.mapwize");

        MapwizeConfiguration config = new MapwizeConfiguration.Builder(this, "82a148cb703ba7fc2f0e50bbb3e31902").build();
        MapwizeConfiguration.start(config);

        MapOptions opts = new MapOptions.Builder()
            .language(Locale.getDefault().getLanguage())
            .build();
        mapwizeFragment = MapwizeFragment.newInstance(opts);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_draw_open, R.string.navigation_draw_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment(mapwizeFragment)).commit();
            navigationView.setCheckedItem(R.id.home);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        listViewDefault = sharedPreferences.getBoolean("listViewDefault", false);
        Fragment selectedFragment;
        String tag = "";
        switch (item.getItemId()){
            case R.id.search:
                if (listViewDefault){
                    selectedFragment = new SearchFragment();
                }
                else{
                    selectedFragment = mapwizeFragment;
                }
                toolbar.setTitle("Search");
                break;
            case R.id.bookmarks:
                selectedFragment = new BookmarksFragment();
                toolbar.setTitle("Bookmarks");
                break;
            case R.id.schedules:
                selectedFragment = new SchedulesFragment();
                toolbar.setTitle("Schedules");
                break;
            case R.id.settings:
                selectedFragment = new SettingsFragment();
                toolbar.setTitle("Settings");
                break;
            case R.id.privacy_policy:
                selectedFragment = new PrivacyPolicyFragment();
                toolbar.setTitle("Privacy Policy");
                break;
            case R.id.help_and_feedback:
                selectedFragment = new HelpAndFeedbackFragment();
                toolbar.setTitle("Help and Feedback");
                break;
            default:
                tag = "HOME";
                selectedFragment = new HomeFragment(mapwizeFragment);
                toolbar.setTitle("Via");
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment, tag).commit();

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            Fragment homeFragment = getSupportFragmentManager().findFragmentByTag("HOME");
            if (homeFragment != null && homeFragment.isVisible()) {
                super.onBackPressed();
            }
            else {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment(mapwizeFragment), "HOME").commit();
                navigationView.setCheckedItem(R.id.home);
                toolbar.setTitle("Via");
            }
        }
    }

    @Override
    public void onMenuButtonClick(){}

    @Override
    public void onInformationButtonClick(MapwizeObject mapwizeObject){
    }

    @Override
    public void onFragmentReady(MapwizeMap mapwizeMap){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_ACCESS_FINE_LOCATION);
        }

        this.mapwizeMap = mapwizeMap;
        GPSIndoorLocationProvider gpsIndoorLocationProvider = new GPSIndoorLocationProvider(mapwizeFragment.getContext());
        gpsIndoorLocationProvider.start();
        this.mapwizeMap.setIndoorLocationProvider(gpsIndoorLocationProvider);
        this.mapwizeMap.setFollowUserMode(FollowUserMode.FOLLOW_USER);

        mapwizeMap.addOnVenueEnterListener(this);
        mapwizeMap.addOnVenueExitListener(this);
    }

    @Override
    public void onFollowUserButtonClickWithoutLocation(){
        Log.i("Debug", "onFollowUserButtonClickWithoutLocation search");
        this.mapwizeMap.setFollowUserMode(FollowUserMode.FOLLOW_USER);
    }

    @Override
    public boolean shouldDisplayInformationButton(MapwizeObject mapwizeObject) {
        return true;
    }

    @Override
    public boolean shouldDisplayFloorController(List<Floor> floors) {
        return false;
    }


    @Override
    public void onVenueEnter(@NonNull Venue venue) {

        Log.d("Debug","OnVenueEnter");
        Intent eventMapIntent = new Intent(this, EventActivity.class);
        this.startActivity(eventMapIntent);

    }

    @Override
    public void onVenueWillEnter(@NonNull Venue venue) {
        Log.d("Debug","OnVenueWillEnter");

    }

    @Override
    public void onVenueExit(@NonNull Venue venue) {

        Log.d("Debug","OnVenueExit");

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        listViewDefault = sharedPreferences.getBoolean("listViewDefault", false);
    }
}
