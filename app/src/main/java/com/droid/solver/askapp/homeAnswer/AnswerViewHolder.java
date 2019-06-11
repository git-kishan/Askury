package com.droid.solver.askapp.homeAnswer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.text.emoji.widget.EmojiTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.droid.solver.askapp.R;
import com.like.LikeButton;

import de.hdodenhof.circleimageview.CircleImageView;

public class AnswerViewHolder extends RecyclerView.ViewHolder {

    CircleImageView profileImage;
    EmojiTextView answererName,answererBio,answer;
    TextView likeCount,timeAgo;
    LikeButton likeButton;
    ImageView answerImage;
    private Context context;
    public AnswerViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        profileImage=itemView.findViewById(R.id.circleImageView2);
        answererName=itemView.findViewById(R.id.textView15);
        answererBio=itemView.findViewById(R.id.textView16);
        answer=itemView.findViewById(R.id.textView19);
        likeCount=itemView.findViewById(R.id.textView25);
        timeAgo=itemView.findViewById(R.id.textView27);
        likeButton=itemView.findViewById(R.id.likeButton);
        answerImage=itemView.findViewById(R.id.imageView19);
        this.context=context;

    }
}
