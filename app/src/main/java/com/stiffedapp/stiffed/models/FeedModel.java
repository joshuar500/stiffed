package com.stiffedapp.stiffed.models;


public class FeedModel {
    public String date;
    public String action;
    public String interact;

    public FeedModel(String date, String action, String interact) {
        this.date = date;
        this.action = action;
        this.interact = interact;
    }
}
