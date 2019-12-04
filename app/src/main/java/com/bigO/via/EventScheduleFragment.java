package com.bigO.via;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import io.mapwize.mapwizeui.MapwizeFragment;

import static android.content.Context.MODE_PRIVATE;

public class EventScheduleFragment extends Fragment {

    private ArrayList<Place> scheduleList;

    private RecyclerView scheduleRecylerView;
    private TextView emptyView;
    private Button navigateToAllButton;
    private EventScheduleAdapter scheduleRecyclerListAdapter;
    private RecyclerView.LayoutManager recyclerViewManger;

    private BottomNavigationView bottomNav;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadSchedule();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_event_schedule, container, false);

        bottomNav = this.getActivity().findViewById(R.id.bottom_nav);

        createRecyclerView(view);
        emptyView = view.findViewById(R.id.emptyView);
        navigateToAllButton = view.findViewById(R.id.navigate_to_all);

        if (scheduleList.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
        }
        else {
            emptyView.setVisibility(View.GONE);
        }

        if (scheduleList.size() >= 3) {
            navigateToAllButton.setVisibility(View.VISIBLE);
        }
        else {
            navigateToAllButton.setVisibility(View.GONE);
        }

        scheduleRecyclerListAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {

            @Override
            public void onChanged() {
                super.onChanged();
                checkEmpty();
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                checkEmpty();
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                checkEmpty();
            }

            void checkEmpty() {
                emptyView.setVisibility(scheduleRecyclerListAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
                navigateToAllButton.setVisibility(scheduleRecyclerListAdapter.getItemCount() >= 3 ? View.VISIBLE : View.GONE);
            }
        });

        navigateToAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                multiPointMapSetup();
            }
        });

        return view;
    }

    public void createRecyclerView(View view){
        scheduleRecylerView = view.findViewById(R.id.recycler_schedule_view);
        recyclerViewManger = new LinearLayoutManager(getContext());
        scheduleRecyclerListAdapter = new EventScheduleAdapter(scheduleList);

        scheduleRecylerView.setLayoutManager(recyclerViewManger);
        scheduleRecylerView.setAdapter(scheduleRecyclerListAdapter);

        scheduleRecyclerListAdapter.setOnItemClickListener(new EventScheduleAdapter.OnItemClickListener() {
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

                scheduleList.remove(position);
                checkAllCollisions();
                scheduleRecyclerListAdapter.notifyItemRemoved(position);
                scheduleRecyclerListAdapter.notifyItemRangeChanged(0, scheduleList.size());
                saveSchedule();
            }

            @Override
            public void onNavigateButtonClick(int position) {
                Place clickedPlace = scheduleList.get(position);
                io.mapwize.mapwizesdk.api.Place mapwizePlace = clickedPlace.getMapwizePlace();
                mapSetup(mapwizePlace);
            }
        });

        ItemTouchHelper ith = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder dragged, @NonNull RecyclerView.ViewHolder target) {

                int positionDragged = dragged.getAdapterPosition();
                int positionTarget = target.getAdapterPosition();

                if (!(scheduleList.get(positionDragged).getEventDuration().isTimed())){
                    Collections.swap(scheduleList, positionDragged, positionTarget);
                    scheduleRecyclerListAdapter.notifyItemMoved(positionDragged, positionTarget);
                }

                saveSchedule();

                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        });

        ith.attachToRecyclerView(scheduleRecylerView);

    }

    private void mapSetup(io.mapwize.mapwizesdk.api.Place mapwizePlace){

        EventActivity activity = (EventActivity) this.getActivity();
        MapwizeFragment mapwizeFragment = activity.getMapwizeFragment();

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mapwizePlace);
        editor.putString("custom place", json);
        editor.apply();

        this.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mapwizeFragment).commit();
        bottomNav.setSelectedItemId(R.id.map_view);
    }

    private void multiPointMapSetup(){

        EventActivity activity = (EventActivity) this.getActivity();
        MapwizeFragment mapwizeFragment = activity.getMapwizeFragment();

        ArrayList<io.mapwize.mapwizesdk.api.Place> customPlaceList = new ArrayList<>();
        for (int i=0; i < scheduleList.size(); i++){
            customPlaceList.add(scheduleList.get(i).getMapwizePlace());
        }
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(customPlaceList);
        editor.putString("custom place list", json);
        editor.apply();

        this.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mapwizeFragment).commit();
        bottomNav.setSelectedItemId(R.id.map_view);
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

    private void checkAllCollisions(){
        for (Place place : scheduleList) {
            place.setHasCollision(false);
            EventDuration duration = place.getEventDuration();
            for (Place anotherPlace : scheduleList) {
                EventDuration anotherDuration = anotherPlace.getEventDuration();
                if (!place.getName().equals(anotherPlace.getName())){
                    if (duration.getStartTime() <= anotherDuration.getEndTime() && anotherDuration.getStartTime() <= duration.getEndTime() && anotherDuration.isTimed()){
                        place.setHasCollision(true);
                        anotherPlace.setHasCollision(true);
                    }
                }
            }
        }
    }


}
