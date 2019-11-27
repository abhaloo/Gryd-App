package com.bigO.via;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class SchedulesFragment extends Fragment {

    private ArrayList<ScheduleElement> scheduleList;

    private RecyclerView scheduleRecylerView;
    private ScheduleRcAdapter scheduleRecyclerListAdapter;
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

        scheduleRecyclerListAdapter.setOnItemClickListener(new ScheduleRcAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String test = scheduleList.get(position).getName() + " was click";

                Toast toast = Toast.makeText(getContext(), test, Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onRemoveClick(int position) {
                String test = scheduleList.get(position).getName() + " was removed";

                Toast toast = Toast.makeText(getContext(), test, Toast.LENGTH_SHORT);
                toast.show();

            }
        });

    }


    private void saveSchedule() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(scheduleList);
        editor.putString("bookmark list", json);
        editor.apply();
    }

    private void loadSchedule() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("bookmark list", null);
        Type type = new TypeToken<ArrayList<Event>>() {}.getType();
        scheduleList = gson.fromJson(json, type);

        if (scheduleList == null) {
            scheduleList = new ArrayList<>();
        }
    }


}
