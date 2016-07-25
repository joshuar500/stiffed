package com.stiffedapp.stiffed.controllers;

import com.stiffedapp.stiffed.api.StiffedApi;
import com.stiffedapp.stiffed.beans.Tip;
import com.stiffedapp.stiffed.beans.Tips;
import com.stiffedapp.stiffed.beans.User;
import com.stiffedapp.stiffed.net.ServiceGenerator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SummaryController {
    public String sName;
    public Double sNumber;


    public SummaryController(String sName, Double sNumber){
        this.sName = sName;
        this.sNumber = sNumber;
    }

    public static ArrayList<SummaryController> getSummary(final String uid, final String authToken){
        final ArrayList<SummaryController> summaries = new ArrayList<>();

        StiffedApi stiffedApi = ServiceGenerator.createService(StiffedApi.class, authToken);
        Call<Tips> weeklyTips = stiffedApi.weeklyTips(uid);
        weeklyTips.enqueue(new Callback<Tips>() {
            @Override
            public void onResponse(Call<Tips> call, Response<Tips> response) {

                List<Tip> tips = response.body().getTips();
                Double addedTips = 0.0;
                for(Tip tip : tips){
                    addedTips += tip.getAmount();
                }
                if (addedTips != null) {
                    summaries.add(new SummaryController("TIPS THIS WEEK", addedTips));
                }
                summaries.add(new SummaryController("INCOME THIS WEEK", 522.08));
                summaries.add(new SummaryController("TIP OUTS THIS WEEK", 142.08));
                summaries.add(new SummaryController("TOTALS THIS WEEK", 123.412));
            }

            @Override
            public void onFailure(Call<Tips> call, Throwable t) {

            }
        });
        return summaries;
    }

}
