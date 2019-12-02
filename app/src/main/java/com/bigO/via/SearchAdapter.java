package com.bigO.via;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.searchViewHolder> {

    private ArrayList<Event> eventList;
    private ArrayList<Event> bookmarkList;
    private OnItemClickListener eventClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onBookmarkButtonClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        eventClickListener = listener;
    }

    public static class searchViewHolder extends RecyclerView.ViewHolder {

        public ImageView eventImageView;
        public TextView eventNameView;
        public TextView eventDateView;
        public TextView eventBlurbView;
        public ImageView bookmarkButtonView;
        public ImageView expandButtonView;

        public searchViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            eventImageView = itemView.findViewById(R.id.eventImage);
            eventNameView = itemView.findViewById(R.id.eventName);
            eventDateView = itemView.findViewById(R.id.eventDates);
            eventBlurbView = itemView.findViewById(R.id.eventBlurb);
            bookmarkButtonView = itemView.findViewById(R.id.bookmark);
            expandButtonView = itemView.findViewById(R.id.expand_button);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            bookmarkButtonView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onBookmarkButtonClick(position);
                        }
                    }
                }
            });

            expandButtonView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (eventBlurbView.getVisibility() == View.GONE) {
                        eventBlurbView.setVisibility(View.VISIBLE);
                        expandButtonView.setImageResource(R.drawable.ic_eventcard_contract);
                    } else {
                        eventBlurbView.setVisibility(View.GONE);
                        expandButtonView.setImageResource(R.drawable.ic_eventcard_expand);
                    }
                }
            });
        }
    }

    public SearchAdapter(ArrayList<Event> eventList, ArrayList<Event> bookmarkList){
        this.eventList = eventList;
        this.bookmarkList = bookmarkList;
    }

    @NonNull
    @Override
    public searchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_event, parent, false);
        searchViewHolder svh = new searchViewHolder(view, eventClickListener);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull searchViewHolder holder, int position) {
        Event currentEvent = eventList.get(position);
        holder.eventImageView.setImageResource(currentEvent.getEventImage());
        holder.eventNameView.setText(currentEvent.getEventName());
        holder.eventDateView.setText(currentEvent.getDatesAsString());
        holder.eventBlurbView.setText(currentEvent.getBlurb());
        if (currentEvent.isPromoted()){
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorAccent));
        }
        else {
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        boolean isBookmarked = false;
        for (int i=0; i<bookmarkList.size(); i++){
            if (bookmarkList.get(i).getEventName().equals(currentEvent.getEventName())){
                isBookmarked = true;
            }
        }
        if (isBookmarked){
            holder.bookmarkButtonView.setImageResource(R.drawable.ic_eventcard_bookmark_clicked);
        }
        else{
            holder.bookmarkButtonView.setImageResource(R.drawable.ic_eventcard_bookmark);
        }
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
}
