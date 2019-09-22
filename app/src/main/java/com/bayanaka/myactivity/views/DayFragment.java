package com.bayanaka.myactivity.views;


import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bayanaka.myactivity.R;
import com.bayanaka.myactivity.adapters.DayRecyclerAdapter;
import com.bayanaka.myactivity.models.Activities;
import com.bayanaka.myactivity.viewmodels.ActivityViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DayFragment extends Fragment implements DayRecyclerAdapter.OnItemClickCallback {

    private String day;
    private RecyclerView recyclerView;
    private DayRecyclerAdapter adapter;
    private List<Activities> activitiesList;
    private ActivityViewModel activityViewModel;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public DayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_monday, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerActivity);
        activitiesList = new ArrayList<>();
        initRecylerView();

        activityViewModel = ViewModelProviders.of(this).get(ActivityViewModel.class);

        activityViewModel.getActivities(day).observe(this, new Observer<List<Activities>>() {
            @Override
            public void onChanged(List<Activities> activities) {
                setActivityList(activities);
            }
        });
    }

    private void initRecylerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new DayRecyclerAdapter();
        adapter.setContext(getContext());

        adapter.setOnItemClickCallback(this);
        recyclerView.setAdapter(adapter);

    }

    public void setActivityList(List<Activities> activitiesList) {
        adapter.setListOfActivities(activitiesList);
    }

    @Override
    public void onItemLongClick(final Activities data) {
        new MaterialAlertDialogBuilder(getContext())
                .setTitle("Yakin mau hapus ?")
                .setMessage("Awokwoko")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteActivityById(data.getId());
                    }
                })
                .show();
    }

    private void deleteActivityById(int id) {
        activityViewModel.delete(id);
    }
}
