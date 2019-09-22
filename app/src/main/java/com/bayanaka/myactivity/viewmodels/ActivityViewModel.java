package com.bayanaka.myactivity.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bayanaka.myactivity.models.Activities;
import com.bayanaka.myactivity.repository.ActivityRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityViewModel extends AndroidViewModel {
    private ActivityRepository activityRepository;
    private LiveData<List<Activities>> activityList;
    private MutableLiveData<String> activity = new MutableLiveData<>();
    private MutableLiveData<Date> startTime = new MutableLiveData<>();
    private MutableLiveData<Date> endTime = new MutableLiveData<>();

    public ActivityViewModel(@NonNull Application application) {
        super(application);
        activityRepository = new ActivityRepository(application);
    }

    public LiveData<List<Activities>> getActivities(String day) {
        activityList = activityRepository.getActivites(day);
        return activityList;
    }

    public LiveData<String> getActivityName() {
        return activity;
    }

    public LiveData<Date> getStartTime() {
        return startTime;
    }

    public LiveData<Date> getEndTime() {
        return endTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime.postValue(startTime);
    }

    public void setEndTime(Date endTime) {
        this.endTime.postValue(endTime);
    }

    public void setActivity(String activity) {
        this.activity.postValue(activity);
    }

    public void insert (Activities activities) {
        activityRepository.insert(activities);
    }

    public void delete(int id) {
        activityRepository.delete(id);
    }
}
