package com.stiffedapp.stiffed.controllers;


import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.stiffedapp.stiffed.LoginActivity;
import com.stiffedapp.stiffed.api.StiffedApi;
import com.stiffedapp.stiffed.beans.User;
import com.stiffedapp.stiffed.net.ServiceGenerator;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginController {
    private StiffedApi stiffedApi;

    public LoginController() {
        this.stiffedApi = ServiceGenerator.createService(StiffedApi.class);
    }

    public void login(String email, String password, final LoginActivity activity) {
        HashMap<String, Object> credentials = new HashMap<>();
        credentials.put("email", email);
        credentials.put("password", password);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(credentials)).toString());

        Call<User> loggedIn = stiffedApi.login(body);

        loggedIn.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                activity.hideProgressDialog();

                // check if success
                if(response.code() == 200) {
                    activity.setResult(Activity.RESULT_OK, new Intent().putExtra("userid", response.body().getId()).putExtra("authtoken", response.body().getToken()));
                    activity.finish();
                } else {
                    Toast.makeText(activity.getBaseContext(), "Wrong username or password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                activity.hideProgressDialog();
                Toast.makeText(activity.getBaseContext(), "Please make sure you are connected to the Internet", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
