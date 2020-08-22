package com.net.mine.client;


import com.net.mine.gson.Data;
import com.net.mine.gson.Info;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    @GET("api/rand.music")
    Call<Data<Info>>getJsonData(@Query("sort")String sort, @Query("format")String format);
}
