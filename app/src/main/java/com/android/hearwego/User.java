package com.android.hearwego;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class User {

    public String name;
    //public Map<String, String> locname;
    public List<String> locname;
    public List<String> keyword;
    public List<String> latitude;
    public List<String> longtitude;
    public User() {}
    public User(String name){
        this.name = name;
    }
}