package com.bigO.via;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookmarksAdapter extends RecyclerView.Adapter<BookmarksAdapter.bookmarksViewHolder> {

    private ArrayList<Event> bookmarkList;
    private OnItemClickListener bookmarkClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onRemoveButtonClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        bookmarkClickListener = listener;
    }

    public static class bookmarksViewHolder extends RecyclerView.ViewHolder {

        public TextView eventNameView;
        public TextView eventDateView;
        public TextView eventBlurbView;
        public ImageView removeButtonView;
        public ImageView expandButtonView;
        public TextView noBookmark;

        public bookmarksViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            eventNameView = itemView.findViewById(R.id.eventName);
            eventDateView = itemView.findViewById(R.id.eventDates);
            eventBlurbView = itemView.findViewById(R.id.eventBlurb);
            removeButtonView = itemView.findViewById(R.id.removeBookmark);
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

            removeButtonView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onRemoveButtonClick(position);
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

    public BookmarksAdapter(ArrayList<Event> bookmarkList){
        this.bookmarkList = bookmarkList;
    }

    @NonNull
    @Override
    public bookmarksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_card, parent, false);
        bookmarksViewHolder bvh = new bookmarksViewHolder(view, bookmarkClickListener);
        return bvh;
    }

    @Override
    public void onBindViewHolder(@NonNull bookmarksViewHolder holder, int position) {
        Event currentBookmark = bookmarkList.get(position);
        holder.eventNameView.setText(currentBookmark.getEventName());
        holder.eventDateView.setText(currentBookmark.getDatesAsString());
        holder.eventBlurbView.setText(currentBookmark.getBlurb());
    }

    @Override
    public int getItemCount() {
        return bookmarkList.size();
    }
}
