package com.stiffedapp.stiffed.controllers;


import android.widget.Toast;

import com.stiffedapp.stiffed.MainActivity;
import com.stiffedapp.stiffed.api.StiffedApi;
import com.stiffedapp.stiffed.beans.Tip;
import com.stiffedapp.stiffed.beans.Tips;
import com.stiffedapp.stiffed.net.ServiceGenerator;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EarningsController {
    private StiffedApi stiffedApi;

    private static final String LOG_TAG = EarningsController.class.getSimpleName();

    public EarningsController() {
        this.stiffedApi = ServiceGenerator.createService(StiffedApi.class);
    }

    public void getEarnings(String userID, final Double amount, String date, final MainActivity activity) {

        Call<Tips> getEarningsCall = stiffedApi.getEarnings(userID);

        getEarningsCall.enqueue(new Callback<Tips>() {
            @Override
            public void onResponse(Call<Tips> call, Response<Tips> response) {

                // check if success
                if(response.code() != 200) {
                    Toast.makeText(activity.getBaseContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Tips> call, Throwable t) {
                Toast.makeText(activity.getBaseContext(), "Please make sure you are connected to the Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
