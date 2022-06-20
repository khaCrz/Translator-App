package com.midterm.traslator_app;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {data.class}, version = 1)
public abstract class data_database extends RoomDatabase {
    public abstract datadao datadao();

    private static data_database instance;

    public static  data_database getInstance(Context context){
        if(instance == null){
            instance  = Room.databaseBuilder(context,
                    data_database.class, "translator_app").build();
        }
        return  instance;
    }
}
