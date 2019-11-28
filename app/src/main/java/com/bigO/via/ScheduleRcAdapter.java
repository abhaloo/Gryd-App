package com.bigO.via;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ScheduleRcAdapter extends RecyclerView.Adapter<ScheduleRcAdapter.ScheduleListViewHolder> {

    private ArrayList<PlaceData> scheduleList;

    private OnItemClickListener scheduleAdapterListener;

    public void setOnItemClickListener(OnItemClickListener scheduleAdapterListener) {
        this.scheduleAdapterListener = scheduleAdapterListener;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onRemoveClick(int position);
    }

    public static class ScheduleListViewHolder extends RecyclerView.ViewHolder {

        public TextView scheduleEventName;
        public TextView scheduleEventTime;
        public TextView scheduleEventData;
        public ImageView removeImage;

        public ScheduleListViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            scheduleEventName = itemView.findViewById(R.id.schedule_event_name);
            scheduleEventTime = itemView.findViewById(R.id.schedule_event_time);
            scheduleEventData = itemView.findViewById(R.id.schedule_event_data);
            removeImage = itemView.findViewById(R.id.schedule_remove);

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

            removeImage.setOnClickListener(new View.OnClickListener() {
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
        }
    }

    public ScheduleRcAdapter(ArrayList<PlaceData> scheduleList) {
        this.scheduleList = scheduleList;
    }

    @NonNull
    @Override
    public ScheduleListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_card, parent,false);
        ScheduleListViewHolder scheduleListViewHolder = new ScheduleListViewHolder(view, scheduleAdapterListener);
        return  scheduleListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleListViewHolder holder, int position) {
        PlaceData currentScheduleElement = scheduleList.get(position);
        holder.scheduleEventName.setText(currentScheduleElement.getName());
        // TODO set time and data
        String time = "From:\t" + currentScheduleElement.getEventDuration().getStartHour() + ":" + currentScheduleElement.getEventDuration().getStartMinute()
                + "to " + currentScheduleElement.getEventDuration().getEndHour() + ":" + currentScheduleElement.getEventDuration().getEndMinute();
        holder.scheduleEventTime.setText(time);
        holder.scheduleEventData.setText("Random Data");
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

}
