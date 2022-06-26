package com.example.translateapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.translateapp.Model.textdata;

import java.util.List;
@Dao
public interface textDao {
 @Query("SELECT * FROM textdata")
 public List<textdata> getAlldata();
 @Insert
 public void insert(textdata textdata);


}
