package com.bayanaka.myactivity.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.bayanaka.myactivity.models.Activities;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ActivityDao {

    @Query("select * from Activities")
    LiveData<List<Activities>> getActivities();

    @Query("select * from Activities where day = :day order by startTime asc")
    LiveData<List<Activities>> getActivitiesByDay(String day);

    @Query("select * from Activities where id = :id")
    Activities getActivityById(String id);

    @Insert(onConflict = REPLACE)
    void addActivity(Activities borrowModel);

    @Query("delete from Activities where id = :id")
    void deleteActivityById(int id);
}
