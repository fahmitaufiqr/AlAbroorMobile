package com.example.alabroormobile.calendar;

import java.util.ArrayList;

public class HomeCollection {
    public String date="";
    public String name="";
    public String description="";


    public static ArrayList<HomeCollection> date_collection_arr;
    public HomeCollection(String date, String name, String description){

        this.date=date;
        this.name=name;
        this.description= description;

    }
}
