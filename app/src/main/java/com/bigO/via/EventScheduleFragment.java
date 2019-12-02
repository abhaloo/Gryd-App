package com.bigO.via;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class EventScheduleFragment extends Fragment {

    private ArrayList<Place> scheduleList;

    private RecyclerView scheduleRecylerView;
    private TextView emptyView;
    private EventScheduleAdapter scheduleRecyclerListAdapter;
    private RecyclerView.LayoutManager recyclerViewManger;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadSchedule();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_event_schedule, container, false);

        createRecyclerView(view);
        emptyView = view.findViewById(R.id.emptyView);

        if (scheduleList.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
        }
        else {
            emptyView.setVisibility(View.GONE);
        }

        scheduleRecyclerListAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {

            @Override
            public void onChanged() {
                super.onChanged();
                checkEmpty();
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                checkEmpty();
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                checkEmpty();
            }

            void checkEmpty() {
                emptyView.setVisibility(scheduleRecyclerListAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
            }
        });

        return view;
    }

    public void createRecyclerView(View view){
        scheduleRecylerView = view.findViewById(R.id.recycler_schedule_view);
        recyclerViewManger = new LinearLayoutManager(getContext());
        scheduleRecyclerListAdapter = new EventScheduleAdapter(scheduleList);

        scheduleRecylerView.setLayoutManager(recyclerViewManger);
        scheduleRecylerView.setAdapter(scheduleRecyclerListAdapter);

        scheduleRecyclerListAdapter.setOnItemClickListener(new EventScheduleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String test = scheduleList.get(position).getName() + " was click";
                Toast toast = Toast.makeText(getContext(), test, Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onRemoveClick(int position) {
                String test = scheduleList.get(position).getName() + " was removed";
                Toast toast = Toast.makeText(getContext(), test, Toast.LENGTH_SHORT);
                toast.show();

                scheduleList.remove(position);
                scheduleRecyclerListAdapter.notifyItemRemoved(position);
                saveSchedule();
            }
        });

    }

    private void saveSchedule() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(scheduleList);
        editor.putString("schedule list", json);
        editor.apply();
    }

    private void loadSchedule() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("schedule list", null);
        Type type = new TypeToken<ArrayList<Place>>() {}.getType();
        scheduleList = gson.fromJson(json, type);

        if (scheduleList == null) {
            scheduleList = new ArrayList<>();
        }
    }


}
