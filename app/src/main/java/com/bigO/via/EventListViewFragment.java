package com.bigO.via;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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


public class EventListViewFragment extends Fragment {

    private ArrayList<PlaceData> places;

    private RecyclerView eventRecylerView;
    private EventRcAdapter eventRecyclerListAdapter;
    private  RecyclerView.LayoutManager recyclerViewManger;
    private ArrayList<PlaceData> scheduleList;

    public EventListViewFragment(ArrayList<PlaceData> places){
        this.places = places;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadSchedule();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_event_listview, container, false);
        createRecyclerView(view);

        eventRecyclerListAdapter.setOnItemClickListener(new EventRcAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String test = places.get(position).getName() + " was click";
                Toast toast = Toast.makeText(getContext(), test, Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onAddToScheduleClick(int position) {
                // check if it's already there
                boolean isScheduled = false;
                PlaceData clickedEvent = places.get(position);
                for (int i=0; i<scheduleList.size(); i++){
                    if (scheduleList.get(i).getName().equals(clickedEvent.getName())){
                        isScheduled = true;
                    }
                }

               // add it to the scheduled list if its not in the list and it is an event
                if(!isScheduled && PlaceData.isEvent(clickedEvent.getName())){
                    EventDuration duration = places.get(position).getEventDuration();

                    if (checkCollisions(duration)){
                        Log.i("Debug","collision");
                        String test = places.get(position).getName() + " Colliding with the current schedule but adding it";
                        Toast toast = Toast.makeText(getContext(), test, Toast.LENGTH_SHORT);
                        toast.show();
                        scheduleList.add(places.get(position));

                    }else {
                        scheduleList.add(places.get(position));
                        String test = places.get(position).getName() + " Should be added to Schedule";
                        Toast toast = Toast.makeText(getContext(), test, Toast.LENGTH_SHORT);
                        toast.show();

                    }
                    eventRecyclerListAdapter.notifyItemChanged(position);
                    saveSchedule();


                } else if(!PlaceData.isEvent(clickedEvent.getName())){
                    String test = "Cannot add activity " + places.get(position).getName() + " to the schedule";
                    Toast toast = Toast.makeText(getContext(), test, Toast.LENGTH_SHORT);
                    toast.show();

                } else {
                    String test = places.get(position).getName() + " Already in the Schedule";
                    Toast toast = Toast.makeText(getContext(), test, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        return view;
    }

    public void createRecyclerView(View view){
        eventRecylerView = view.findViewById(R.id.recycler_event_list);
        eventRecylerView.setHasFixedSize(true);
        recyclerViewManger = new LinearLayoutManager(getContext());
        eventRecyclerListAdapter = new EventRcAdapter(places);

        eventRecylerView.setLayoutManager(recyclerViewManger);
        eventRecylerView.setAdapter(eventRecyclerListAdapter);

    }

    private void saveSchedule() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(scheduleList);
        editor.putString("schedule list", json);
        editor.apply();
    }

    private void loadSchedule() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("schedule list", null);
        Type type = new TypeToken<ArrayList<PlaceData>>() {}.getType();
        scheduleList = gson.fromJson(json, type);

        if (scheduleList == null) {
            scheduleList = new ArrayList<>();
        }
    }


    public boolean checkCollisions(EventDuration duration){

        boolean collision = false;

        for(PlaceData place:scheduleList) {
            EventDuration scheduleEventDuration = place.getEventDuration();

            if (
                    // starts after ends before
                    (scheduleEventDuration.getStartHour() >= duration.getStartHour()
                    && scheduleEventDuration.getEndHour() <= duration.getEndHour())
            || (scheduleEventDuration.getStartHour() <= duration.getStartHour()
                            && scheduleEventDuration.getEndHour() >= duration.getEndHour())) {
                collision = true;
            }
        }
        return collision;
    }

}
