package com.bigO.via;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class SearchFragment extends Fragment {

    private ArrayList<Event> eventList;
    private ArrayList<Event> bookmarkList;
    private RecyclerView sRecyclerView;
    private RecyclerView.LayoutManager sLayoutManager;
    private SearchAdapter sAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        loadBookmarks();

        eventList = new ArrayList<Event>();
        String eventName = "Calgary Auto Show";
        String blurb = "The leading car show in North America";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = new Date();
        Date endDate = new Date();
        try {
            startDate = dateFormat.parse("12/12/2019");
            endDate = dateFormat.parse("17/12/2019");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        eventList.add(new Event(eventName, blurb, startDate, endDate));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        sRecyclerView = view.findViewById(R.id.searchRecyclerView);
        sLayoutManager = new LinearLayoutManager(this.getActivity());
        sAdapter = new SearchAdapter(eventList, bookmarkList);

        sRecyclerView.setHasFixedSize(true);
        sRecyclerView.setLayoutManager(sLayoutManager);
        sRecyclerView.setAdapter(sAdapter);

        sAdapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(int position) {
//                Intent eventMapIntent;
//                eventMapIntent = new Intent(SearchFragment.this.getActivity(), EventMapActivity.class);
//                SearchFragment.this.startActivity(eventMapIntent);
            }

            @Override
            public void onBookmarkButtonClick(int position) {
                boolean isBookmarked = false;
                int bookmarkListPosition = -1;
                Event clickedEvent = eventList.get(position);
                for (int i=0; i<bookmarkList.size(); i++){
                    if (bookmarkList.get(i).getEventName().equals(clickedEvent.getEventName())){
                        isBookmarked = true;
                        bookmarkListPosition = i;
                    }
                }
                if (isBookmarked){
                    bookmarkList.remove(bookmarkListPosition);
                }
                else{
                    bookmarkList.add(clickedEvent);
                }
                sAdapter.notifyItemChanged(position);
                saveBookmarks();
            }

        });

        return view;
    }

    private void saveBookmarks() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(bookmarkList);
        editor.putString("bookmark list", json);
        editor.apply();
    }

    private void loadBookmarks() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("bookmark list", null);
        Type type = new TypeToken<ArrayList<Event>>() {}.getType();
        bookmarkList = gson.fromJson(json, type);

        if (bookmarkList == null) {
            bookmarkList = new ArrayList<>();
        }
    }
}
