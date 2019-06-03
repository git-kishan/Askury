package com.droid.solver.askapp.Question;

import android.content.Context;
import android.support.text.emoji.widget.EmojiTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.droid.solver.askapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class AskQuestionViewHolderWithImage extends RecyclerView.ViewHolder{
    ImageView shareImage,questionImage;
    CircleImageView profilePicture;
    EmojiTextView profileName,about,tapToMore;
    EmojiTextView question,timeAgo;
    CardView cardView;
    public AskQuestionViewHolderWithImage(View itemView, Context context){
        super(itemView);
        cardView=itemView.findViewById(R.id.root_card_view);
        shareImage=itemView.findViewById(R.id.share_image_view);
        questionImage = itemView.findViewById(R.id.question_image);
        profilePicture=itemView.findViewById(R.id.profile_image);
        profileName=itemView.findViewById(R.id.asker_name);
        about=itemView.findViewById(R.id.about_textview);
        tapToMore=itemView.findViewById(R.id.tap_to_more_textview);
        question=itemView.findViewById(R.id.question_textview);
        timeAgo=itemView.findViewById(R.id.time_ago_textview);

    }


}
