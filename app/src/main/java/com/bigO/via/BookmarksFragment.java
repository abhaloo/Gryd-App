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

public class BookmarksFragment extends Fragment {

    private ArrayList<Event> bookmarkList;
    private RecyclerView bRecyclerView;
    private RecyclerView.LayoutManager bLayoutManager;
    private BookmarksAdapter bAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        loadBookmarks();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_bookmarks, container, false);

        bRecyclerView = view.findViewById(R.id.bookmarksRecyclerView);
        bLayoutManager = new LinearLayoutManager(this.getActivity());
        bAdapter = new BookmarksAdapter(bookmarkList);

        bRecyclerView.setHasFixedSize(true);
        bRecyclerView.setLayoutManager(bLayoutManager);
        bRecyclerView.setAdapter(bAdapter);

        bAdapter.setOnItemClickListener(new BookmarksAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent eventMapIntent;
                eventMapIntent = new Intent(BookmarksFragment.this.getActivity(), EventMapActivity.class);
                BookmarksFragment.this.startActivity(eventMapIntent);
            }

            @Override
            public void onRemoveButtonClick(int position) {
                bookmarkList.remove(position);
                bAdapter.notifyItemRemoved(position);
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
