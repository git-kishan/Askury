package com.droid.solver.askapp.homeAnswer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.text.emoji.widget.EmojiTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.droid.solver.askapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class QuestionViewHolder extends RecyclerView.ViewHolder {

    CircleImageView profileImage;
    EmojiTextView question,askerName,askerBio;
    ImageView wantToAnswerImage;
    TextView timeAgo;
    private Context context;
    public QuestionViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        profileImage=itemView.findViewById(R.id.circleImageView2);
        askerName=itemView.findViewById(R.id.textView15);
        askerBio=itemView.findViewById(R.id.textView16);
        question=itemView.findViewById(R.id.textView19);
        wantToAnswerImage=itemView.findViewById(R.id.imageView18);
        timeAgo=itemView.findViewById(R.id.textView28);
        this.context=context;

    }
}
