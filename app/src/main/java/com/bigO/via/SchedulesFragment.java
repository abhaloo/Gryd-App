package com.bigO.via;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SchedulesFragment extends Fragment {

    private ArrayList<ScheduleElement> scheduleList;

    private RecyclerView scheduleRecylerView;
    private RecyclerView.Adapter scheduleRecyclerListAdapter;
    private RecyclerView.LayoutManager recyclerViewManger;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_event_schedule, container, false);
        scheduleList = new ArrayList<>();

        scheduleList.add(new ScheduleElement("Event 3", "10 am to 6 pm","This is a random test event"));
        scheduleList.add(new ScheduleElement("Event 1", "10 am to 1pm","This is a random test event"));
        scheduleList.add(new ScheduleElement("Event 2", "1 pm to 5 pm","This is a random test event"));
        scheduleList.add(new ScheduleElement("Event 3", "10 am to 6 pm","This is a random test event"));

        createRecyclerView(view);

        return view;
    }

    public void createRecyclerView(View view){
        scheduleRecylerView = view.findViewById(R.id.recycler_schedule_view);
        recyclerViewManger = new LinearLayoutManager(getContext());
        scheduleRecyclerListAdapter = new ScheduleRcAdapter(scheduleList);

        scheduleRecylerView.setLayoutManager(recyclerViewManger);
        scheduleRecylerView.setAdapter(scheduleRecyclerListAdapter);

    }


}
