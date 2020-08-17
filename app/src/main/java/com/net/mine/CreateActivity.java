package com.net.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.net.mine.gson.User;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create);
        EditText input_name=findViewById(R.id.input_name);
        EditText input_email=findViewById(R.id.input_email);
        EditText input_age=findViewById(R.id.input_age);
        EditText input_topics=findViewById(R.id.input_topics);
        Button btn_sign=findViewById(R.id.btn_sign);
        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user=new User(
                        input_name.getText().toString().trim(),
                        input_email.getText().toString().trim(),
                        Integer.parseInt(input_age.getText().toString().trim()),
                        input_topics.getText().toString().trim().split(",")
                );
                sendNetWorkRequest(user);
            }
        });
    }
    private void sendNetWorkRequest(User user){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
