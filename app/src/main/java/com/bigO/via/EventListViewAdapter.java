package com.bigO.via;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventListViewAdapter extends RecyclerView.Adapter<EventListViewAdapter.EventListViewHolder> implements Filterable {

    private ArrayList<Place> places;
    private ArrayList<Place> allPlaces;
    private OnItemClickListener eventAdapterListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onAddToScheduleClick(int position);
    }

    public EventListViewAdapter(ArrayList<Place> places){
        this.places = places;
        this.allPlaces = new ArrayList<>(places);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        eventAdapterListener = listener;
    }

    public static class EventListViewHolder extends RecyclerView.ViewHolder {
        public ImageView placeIcon;
        public TextView placeName;
        public TextView placeData;
        public TextView placeDuration;
        public ImageView addToSchedule;


        public EventListViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            placeIcon = itemView.findViewById(R.id.place_logo);
            placeName = itemView.findViewById(R.id.place_name);
//            placeData = itemView.findViewById(R.id.place_data);
            placeDuration = itemView.findViewById(R.id.place_duration);
//            addToSchedule = itemView.findViewById(R.id.add_to_schedule_image);

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

//            addToSchedule.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (listener !=null) {
//                        int position = getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION){
//                            listener.onAddToScheduleClick(position);
//                        }
//                    }
//                }
//            });

        }
    }

    @NonNull
    @Override
    public EventListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_card, parent,false);
        EventListViewHolder eventListViewHolder = new EventListViewHolder(view,eventAdapterListener);
        return  eventListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventListViewHolder holder, int position) {
        Place currentPlace = places.get(position);
        holder.placeIcon.setImageBitmap(currentPlace.getIcon());
        holder.placeName.setText(currentPlace.getName());
        String duration = "From:\t" + currentPlace.getEventDuration().getStartHour() + ":" + currentPlace.getEventDuration().getStartMinute()
                + " to " + currentPlace.getEventDuration().getEndHour() + ":" + currentPlace.getEventDuration().getEndMinute();
        holder.placeDuration.setText(duration);

//        holder.placeData.setText("Random Data");
        // TODO present the place data in a nice way!
//        placeData.setText(singlePlace.getPlaceData().toString());

    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    @Override
    public Filter getFilter() {
        return placesFilter;
    }

    private Filter placesFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Place> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(allPlaces);
            }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Place place : allPlaces){
                    if (place.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(place);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            places.clear();
            places.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };

}
