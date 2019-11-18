package com.bigO.via;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ImageButton search_button = (ImageButton) view.findViewById(R.id.search_button);

        search_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) { searchHandler(v); }
        });

        return view;
    }

    /** Called when the user touches the search button */
    public void searchHandler(View view) {
        // Do something in response to search click
        Intent eventMapIntend;
        eventMapIntend = new Intent(HomeFragment.this.getActivity(), EventMapActivity.class);
        this.startActivity(eventMapIntend)
         ;
    }




}
