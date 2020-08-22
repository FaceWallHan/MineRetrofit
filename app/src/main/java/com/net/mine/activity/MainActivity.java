package com.net.mine.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.net.mine.R;
import com.net.mine.client.GitHubClient;
import com.net.mine.gson.GitHubResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView pagination_list=findViewById(R.id.pagination_list);
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GitHubClient client=retrofit.create(GitHubClient.class);
        Call<List<GitHubResponse>> call=client.responseForUser("FaceWallHan");

        call.enqueue(new Callback<List<GitHubResponse>>() {
            @Override
            public void onResponse(Call<List<GitHubResponse>> call, Response<List<GitHubResponse>> response) {
                List<GitHubResponse>resp=response.body();
                pagination_list.setAdapter(new ResponseAdapter(MainActivity.this,resp));
            }

            @Override
            public void onFailure(Call<List<GitHubResponse>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "error :(", Toast.LENGTH_SHORT).show();
            }
        });
    }
}