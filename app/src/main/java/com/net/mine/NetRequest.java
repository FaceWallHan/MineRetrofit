package com.net.mine;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.animation.BaseInterpolator;

import com.net.mine.client.NetClient;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetRequest extends Thread{
    private ProgressDialog dialog;
    private boolean isLoop=false;
    private int time=0;
    private Map<String,Object> objectMap;
    private NetCall netCall;
    private String url;

    public NetRequest setUrl(String url) {
        this.url = url;
        return this;
    }

    public NetRequest() {
        objectMap=new HashMap<>();
    }
    public NetRequest showDialog(Context context) {
        dialog=new ProgressDialog(context);
        dialog.setMessage("loading...");
        dialog.setTitle("提示");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return this;
    }

    public NetRequest setLoop(boolean loop,int time) {
        isLoop = loop;
        this.time = time;
        return this;
    }
    public NetRequest addValue(String key,Object value){
        objectMap.put(key,value);
        return this;
    }

    public NetRequest setNetCall(NetCall netCall) {
        this.netCall = netCall;
        return this;
    }

    @Override
    public void run() {
        do {
            OkHttpClient.Builder builder=new OkHttpClient.Builder()
//                    .writeTimeout(5, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .connectTimeout(5, TimeUnit.SECONDS);
            builder.addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request request=chain.request();
                    request=request.newBuilder()
                            .addHeader("","")
                            .build();
                    /**
                     * OkHttp拦截器
                     * */
                    okhttp3.Response response=chain.proceed(request);
                    return response;
                }
            });
            Retrofit retrofit=new Retrofit.Builder()
                    .baseUrl("https://api.uomg.com/api/")//只有这里地址加上“/”
                    //.addConverterFactory(GsonConverterFactory.create())
                    .client(builder.build())
                    .build();
            NetClient client=retrofit.create(NetClient.class);
            Call<ResponseBody> call=client.getResource(url,objectMap);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    String result = null;
                    try {
                        result = response.body().string();
                        JSONObject jsonObject=new JSONObject(result);
                        netCall.onSuccess(jsonObject);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (dialog!=null&&dialog.isShowing()){
                        dialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    netCall.onFailure(t);
                    if (dialog!=null&&dialog.isShowing()){
                        dialog.dismiss();
                    }
                }
            });
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }while (isLoop);
    }
}
