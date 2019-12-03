package com.bigO.via;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import io.mapwize.mapwizesdk.api.MapwizeObject;

public class EventPlaceHighlightFragment extends Fragment {

    private Place selectedPlace;

    private TextView placeName;
    private TextView placeTimes;

    public EventPlaceHighlightFragment(Place selectedPlace){
        this.selectedPlace = selectedPlace;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_event_place_highlight, container, false);
        placeName = view.findViewById(R.id.primary_text);
        setName(selectedPlace.getName());
        return view;
    }

    public void setName(String aPlaceName){
        placeName.setText(aPlaceName);
    }

}
