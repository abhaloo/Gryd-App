package com.bigO.via;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

        eventList = new ArrayList<>();

        String eventName = "Calgary Auto Show 2020";
        String blurb = "The leading car show in North America";
        Date startDate = new Date();
        Date endDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            startDate = dateFormat.parse("11/03/2020");
            endDate = dateFormat.parse("15/03/2020");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        eventList.add(new Event(R.drawable.ciats_image, eventName, blurb, startDate, endDate, true, true));

        eventName = "Calgary Stampede 2020";
        blurb = "Information on this event is not available yet. Please check back later!";
        startDate = new Date();
        endDate = new Date();
        try {
            startDate = dateFormat.parse("03/07/2020");
            endDate = dateFormat.parse("12/07/2020");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        eventList.add(new Event(R.drawable.cs_image, eventName, blurb, startDate, endDate, false, false));

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        sRecyclerView = view.findViewById(R.id.searchRecyclerView);
        sLayoutManager = new LinearLayoutManager(this.getActivity(),LinearLayoutManager.VERTICAL, false);
        sAdapter = new SearchAdapter(eventList, bookmarkList);

        sRecyclerView.setHasFixedSize(true);
        sRecyclerView.setLayoutManager(sLayoutManager);
        sRecyclerView.setAdapter(sAdapter);

        sAdapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(int position) {
                if (eventList.get(position).isAvailable()) {
                    Intent eventMapIntent;
                    eventMapIntent = new Intent(SearchFragment.this.getActivity(), EventActivity.class);
                    SearchFragment.this.startActivity(eventMapIntent);
                }
                else {
                    String test = eventList.get(position).getEventName() + " is not available yet";
                    Toast toast = Toast.makeText(getContext(), test, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onBookmarkButtonClick(int position) {
                boolean isBookmarked = false;
                Event clickedEvent = eventList.get(position);
                for (int i=0; i<bookmarkList.size(); i++){
                    if (bookmarkList.get(i).getEventName().equals(clickedEvent.getEventName())){
                        isBookmarked = true;
                        bookmarkList.remove(i);
                    }
                }
                if (!isBookmarked){
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
