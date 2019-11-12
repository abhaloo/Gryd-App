package com.bigO.via;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;

import io.mapwize.mapwizesdk.core.MapwizeConfiguration;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MapwizeConfiguration config = new MapwizeConfiguration.Builder(this, "82a148cb703ba7fc2f0e50bbb3e31902").build();

        MapwizeConfiguration.start(config);

        setContentView(R.layout.activity_main);
    }
}
