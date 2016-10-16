package com.stiffedapp.stiffed.api;

import com.stiffedapp.stiffed.beans.Tip;
import com.stiffedapp.stiffed.beans.Tips;
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

    @GET("/v1/{uid}/tips/weekly")
    Call<Tips> weeklyTips(@Path("uid") String uid);

    @GET("/v1/{uid}/tips/all")
    Call<Tips> allTips(@Path("uid") String uid);

    @POST("/v1/{uid}/tips/add")
    Call<Tip> addTip(@Path("uid") String uid, @Body RequestBody params);

    @POST("/v1/{uid}/tips/bydate")
    Call<Tips> getTipsByDate(@Path("uid") String uid, @Body RequestBody params);
}