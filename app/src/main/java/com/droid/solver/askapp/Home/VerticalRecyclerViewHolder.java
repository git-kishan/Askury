package com.droid.solver.askapp.Home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.droid.solver.askapp.R;

import java.util.ArrayList;

public class VerticalRecyclerViewHolder extends RecyclerView.ViewHolder {

    RecyclerView recyclerView;
    Context context;
    public VerticalRecyclerViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context=context;
        recyclerView=itemView.findViewById(R.id.recycler_view);

    }

}
