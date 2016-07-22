package com.stiffedapp.stiffed.models;


import java.util.List;
import java.util.Map;

public class User {
    public String name;
    public String email;
    public List<Integer> tips;
    public List<Integer> wages;
    public String locID;
    public Map<Object, Boolean> friends;
    public String dateCreated;

    public User() {

    }

    public User(String name, String email, List<Integer> tips, List<Integer> wages, String locID, Map<Object, Boolean> friends, String dateCreated) {
        this.name = name;
        this.email = email;
        this.tips = tips;
        this.wages = wages;
        this.friends = friends;
        this.dateCreated = dateCreated;
    }
}
