package com.android.hearwego;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class User {

    public String name;
    public List<String> keywords;
    public Map<String, String> locnames;
    public Map<String, GeoPoint> geopoints;
    public User() {}
    public User(String name){
        this.name = name;
    }
    public User (String name, List<String> keywords, Map<String,String> locnames, Map<String, GeoPoint> geopoints){
        this.name = name;
        this.keywords = keywords;
        this.locnames = locnames;
        this.geopoints = geopoints;
    }

}