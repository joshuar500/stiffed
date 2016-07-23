package com.stiffedapp.stiffed.api;

import com.stiffedapp.stiffed.models.User;

import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface StiffedApi {
    @GET("/users/{email}")
    Call<User> getUser(@Path("email") String email);

    @POST("/v1/login")
    Call<User> login(@Body RequestBody params);
}
