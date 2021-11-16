package com.android.hearwego;
import java.util.List;

public class User {

    public String name;
    public List<String> keyword;
    public List<String> locname;
    public List<String> latitude;
    public List<String> longtitude;
    public User() {}
    public User(String name){
        this.name = name;
    }
}