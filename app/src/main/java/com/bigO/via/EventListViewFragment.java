package com.bigO.via;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import io.mapwize.mapwizeui.MapwizeFragment;

import static android.content.Context.MODE_PRIVATE;


public class EventListViewFragment extends Fragment {

    private ArrayList<Place> places;
    private ArrayList<Place> scheduleList;

    private RecyclerView eventRecylerView;
    private EventListViewAdapter eventRecyclerListAdapter;
    private RecyclerView.LayoutManager recyclerViewManger;

    private BottomNavigationView bottomNav;

    public EventListViewFragment(ArrayList<Place> places){
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
        setHasOptionsMenu(true);

        bottomNav = this.getActivity().findViewById(R.id.bottom_nav);

        createRecyclerView(view);

        eventRecyclerListAdapter.setOnItemClickListener(new EventListViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String test = places.get(position).getName() + " was clicked";
                Toast toast = Toast.makeText(getContext(), test, Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onScheduleButtonClick(int position) {
                // check if it's already there
                boolean isScheduled = false;
                Place clickedEvent = places.get(position);
                for (int i=0; i<scheduleList.size(); i++){
                    if (scheduleList.get(i).getName().equals(clickedEvent.getName())){
                        isScheduled = true;
                    }
                }

               // add it to the scheduled list if its not in the list and it is an event
                if(!isScheduled && Place.isEvent(clickedEvent.getName())){
                    EventDuration duration = places.get(position).getEventDuration();

                    if (checkCollisions(duration)){
                        String test = places.get(position).getName() + " has been added to your schedule but it collides with another attraction";
                        Toast toast = Toast.makeText(getContext(), test, Toast.LENGTH_SHORT);
                        toast.show();
                        scheduleList.add(places.get(position));

                    }
                    else {
                        scheduleList.add(places.get(position));
                        String test = places.get(position).getName() + " has been added to your schedule";
                        Toast toast = Toast.makeText(getContext(), test, Toast.LENGTH_SHORT);
                        toast.show();

                    }
                    eventRecyclerListAdapter.notifyItemChanged(position);
                    saveSchedule();

                } else if(!Place.isEvent(clickedEvent.getName())){
                    String test = "You can only add timed attractions to your schedule";
                    Toast toast = Toast.makeText(getContext(), test, Toast.LENGTH_SHORT);
                    toast.show();

                } else {
                    String test = scheduleList.get(position).getName() + " was removed from your schedule";
                    Toast toast = Toast.makeText(getContext(), test, Toast.LENGTH_SHORT);
                    toast.show();

                    scheduleList.remove(position);
                    eventRecyclerListAdapter.notifyItemChanged(position);
                    saveSchedule();

                }
            }

            @Override
            public void onNavigateButtonClick(int position) {
                Place clickedPlace = places.get(position);
                io.mapwize.mapwizesdk.api.Place mapwizePlace = clickedPlace.getMapwizePlace();
                mapSetup(mapwizePlace);
            }

        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();

        inflater.inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                eventRecyclerListAdapter.getFilter().filter(newText);
                return false;
            }

        });
    }

    private void mapSetup(io.mapwize.mapwizesdk.api.Place mapwizePlace){
        EventActivity activity = (EventActivity) this.getActivity();
        MapwizeFragment mapwizeFragment = activity.getMapwizeFragment();
        mapwizeFragment.selectPlace(mapwizePlace, true);
        mapwizeFragment.showDirectionUI();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mapwizeFragment).commit();
        bottomNav.setSelectedItemId(R.id.map_view);
    }

    private void createRecyclerView(View view){
        eventRecylerView = view.findViewById(R.id.recycler_event_list);
        eventRecylerView.setHasFixedSize(true);
        recyclerViewManger = new LinearLayoutManager(getContext());
        eventRecyclerListAdapter = new EventListViewAdapter(places, scheduleList);

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
        Type type = new TypeToken<ArrayList<Place>>() {}.getType();
        scheduleList = gson.fromJson(json, type);

        if (scheduleList == null) {
            scheduleList = new ArrayList<>();
        }
    }


    private boolean checkCollisions(EventDuration duration){

        boolean collision = false;

        for(Place place:scheduleList) {
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
