package com.stiffedapp.stiffed.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.stiffedapp.stiffed.MainActivity;
import com.stiffedapp.stiffed.SummaryFragment;
import com.stiffedapp.stiffed.api.StiffedApi;
import com.stiffedapp.stiffed.beans.Tip;
import com.stiffedapp.stiffed.beans.User;
import com.stiffedapp.stiffed.net.ServiceGenerator;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TipController {

    private StiffedApi stiffedApi;

    public TipController() {
        this.stiffedApi = ServiceGenerator.createService(StiffedApi.class);
    }

    public void addNewTip(String userID, Double amount, String date, final MainActivity activity) {
        HashMap<String, Object> credentials = new HashMap<>();
        credentials.put("amount", amount);
        credentials.put("tip_date", date);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(credentials)).toString());

        Call<Tip> addTip = stiffedApi.addTip(userID, body);

        addTip.enqueue(new Callback<Tip>() {
            @Override
            public void onResponse(Call<Tip> call, Response<Tip> response) {

                // check if success
                if(response.code() == 200) {
                } else {
                    Toast.makeText(activity.getBaseContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Tip> call, Throwable t) {
                Toast.makeText(activity.getBaseContext(), "Please make sure you are connected to the Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void calculateTipsThisWeek(Tip tip){

    }

}
