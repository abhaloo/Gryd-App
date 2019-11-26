package com.bigO.via;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ScheduleRcAdapter extends RecyclerView.Adapter<ScheduleRcAdapter.ScheduleListViewHolder> {

    private ArrayList<ScheduleElement> scheduleList;

    public static class ScheduleListViewHolder extends RecyclerView.ViewHolder {

        public TextView scheduleEventName;
        public TextView scheduleEventTime;
        public TextView scheduleEventData;

        public ScheduleListViewHolder(@NonNull View itemView) {
            super(itemView);
            scheduleEventName = itemView.findViewById(R.id.schedule_event_name);
            scheduleEventTime = itemView.findViewById(R.id.schedule_event_time);
            scheduleEventData = itemView.findViewById(R.id.schedule_event_data);
        }
    }

    public ScheduleRcAdapter(ArrayList<ScheduleElement> scheduleList) {
        this.scheduleList = scheduleList;
    }

    @NonNull
    @Override
    public ScheduleListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_card, parent,false);
        ScheduleListViewHolder scheduleListViewHolder = new ScheduleListViewHolder(view);
        return  scheduleListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleListViewHolder holder, int position) {
        ScheduleElement currentScheduleElement = scheduleList.get(position);
        holder.scheduleEventName.setText(currentScheduleElement.getName());
        holder.scheduleEventTime.setText(currentScheduleElement.getTime());
        holder.scheduleEventData.setText(currentScheduleElement.getData());
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }




}
