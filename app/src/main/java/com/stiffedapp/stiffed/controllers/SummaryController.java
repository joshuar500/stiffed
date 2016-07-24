package com.stiffedapp.stiffed.controllers;

import com.stiffedapp.stiffed.api.StiffedApi;
import com.stiffedapp.stiffed.net.ServiceGenerator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class SummaryController {
    private StiffedApi stiffedApi;

    public String sName;
    public String sNumber;


    public SummaryController(String sName, String sNumber){
        this.sName = sName;
        this.sNumber = sNumber;
        this.stiffedApi = ServiceGenerator.createService(StiffedApi.class);
    }

    public static ArrayList<SummaryController> getSummary(String uid){

        RequestBody body = null;

        try {
            body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(uid)).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(body == null){
            //return null;
        }

        final ArrayList<SummaryController> summaries = new ArrayList<>();
        summaries.add(new SummaryController("TIPS THIS WEEK", "123"));
        summaries.add(new SummaryController("INCOME THIS WEEK", "142"));
        summaries.add(new SummaryController("TIP OUTS THIS WEEK", "121"));
        summaries.add(new SummaryController("TOTALS THIS WEEK", "12"));
        return summaries;
    }

}
