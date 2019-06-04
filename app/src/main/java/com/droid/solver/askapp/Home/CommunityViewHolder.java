package com.droid.solver.askapp.Home;

import android.content.ReceiverCallNotAllowedException;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.droid.solver.askapp.R;

public class CommunityViewHolder extends RecyclerView.ViewHolder {
    CardView cardView;
    public CommunityViewHolder(@NonNull View itemView) {
        super(itemView);
        cardView=itemView.findViewById(R.id.root_card_view);
    }
}
