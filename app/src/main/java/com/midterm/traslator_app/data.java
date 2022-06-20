package com.midterm.traslator_app;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
@Entity
public class data {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String dateime;

    private String username;

    private String password;

    public data(int id, String name, String dateime, String username, String password) {
        this.id = id;
        this.name = name;
        this.dateime = dateime;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateime() {
        return dateime;
    }

    public void setDateime(String dateime) {
        this.dateime = dateime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
