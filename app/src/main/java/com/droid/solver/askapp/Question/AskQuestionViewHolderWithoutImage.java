package com.droid.solver.askapp.Question;

import android.content.Context;
import android.content.Intent;
import android.support.text.emoji.widget.EmojiTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.droid.solver.askapp.Answer.AnswerActivity;
import com.droid.solver.askapp.R;

import java.util.ArrayList;
import java.util.PriorityQueue;

import de.hdodenhof.circleimageview.CircleImageView;

public class AskQuestionViewHolderWithoutImage extends RecyclerView.ViewHolder {

    ImageView shareImage;
    CircleImageView profilePicture;
    EmojiTextView profileName,about,tapToMore;
    EmojiTextView question,timeAgo;
    CardView cardView;
    View view;
    public AskQuestionViewHolderWithoutImage(View itemView, Context context){
        super(itemView);
        cardView=itemView.findViewById(R.id.root_card_view);
        shareImage=itemView.findViewById(R.id.share_image_view);
        profilePicture = itemView.findViewById(R.id.profile_image);
        profileName=itemView.findViewById(R.id.asker_name);
        about=itemView.findViewById(R.id.about_textview);
        timeAgo=itemView.findViewById(R.id.time_ago_textview);
        question=itemView.findViewById(R.id.question_textview);
        tapToMore=itemView.findViewById(R.id.tap_to_more_textview);
        view=itemView.findViewById(R.id.view9);


    }
     void onCardClicked(Context context,int i,
                              String askerUid,String questionId,String question,long timeOfAsking,
                              String askerName,String askerImageUrl,String askerBio,ArrayList<String > questionType){

                    Intent intent=new Intent(context,AnswerActivity.class);
                    intent.putExtra("askerUid",askerUid);
                    intent.putExtra("questionId",questionId);
                    intent.putExtra("question", question);
                    intent.putExtra("timeOfAsking", timeOfAsking);
                    intent.putExtra("askerName",askerName);
                    intent.putExtra("askerImageUrl", askerImageUrl);
                    intent.putExtra("askerBio", askerBio);
                    intent.putStringArrayListExtra("questionType",questionType);
                    context.startActivity(intent);
    }


}
