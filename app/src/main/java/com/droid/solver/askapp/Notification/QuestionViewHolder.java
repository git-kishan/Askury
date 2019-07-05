package com.droid.solver.askapp.Notification;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.droid.solver.askapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class QuestionViewHolder extends RecyclerView.ViewHolder {

    CardView rootCardView;
    CircleImageView likerImage,dot;
    TextView timeAgo,statusTextView,likes;
    public QuestionViewHolder(@NonNull View itemView) {
        super(itemView);
        rootCardView=itemView.findViewById(R.id.root_card_view);
        likerImage=itemView.findViewById(R.id.liker_image);
        timeAgo=itemView.findViewById(R.id.time_ago);
        statusTextView=itemView.findViewById(R.id.text_view1);
        likes=itemView.findViewById(R.id.text_view2);
        dot=itemView.findViewById(R.id.dot);
    }
}
