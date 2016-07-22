package com.stiffedapp.stiffed.controllers;

import java.util.ArrayList;

public class SummaryController {
    public String sName;
    public String sNumber;

    public SummaryController(String sName, String sNumber){
        this.sName = sName;
        this.sNumber = sNumber;
    }

    public static ArrayList<SummaryController> getSummary(){

        final ArrayList<SummaryController> summaries = new ArrayList<>();
        summaries.add(new SummaryController("TIPS THIS WEEK", "123"));
        summaries.add(new SummaryController("INCOME THIS WEEK", "142"));
        summaries.add(new SummaryController("TIP OUTS THIS WEEK", "121"));
        summaries.add(new SummaryController("TOTALS THIS WEEK", "12"));
        return summaries;
    }

}
