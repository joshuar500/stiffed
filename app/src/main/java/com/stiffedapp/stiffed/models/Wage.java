package com.stiffedapp.stiffed.models;

public class Wage {
    public String userID;
    public String wageAmount;
    public String locID;
    public String date;

    public Wage() {
    }

    public Wage(String userID, String wageAmount, String locID, String date) {
        this.userID = userID;
        this.wageAmount = wageAmount;
        this.locID = locID;
        this.date = date;
    }
}
