package com.net.mine;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.internal.operators.observable.ObservableScalarXMap;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServiceManager {
    private static final int DEFAULT_TIME_OUT = 5;//超时时间 5s
    private static final int DEFAULT_READ_TIME_OUT = 10;
    private Retrofit retrofit;
    private  static RetrofitServiceManager manager;
    private RetrofitServiceManager(){
        //创建OkHttpBuilder
        OkHttpClient.Builder builder=new OkHttpClient.Builder()
                .writeTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request=chain.request();
                request=request.newBuilder()
                        .addHeader("","")
                        .build();
                /**
                 * OkHttp拦截器
                 * */
                return chain.proceed(request);
            }
        });
        retrofit=new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("")
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }
    public static RetrofitServiceManager getInstance(){
        if (manager==null){
            synchronized (RetrofitServiceManager.class){
                //注意:在任何时候,最多允许一个线程拥有同步锁,谁拿到锁就进入代码块,其他的线程只能在外等着.
                if (manager==null){
                    manager=new RetrofitServiceManager();
                }
            }
        }
        return manager;
    }
}
