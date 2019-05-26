package com.droid.solver.askapp.Survey;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.droid.solver.askapp.R;

import java.util.ArrayList;

public class SurveyOptionProviderRecyclerAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<Integer> list;
    private LayoutInflater inflater;

    SurveyOptionProviderRecyclerAdapter(Context context,ArrayList<Integer> list){
        this.context = context;
        this.list=list;
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view=inflater.inflate(R.layout.survey_options_item, parent,false);
        return new SurveyOptionProviderRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof SurveyOptionProviderRecyclerViewHolder){

        }
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }
}
