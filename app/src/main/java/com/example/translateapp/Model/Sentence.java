package com.example.translateapp.Model;

import android.graphics.RectF;

import java.util.ArrayList;

public class Sentence {
    public  int id;
    public String text;
    public ArrayList<RectF> listRectf;

    public Sentence(int Id, String Text){
        this.id = Id;
        this.text = Text;
        listRectf = new ArrayList<>();

    }

    public void AddRect(RectF rectF){
        this.listRectf.add(rectF);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<RectF> getListRectf() {
        return listRectf;
    }

    public void setListRectf(ArrayList<RectF> listRectf) {
        this.listRectf = listRectf;
    }

    @Override
    public String toString() {
        return "Sentence{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", listRectf=" + listRectf.size() +
                '}';
    }
}
