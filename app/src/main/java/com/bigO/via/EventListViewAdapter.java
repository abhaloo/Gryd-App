package com.bigO.via;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private ArrayList<Place> scheduleList;
    private OnItemClickListener eventAdapterListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onScheduleButtonClick(int position);
        void onNavigateButtonClick(int position);
    }

    public EventListViewAdapter(ArrayList<Place> places, ArrayList<Place> scheduleList){
        this.places = places;
        this.allPlaces = new ArrayList<>(places);

        this.scheduleList = scheduleList;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        eventAdapterListener = listener;
    }

    public static class EventListViewHolder extends RecyclerView.ViewHolder {
        public ImageView placeIcon;
        public TextView placeName;
        public TextView placeData;
        public TextView placeDuration;
        public Button scheduleButton;
        public Button navigateButton;

        public EventListViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            placeIcon = itemView.findViewById(R.id.place_logo);
            placeName = itemView.findViewById(R.id.place_name);
            placeData = itemView.findViewById(R.id.place_data);
            placeDuration = itemView.findViewById(R.id.place_duration);
            scheduleButton = itemView.findViewById(R.id.schedule_button);
            navigateButton = itemView.findViewById(R.id.navigate_button);


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

            scheduleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener !=null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onScheduleButtonClick(position);
                        }
                    }
                }
            });

            navigateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener !=null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onNavigateButtonClick(position);
                        }
                    }
                }
            });

        }
    }

    @NonNull
    @Override
    public EventListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_card, parent,false);
        EventListViewHolder eventListViewHolder = new EventListViewHolder(view, eventAdapterListener);
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
        boolean isScheduled = false;
        for (int i=0; i<scheduleList.size(); i++){
            if (scheduleList.get(i).getName().equals(currentPlace.getName())){
                isScheduled = true;
            }
        }
        if (!isScheduled){
            holder.scheduleButton.setText("Add to Schedule");
        }
        else{
            holder.scheduleButton.setText("Remove from Schedule");
        }

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
