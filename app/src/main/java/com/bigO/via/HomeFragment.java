package com.bigO.via;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import java.util.Locale;

import io.mapwize.mapwizesdk.map.MapOptions;
import io.mapwize.mapwizeui.MapwizeFragment;

public class HomeFragment extends Fragment {

    private MapwizeFragment mapwizeFragment;

    public HomeFragment(MapwizeFragment mapwizeFragment) {
        this.mapwizeFragment = mapwizeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ImageButton logo_button = (ImageButton) view.findViewById(R.id.logo);
        logo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToEventHandler(v);
            }
        });

        ImageButton search_button = (ImageButton) view.findViewById(R.id.search_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchHandler(v);
            }
        });


        return view;
    }

    /**
     * Called when the user touches the search button
     */
    public void searchHandler(View view) {
        this.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mapwizeFragment).commit();
        NavigationView navigationView = this.getActivity().findViewById(R.id.navigationView);
        navigationView.setCheckedItem(R.id.search);
    }

    /**
     * Called when the user touches the search button
     */
    public void goToEventHandler(View view) {
        Intent eventMapIntent;
        eventMapIntent = new Intent(HomeFragment.this.getActivity(), EventMapActivity.class);
        this.startActivity(eventMapIntent);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


}
