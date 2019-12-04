package com.bigO.via;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;

import io.mapwize.mapwizesdk.api.MapwizeObject;

import static android.content.Context.MODE_PRIVATE;

public class EventPlaceHighlightFragment extends Fragment {

    private Place selectedPlace;

    private ArrayList<Place> scheduleList;

    private TextView placeName;
    private TextView placeTimes;
    private TextView placeDescription;
    private Button scheduleButton;

    public EventPlaceHighlightFragment(Place selectedPlace){
        this.selectedPlace = selectedPlace;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_event_place_highlight, container, false);

        placeName = view.findViewById(R.id.primary_text);
        placeTimes = view.findViewById(R.id.sub_text);
        placeDescription = view.findViewById(R.id.supporting_text);
        scheduleButton = view.findViewById(R.id.action_button_1);

        loadSchedule();
        updateScheduleButton();

        scheduleButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                boolean isScheduled = false;
                int locationInSchedule = 0;
                for (int i=0; i<scheduleList.size(); i++){
                    if (scheduleList.get(i).getName().equals(selectedPlace.getName())){
                        isScheduled = true;
                        locationInSchedule = i;
                    }
                }
                if(!isScheduled && Place.isEvent(selectedPlace.getName())){
                    EventDuration duration = selectedPlace.getEventDuration();

                    if (checkCollisions(selectedPlace)){
//                        scheduleList.add(selectedPlace);
                        addToSchedule(selectedPlace);
                        String test = selectedPlace.getName() + " has been added to your schedule but it collides with another attraction";
                        Toast toast = Toast.makeText(getContext(), test, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else {
//                        scheduleList.add(selectedPlace);
                        addToSchedule(selectedPlace);
                        String test = selectedPlace.getName() + " has been added to your schedule";
                        Toast toast = Toast.makeText(getContext(), test, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    updateScheduleButton();
                    saveSchedule();

                } else if(!Place.isEvent(selectedPlace.getName())){
                    String test = "This location cannot be added to your schedule";
                    Toast toast = Toast.makeText(getContext(), test, Toast.LENGTH_SHORT);
                    toast.show();

                } else {
                    scheduleList.remove(locationInSchedule);
                    checkAllCollisions();
                    String test = selectedPlace.getName() + " was removed from your schedule";
                    Toast toast = Toast.makeText(getContext(), test, Toast.LENGTH_SHORT);
                    toast.show();

                    updateScheduleButton();
                    saveSchedule();
                }
            }
        });

        placeName.setText(selectedPlace.getName());
        placeTimes.setText("-");
        placeDescription.setText("-");

        return view;
    }

    private void updateScheduleButton(){

        boolean isScheduled = false;
        for (int i=0; i<scheduleList.size(); i++){
            if (scheduleList.get(i).getName().equals(selectedPlace.getName())){
                isScheduled = true;
            }
        }
        if (isScheduled){
            scheduleButton.setText("Remove from schedule");
        }
        else {
            scheduleButton.setText("Add to schedule");
        }

    }

    private void addToSchedule(Place addedPlace){

        if (addedPlace.getEventDuration().isTimed()) {
            int i;
            for (i = 0; i < scheduleList.size(); i++) {
                Place currentPlace = scheduleList.get(i);
                if (addedPlace.getEventDuration().getStartTime() <= currentPlace.getEventDuration().getStartTime()) {
                    break;
                }
            }
            scheduleList.add(i, addedPlace);
        }

        else {
            scheduleList.add(addedPlace);
        }

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

    private void saveSchedule() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(scheduleList);
        editor.putString("schedule list", json);
        editor.apply();
    }

    private boolean checkCollisions(Place clickedPlace){

        EventDuration duration = clickedPlace.getEventDuration();
        boolean collision = false;

        for(Place place:scheduleList) {
            EventDuration scheduleEventDuration = place.getEventDuration();
            if (duration.getStartTime() <= scheduleEventDuration.getEndTime() && scheduleEventDuration.getStartTime() <= duration.getEndTime() && scheduleEventDuration.isTimed()){
                place.setHasCollision(true);
                clickedPlace.setHasCollision(true);
                collision = true;
            }
        }
        return collision;
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
