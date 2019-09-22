package com.bayanaka.myactivity.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.bayanaka.myactivity.models.Activities;

@Database(entities = {Activities.class}, version = 1, exportSchema = false)
public abstract class MyActivityDatabase extends RoomDatabase {
    private static MyActivityDatabase INSTANCE;

    public static MyActivityDatabase getDatabase(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    MyActivityDatabase.class, "myactivity_db").build();
        }

        return INSTANCE;
    }

    public abstract ActivityDao activityDao();
}
