package com.droid.solver.askapp.Notification;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.droid.solver.askapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class SurveyViewHolder extends RecyclerView.ViewHolder {
    CardView rootCardView;
    TextView timeAgo;
    TextView statusTextView;
    CircleImageView dot;
    public SurveyViewHolder(@NonNull View itemView) {
        super(itemView);
        rootCardView=itemView.findViewById(R.id.root_card);
        timeAgo=itemView.findViewById(R.id.time_ago);
        statusTextView=itemView.findViewById(R.id.text_view1);
        dot=itemView.findViewById(R.id.dot);
    }
}
