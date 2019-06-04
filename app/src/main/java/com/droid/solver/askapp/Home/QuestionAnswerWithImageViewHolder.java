package com.droid.solver.askapp.Home;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.text.emoji.widget.EmojiTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.droid.solver.askapp.R;
import com.like.LikeButton;

import de.hdodenhof.circleimageview.CircleImageView;

public class QuestionAnswerWithImageViewHolder extends RecyclerView.ViewHolder {
    ImageView answerImageView;
    CircleImageView askerImageView,answererImageView;
    EmojiTextView askerName,askerBio,answererName;
    TextView timeAgo,wantToAnswerTextView;
    EmojiTextView question,answer;
    ImageView threeDot;
    LikeButton likeButton;
    TextView likeCount,answerCount;
    ImageView numberOfAnswerImageView,wantToAnswerImageView;
    public QuestionAnswerWithImageViewHolder(@NonNull View itemView) {
        super(itemView);
        askerImageView=itemView.findViewById(R.id.profile_image);
        answererImageView=itemView.findViewById(R.id.circleImageView);
        askerName=itemView.findViewById(R.id.asker_name);
        askerBio=itemView.findViewById(R.id.about_textview);
        answererName=itemView.findViewById(R.id.answerer_name);
        timeAgo=itemView.findViewById(R.id.time_ago_textview);
        answerImageView=itemView.findViewById(R.id.answer_image_view);
        wantToAnswerTextView=itemView.findViewById(R.id.textView18);
        question=itemView.findViewById(R.id.question_textview);
        answer=itemView.findViewById(R.id.answer_text_view);
        threeDot=itemView.findViewById(R.id.imageView4);
        likeButton=itemView.findViewById(R.id.likeButton);
        likeCount=itemView.findViewById(R.id.textView16);
        answerCount=itemView.findViewById(R.id.textView17);
        numberOfAnswerImageView=itemView.findViewById(R.id.imageView2);
        wantToAnswerImageView=itemView.findViewById(R.id.imageView3);
    }
}
