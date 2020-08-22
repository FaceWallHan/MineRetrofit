package com.net.mine.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.net.mine.NetCall;
import com.net.mine.NetRequest;
import com.net.mine.R;
import com.net.mine.client.Api;
import com.net.mine.gson.Data;
import com.net.mine.gson.Info;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MusicActivity extends AppCompatActivity {
    private Button get_request,post_request;
    private TextView music_text;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_layout);
        inView();
        post_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetRequest request=new NetRequest();
                request.addValue("format","json")
                        .showDialog(MusicActivity.this)
                        .setLoop(true,3000)
                        .setUrl("rand.qinghua")
                        .setNetCall(new NetCall() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        try {
                            music_text.setText(jsonObject.getString("content"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                }).start();
            }
        });

        get_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit=new Retrofit.Builder()
                        //地址必须以“/”结尾，否则会报错
                        .baseUrl("https://api.uomg.com/")
                        //设置数据解析器
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                //创建网络请求接口对象实例
                Api api=retrofit.create(Api.class);
                //对发送请求进行封装
                Call<Data<Info>>dataCall=api.getJsonData("新歌榜","json");
                //异步请求
                dataCall.enqueue(new Callback<Data<Info>>() {
                    @Override
                    public void onResponse(Call<Data<Info>> call, Response<Data<Info>> response) {
                        Toast.makeText(MusicActivity.this, "get回调成功：异步执行", Toast.LENGTH_SHORT).show();
                        Data<Info> body =response.body();
                        if (body==null)return;
                        Info info=body.getData();
                        if (info==null)return;
                        String text="返回的数据："+"\n\n"+info.getName()+"\n"+info.getPicUrl()+"\n"+info.getUrl();
                        music_text.setText(text);
                    }

                    @Override
                    public void onFailure(Call<Data<Info>> call, Throwable t) {
                        Toast.makeText(MusicActivity.this, "回调失败:(", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        /**
        try {
            //同步请求
            Response<Data<Info>> data =dataCall.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
         **/
    }
    private void inView(){
        get_request=findViewById(R.id.get_request);
        post_request=findViewById(R.id.post_request);
        music_text=findViewById(R.id.music_text);
    }
}
