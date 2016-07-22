package com.stiffedapp.stiffed.models;

import java.util.List;

/**
 * Created by SPACE on 7/19/2016.
 */

public class Location {
    public String city;
    public String state;
    public String zip;
    public List<Integer> tips;
    public List<Integer> wages;

    public Location() {
    }

    public Location(String city, String state, String zip, List<Integer> tips, List<Integer> wages) {
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.tips = tips;
        this.wages = wages;
    }
}
