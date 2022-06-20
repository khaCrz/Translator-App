package com.midterm.traslator_app;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface datadao {
    @Query("SELECT * FROM data")
    public List<data> getAlldata();
    @Insert
    public void insertAll(data...data);


}
