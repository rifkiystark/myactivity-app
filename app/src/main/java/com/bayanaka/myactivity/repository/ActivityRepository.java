package com.bayanaka.myactivity.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.bayanaka.myactivity.database.ActivityDao;
import com.bayanaka.myactivity.database.MyActivityDatabase;
import com.bayanaka.myactivity.models.Activities;

import java.util.List;

public class ActivityRepository {

    private ActivityDao activityDao;
    private LiveData<List<Activities>> activitiyList;
    private String day;

    public ActivityRepository(Application application) {
        MyActivityDatabase myActivityDatabase = MyActivityDatabase.getDatabase(application);
        activityDao = myActivityDatabase.activityDao();
    }

    public LiveData<List<Activities>> getActivites(String day){
        activitiyList = activityDao.getActivitiesByDay(day);
        return activitiyList;
    }

    public void insert(Activities activities) {
        new insertActivites(activityDao).execute(activities);
    }

    public void delete(int id) {
        new deleteActivity(activityDao).execute(id);
    }

    private static class insertActivites extends AsyncTask<Activities, Void, Void> {
        private ActivityDao activityDao;
        insertActivites(ActivityDao newActvityDao) {
            activityDao = newActvityDao;
        }


        @Override
        protected Void doInBackground(Activities... activities) {
            activityDao.addActivity(activities[0]);
            return null;
        }
    }

    private static class deleteActivity extends AsyncTask<Integer, Void, Void>{
        private ActivityDao activityDao;

        deleteActivity(ActivityDao newActivityDao) {
            activityDao = newActivityDao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            activityDao.deleteActivityById(integers[0]);
            return null;
        }
    }
}
