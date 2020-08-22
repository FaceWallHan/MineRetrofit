package com.net.mine.client;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface NetClient {

//    @GET()
//    Call<ResponseBody> getResource(@Url String url,@QueryMap Map<String,Object> map );
     /**GET请求*/
    //如果有@Url注解时，GET传入的Url可以省略。

    @FormUrlEncoded
    @POST
    Call<ResponseBody> getResource(@Url String url,@FieldMap() Map<String,Object> map );
    /**POST请求*/
}
