package com.droid.solver.askapp.Home;

import android.support.annotation.NonNull;
import android.support.text.emoji.widget.EmojiTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.droid.solver.askapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ImagePollViewHolder extends RecyclerView.ViewHolder {
    EmojiTextView profileName,bio,question;
    CircleImageView profileImageView;
    TextView timeAgo,text1,text2;
    RelativeLayout view1,view2;
    ImageView image1,image2,leftWhiteHeart,rightWhiteHeart,leftRedHeart,rightRedHeart,threeDot;
    public ImagePollViewHolder(@NonNull View itemView) {
        super(itemView);
        profileImageView=itemView.findViewById(R.id.profile_image);
        profileName=itemView.findViewById(R.id.asker_name);
        bio=itemView.findViewById(R.id.about_textview);
        question=itemView.findViewById(R.id.question_textview);
        timeAgo=itemView.findViewById(R.id.time_ago_textview);
        threeDot=itemView.findViewById(R.id.imageView4);
        rightRedHeart=itemView.findViewById(R.id.imageView12);
        leftRedHeart=itemView.findViewById(R.id.imageView11);
        rightWhiteHeart=itemView.findViewById(R.id.imageView9);
        leftWhiteHeart=itemView.findViewById(R.id.imageView7);
        text1=itemView.findViewById(R.id.text1);
        text2=itemView.findViewById(R.id.text2);
        view1=itemView.findViewById(R.id.view1);
        view2=itemView.findViewById(R.id.view2);
        image1=itemView.findViewById(R.id.imageView5);
        image2=itemView.findViewById(R.id.imageView6);
    }
}
