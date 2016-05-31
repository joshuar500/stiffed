package com.stiffedapp.stiffed;

import java.util.ArrayList;

/**
 * Created by SPACE on 5/30/2016.
 */

public class Summary {
    public String sName;
    public String sNumber;

    public Summary(String sName, String sNumber){
        this.sName = sName;
        this.sNumber = sNumber;
    }

    public static ArrayList<Summary> getSummary(){
        ArrayList<Summary> summaries = new ArrayList<>();
        summaries.add(new Summary("Price", "12"));
        summaries.add(new Summary("Tax", "142"));
        summaries.add(new Summary("Numa", "121"));
        summaries.add(new Summary("Price", "12"));
        summaries.add(new Summary("Tax", "142"));
        summaries.add(new Summary("Numa", "121"));
        summaries.add(new Summary("Price", "12"));
        summaries.add(new Summary("Tax", "142"));
        summaries.add(new Summary("Numa", "121"));
        summaries.add(new Summary("Price", "12"));
        summaries.add(new Summary("Tax", "142"));
        summaries.add(new Summary("Numa", "121"));
        summaries.add(new Summary("Price", "12"));
        summaries.add(new Summary("Tax", "142"));
        summaries.add(new Summary("Numa", "121"));
        return summaries;
    }

}
