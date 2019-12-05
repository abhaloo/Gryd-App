package com.bigO.via;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventScheduleAdapter extends RecyclerView.Adapter<EventScheduleAdapter.ScheduleListViewHolder> {

    private ArrayList<Place> scheduleList;

    private OnItemClickListener scheduleAdapterListener;

    public void setOnItemClickListener(OnItemClickListener scheduleAdapterListener) {
        this.scheduleAdapterListener = scheduleAdapterListener;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onRemoveClick(int position);
        void onNavigateButtonClick(int position);
    }

    public static class ScheduleListViewHolder extends RecyclerView.ViewHolder {

        public TextView scheduleEventName;
        public TextView scheduleEventTime;
        public TextView scheduleEventData;
        public Button removeButton;
        public Button navigateButton;

        public ScheduleListViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            scheduleEventName = itemView.findViewById(R.id.schedule_event_name);
            scheduleEventTime = itemView.findViewById(R.id.schedule_event_time);
            scheduleEventData = itemView.findViewById(R.id.schedule_event_data);
            removeButton = itemView.findViewById(R.id.schedule_remove);
            navigateButton = itemView.findViewById(R.id.navigate_button);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            listener.onItemClick(pos);
                        }
                    }

                }
            });

            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            listener.onRemoveClick(pos);
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

    public EventScheduleAdapter(ArrayList<Place> scheduleList) {
        this.scheduleList = scheduleList;
    }

    @NonNull
    @Override
    public ScheduleListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_schedule_item, parent,false);
        ScheduleListViewHolder scheduleListViewHolder = new ScheduleListViewHolder(view, scheduleAdapterListener);
        return  scheduleListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleListViewHolder holder, int position) {
        Place currentScheduleElement = scheduleList.get(position);
        holder.scheduleEventName.setText(currentScheduleElement.getName());
        String time = currentScheduleElement.getEventDuration().getDurationAsString();
        holder.scheduleEventTime.setText(time);
        holder.scheduleEventData.setText(currentScheduleElement.getPlaceBlurb());
        if (currentScheduleElement.hasCollision()){
            holder.itemView.setBackgroundColor(Color.parseColor("#FF9999"));
        }
        else {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

}
