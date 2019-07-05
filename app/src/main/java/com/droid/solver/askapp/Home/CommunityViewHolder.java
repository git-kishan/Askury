package com.droid.solver.askapp.Home;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.droid.solver.askapp.R;

public class CommunityViewHolder extends RecyclerView.ViewHolder {
    CardView cardView;
    public CommunityViewHolder(@NonNull View itemView) {
        super(itemView);
        cardView=itemView.findViewById(R.id.root_card_view);
    }
}
