package com.bayanaka.myactivity.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bayanaka.myactivity.R;
import com.bayanaka.myactivity.models.Activities;
import com.google.android.material.card.MaterialCardView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DayRecyclerAdapter extends RecyclerView.Adapter<DayRecyclerAdapter.DayRecyclerViewHolder> {

    private OnItemClickCallback onItemClickCallback;
    private List<Activities> listOfActivities = new ArrayList<>();
    private Context context;


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public DayRecyclerAdapter() {
    }

    public List<Activities> getListOfActivities() {
        return listOfActivities;
    }

    public void setListOfActivities(List<Activities> listOfActivities) {
        this.listOfActivities = listOfActivities;
        notifyDataSetChanged();
    }
    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public DayRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity, parent, false);
        return new DayRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayRecyclerViewHolder holder, final int position) {
        String startTime, endTime;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

        startTime = simpleDateFormat.format(listOfActivities.get(position).getStartTime());
        endTime = simpleDateFormat.format(listOfActivities.get(position).getEndTime());

        holder.tvTime.setText((startTime + " - " + endTime));

        holder.tvActivity.setText(listOfActivities.get(position).getActivityName());



        holder.itemActivity.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemClickCallback.onItemLongClick(listOfActivities.get(position));
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return listOfActivities.size();
    }

    public class DayRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tvActivity;
        TextView tvTime;
        MaterialCardView itemActivity;
        public DayRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            tvActivity = itemView.findViewById(R.id.tvActivity);
            itemActivity = itemView.findViewById(R.id.itemActivity);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }

    public interface OnItemClickCallback {
        void onItemLongClick(Activities data);
    }
}
