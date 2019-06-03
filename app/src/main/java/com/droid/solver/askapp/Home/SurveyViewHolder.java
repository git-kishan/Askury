package com.droid.solver.askapp.Home;

import android.support.annotation.NonNull;
import android.support.text.emoji.widget.EmojiTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.droid.solver.askapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class SurveyViewHolder extends RecyclerView.ViewHolder {
    EmojiTextView profileName,bio,question;
    CircleImageView profileImage;
    ImageView threeDot;
    LinearLayout container1,container2,container3,container4;
    EmojiTextView option1TextView,option2TextView,option3TextView,option4TextView;

    public SurveyViewHolder(@NonNull View itemView) {
        super(itemView);
        profileName=itemView.findViewById(R.id.asker_name);
        bio=itemView.findViewById(R.id.about_textview);
        question=itemView.findViewById(R.id.question_textview);
        profileImage=itemView.findViewById(R.id.profile_image);
        threeDot=itemView.findViewById(R.id.imageView8);
        container1=itemView.findViewById(R.id.linearLayout3);
        container2=itemView.findViewById(R.id.linearLayout4);
        container3=itemView.findViewById(R.id.linearLayout5);
        container4=itemView.findViewById(R.id.linearLayout6);
        option1TextView=itemView.findViewById(R.id.option1);
        option2TextView=itemView.findViewById(R.id.option2);
        option3TextView=itemView.findViewById(R.id.option3);
        option4TextView=itemView.findViewById(R.id.option4);



    }
}
