package com.bigO.via;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventRcAdapter extends RecyclerView.Adapter<EventRcAdapter.EventListViewHolder> {

    private ArrayList<PlaceData> places;
    private OnItemClickListener eventAdapterListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
        void onAddToScheduleClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        eventAdapterListener = listener;
    }

    public static class EventListViewHolder extends RecyclerView.ViewHolder {
        public ImageView placeIcon;
        public TextView placeName;
        public TextView placeData;
        public ImageView addToSchedule;


        public EventListViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            placeIcon = itemView.findViewById(R.id.place_logo);
            placeName = itemView.findViewById(R.id.place_name);
            placeData = itemView.findViewById(R.id.place_data);
            addToSchedule = itemView.findViewById(R.id.add_to_schedule_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener !=null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            addToSchedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener !=null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onAddToScheduleClick(position);
                        }
                    }

                }
            });

        }
    }

    public EventRcAdapter(ArrayList<PlaceData> places){
        this.places = places;
    }

    @NonNull
    @Override
    public EventListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_row, parent,false);
        EventListViewHolder eventListViewHolder = new EventListViewHolder(view,eventAdapterListener);
        return  eventListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventListViewHolder holder, int position) {
        PlaceData currentPlace = places.get(position);
        holder.placeIcon.setImageBitmap(currentPlace.getIcon());
        holder.placeName.setText(currentPlace.getName());
        holder.placeData.setText("Random Data");
        // TODO present the place data in a nice way!
//        placeData.setText(singlePlace.getPlaceData().toString());

    }

    @Override
    public int getItemCount() {
        return places.size();
    }

}
