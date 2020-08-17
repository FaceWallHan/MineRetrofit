package com.net.mine.client;

import com.net.mine.gson.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserClient {
    @POST("user")
    Call<User> createAccount(@Body User user);
}
