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

}