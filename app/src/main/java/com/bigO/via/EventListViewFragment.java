package com.bigO.via;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class EventListViewFragment extends Fragment {

    private ArrayList<PlaceData> places;

    private RecyclerView eventRecylerView;
    private EventRcAdapter eventRecyclerListAdapter;
    private  RecyclerView.LayoutManager recyclerViewManger;

    public EventListViewFragment(ArrayList<PlaceData> places){
        this.places = places;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_event_listview, container, false);
        eventRecylerView = view.findViewById(R.id.recycler_event_list);
        eventRecylerView.setHasFixedSize(true);
        recyclerViewManger = new LinearLayoutManager(getContext());
        eventRecyclerListAdapter = new EventRcAdapter(places);

        eventRecylerView.setLayoutManager(recyclerViewManger);
        eventRecylerView.setAdapter(eventRecyclerListAdapter);

        eventRecyclerListAdapter.setOnItemClickListener(new EventRcAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                String test = places.get(position).getName() + " was click";

                Toast toast = Toast.makeText(getContext(), test, Toast.LENGTH_SHORT);
                toast.show();


            }

            @Override
            public void onAddToScheduleClick(int position) {
                String test = places.get(position).getName() + " Should be added to Schedule";

                Toast toast = Toast.makeText(getContext(), test, Toast.LENGTH_SHORT);
                toast.show();

            }
        });


        return view;
    }



}
