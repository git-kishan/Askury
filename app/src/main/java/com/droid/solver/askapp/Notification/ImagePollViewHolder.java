package com.droid.solver.askapp.Notification;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.droid.solver.askapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ImagePollViewHolder extends RecyclerView.ViewHolder {
    CardView rootCardView;
    CircleImageView image1,image2,dot;
    TextView timeAgo,statusTextView,percent1,percent2;
    public ImagePollViewHolder(@NonNull View itemView) {
        super(itemView);
        rootCardView=itemView.findViewById(R.id.root_card_view);
        image1=itemView.findViewById(R.id.image1);
        image2=itemView.findViewById(R.id.image2);
        timeAgo=itemView.findViewById(R.id.time_ago);
        statusTextView=itemView.findViewById(R.id.text_view1);
        percent1=itemView.findViewById(R.id.textViewa);
        percent2=itemView.findViewById(R.id.textViewb);
        dot=itemView.findViewById(R.id.dot);
    }
}
