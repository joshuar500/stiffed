package com.stiffedapp.stiffed.api;

import com.stiffedapp.stiffed.beans.User;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface StiffedApi {
    @GET("/users/{email}")
    Call<User> getUser(@Path("email") String email);

    @POST("/v1/login")
    Call<User> login(@Body RequestBody params);

    @GET("/v1/tips/{uid}/weekly")
    Call<User> weeklyTips(@Path("uid") String uid);
}