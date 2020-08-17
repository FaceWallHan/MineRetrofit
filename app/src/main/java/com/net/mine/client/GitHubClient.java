package com.net.mine.client;

import com.net.mine.gson.GitHubResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubClient {
    @GET("users/{user}/repos")
    Call<List<GitHubResponse>> responseForUser(@Path("user") String user);
}
