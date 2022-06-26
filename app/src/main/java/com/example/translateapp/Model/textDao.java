package com.example.translateapp.Model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface textDao {
 @Query("SELECT * FROM textdata")
 public List<textdata> getAlldata();
 @Insert
 public void insert(textdata textdata);


}
