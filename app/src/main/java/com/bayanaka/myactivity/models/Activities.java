package com.bayanaka.myactivity.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.bayanaka.myactivity.utils.DateConverter;

import java.sql.Time;
import java.util.Date;

@Entity(tableName = "Activities")
public class Activities {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String activityName;
    private String day;

    @TypeConverters(DateConverter.class)
    private Date startTime;

    @TypeConverters(DateConverter.class)
    private Date endTime;

    public Activities(String activityName, String day, Date startTime, Date endTime) {
        this.activityName = activityName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
