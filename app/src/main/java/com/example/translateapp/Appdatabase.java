package com.example.translateapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.translateapp.Model.textdata;


@Database(entities = {textdata.class}, version = 1)


public abstract class Appdatabase extends RoomDatabase {
    public abstract textDao textDao();

    private static Appdatabase instance;
    public static  Appdatabase getInstance(Context context){
        if(instance == null){
            instance  = Room.databaseBuilder(context,
                    Appdatabase.class, "Translator-App").build();
        }
        return  instance;
    }

}
