package com.bigO.via;

import java.util.List;
import java.util.Locale;

import android.Manifest;
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

import android.util.Log;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import com.mapbox.mapboxsdk.Mapbox;

import io.indoorlocation.gps.GPSIndoorLocationProvider;
import io.indoorlocation.core.IndoorLocation;


//import io.mapwize.mapwizeformapbox.MapwizePlugin;

import io.mapwize.mapwizesdk.core.MapwizeConfiguration;


import io.mapwize.mapwizesdk.api.Floor;
import io.mapwize.mapwizesdk.api.MapwizeObject;
import io.mapwize.mapwizesdk.map.FollowUserMode;
import io.mapwize.mapwizesdk.map.MapOptions;
import io.mapwize.mapwizesdk.map.MapwizeMap;
import io.mapwize.mapwizeui.MapwizeFragment;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MapwizeFragment.OnFragmentInteractionListener {

    private DrawerLayout drawer;

    private MapwizeFragment mapwizeFragment;
    private MapwizeMap mapwizeMap;
    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Mapbox.getInstance(this, "pk.mapwize");

        MapwizeConfiguration config = new MapwizeConfiguration.Builder(this, "82a148cb703ba7fc2f0e50bbb3e31902").build();
        MapwizeConfiguration.start(config);

        MapOptions opts = new MapOptions.Builder()
            .language(Locale.getDefault().getLanguage())
            .build();
        mapwizeFragment = MapwizeFragment.newInstance(opts);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigationView);
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
        Fragment selectedFragment;
        String tag = "";
        switch (item.getItemId()){
            case R.id.search:
                selectedFragment = mapwizeFragment;
                break;
            case R.id.bookmarks:
                selectedFragment = new BookmarksFragment();
                break;
            case R.id.schedules:
                selectedFragment = new SchedulesFragment();
                break;
            case R.id.settings:
                selectedFragment = new SettingsFragment();
                break;
            case R.id.privacy_policy:
                selectedFragment = new PrivacyPolicyFragment();
                break;
            case R.id.help_and_feedback:
                selectedFragment = new HelpAndFeedbackFragment();
                break;
            default:
                selectedFragment = new HomeFragment(mapwizeFragment);
                tag = "HOME";
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
            Fragment myFragment = getSupportFragmentManager().findFragmentByTag("HOME");
            if (myFragment != null && myFragment.isVisible()) {
                super.onBackPressed();
            }
            else {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment(mapwizeFragment), "HOME").commit();
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

        Log.i("Debug", "onFragmentReady");

        this.mapwizeMap = mapwizeMap;
        GPSIndoorLocationProvider gpsIndoorLocationProvider = new GPSIndoorLocationProvider(mapwizeFragment.getContext());
        gpsIndoorLocationProvider.start();
        this.mapwizeMap.setIndoorLocationProvider(gpsIndoorLocationProvider);

        this.mapwizeMap.setFollowUserMode(FollowUserMode.FOLLOW_USER);
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
    
}
