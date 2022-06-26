package com.example.translateapp.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class textdata {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String fromlanguage;
    private String tolanguage;
    private String textfrom;
    private String textto;
    private String datetime;

    public textdata(String fromlanguage, String tolanguage, String textfrom, String textto, String datetime) {
        this.fromlanguage = fromlanguage;
        this.tolanguage = tolanguage;
        this.textfrom = textfrom;
        this.textto = textto;
        this.datetime = datetime;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getFromlanguage() {
        return fromlanguage;
    }

    public void setFromlanguage(String fromlanguage) {
        this.fromlanguage = fromlanguage;
    }

    public String getTolanguage() {
        return tolanguage;
    }

    public void setTolanguage(String tolanguage) {
        this.tolanguage = tolanguage;
    }

    public String getTextfrom() {
        return textfrom;
    }

    public void setTextfrom(String textfrom) {
        this.textfrom = textfrom;
    }

    public String getTextto() {
        return textto;
    }

    public void setTextto(String textto) {
        this.textto = textto;
    }
}
