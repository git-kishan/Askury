package com.droid.solver.askapp.Account;

import android.view.View;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.emoji.widget.EmojiTextView;
import androidx.recyclerview.widget.RecyclerView;
import com.droid.solver.askapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class FollowingViewHolder extends RecyclerView.ViewHolder {
    EmojiTextView profileName;
    CircleImageView profileImage;
    TextView bio;
    CardView cardView;
    public FollowingViewHolder(View itemView){
        super(itemView);
        profileName=itemView.findViewById(R.id.textView48);
        bio=itemView.findViewById(R.id.textView49);
        profileImage=itemView.findViewById(R.id.circleImageView6);
        cardView=itemView.findViewById(R.id.card_view);

    }
}
