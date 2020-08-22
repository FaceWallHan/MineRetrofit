package com.net.mine.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.net.mine.R;
import com.net.mine.gson.GitHubResponse;

import java.util.List;

public class ResponseAdapter extends ArrayAdapter<GitHubResponse> {
    private List<GitHubResponse>list;
    public ResponseAdapter(@NonNull Context context, List<GitHubResponse>list) {
        super(context, 0,list);
        this.list=list;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.response_item,parent,false);
        TextView response_text=convertView.findViewById(R.id.response_text);
        response_text.setText(list.get(position).getName());
        return convertView;
    }

    @Nullable
    @Override
    public GitHubResponse getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
