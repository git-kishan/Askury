package com.droid.solver.askapp.Home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.droid.solver.askapp.Community.CommunityModel;

import java.util.ArrayList;

public class HorizontalRecyclerViewAdapter extends RecyclerView.Adapter {

    private ArrayList<Object> list;
    private Context context;
    private LayoutInflater inflater;

    HorizontalRecyclerViewAdapter(Context context,ArrayList<Object > list){
        this.context=context;
        this.list=list;
        inflater = LayoutInflater.from(context);

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {


    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }
}
