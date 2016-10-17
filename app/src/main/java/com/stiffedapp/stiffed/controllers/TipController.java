package com.stiffedapp.stiffed.controllers;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.stiffedapp.stiffed.FeedFragment;
import com.stiffedapp.stiffed.MainActivity;
import com.stiffedapp.stiffed.R;
import com.stiffedapp.stiffed.SummaryFragment;
import com.stiffedapp.stiffed.api.StiffedApi;
import com.stiffedapp.stiffed.beans.Tip;
import com.stiffedapp.stiffed.beans.Tips;
import com.stiffedapp.stiffed.beans.User;
import com.stiffedapp.stiffed.net.ServiceGenerator;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TipController {

    private StiffedApi stiffedApi;

    private static final String LOG_TAG = TipController.class.getSimpleName();

    public TipController() {
        this.stiffedApi = ServiceGenerator.createService(StiffedApi.class);
    }

    public void addNewTip(String userID, final Double amount, String date, final MainActivity activity) {
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
                    Toast.makeText(activity.getBaseContext(), "Tip Added: " + amount, Toast.LENGTH_LONG).show();
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
}
