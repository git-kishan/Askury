package com.droid.solver.askapp.Account;

import android.content.Context;
import android.content.Intent;
import android.support.text.emoji.widget.EmojiTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.droid.solver.askapp.Answer.UserAnswerModel;
import com.droid.solver.askapp.R;
import com.droid.solver.askapp.homeAnswer.AnswerActivity;
import com.like.LikeButton;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountQuestionAnswerRecyclerViewHolder extends RecyclerView.ViewHolder {

    CircleImageView askerImageView,answererImageView;
    EmojiTextView askerName,askerBio,questionTextView,answerTextView;
    CardView rootCardView;
    LikeButton likeButton;
    public AccountQuestionAnswerRecyclerViewHolder(View itemView){
        super(itemView);
        askerImageView=itemView.findViewById(R.id.profile_image);
        answererImageView=itemView.findViewById(R.id.circleImageView);
        askerName=itemView.findViewById(R.id.asker_name);
        askerBio=itemView.findViewById(R.id.about_textview);
        questionTextView=itemView.findViewById(R.id.question_textview);
        answerTextView=itemView.findViewById(R.id.answer_text_view);
        rootCardView=itemView.findViewById(R.id.root_card_view);
        likeButton=itemView.findViewById(R.id.likeButton);

    }
     void onAskerImageClicked(final Context context, final UserAnswerModel model){
        onClicked("Asker Image", context);
    }
     void onAnswererImageClicked(final Context context,final UserAnswerModel model){
        onClicked("Answerer Image ", context);
    }
     void onQuestionClicked(final Context context,final UserAnswerModel model){
        goToHomeAnswerActivity(context, model);
    }
     void onAnswerClicked(final Context context,final UserAnswerModel model){
        goToHomeAnswerActivity(context, model);
    }
     void onAskerNameClicked(final Context context ,final UserAnswerModel model){
        onClicked("Asker Name ", context);
    }
    void onLikedClicked(final Context context,final UserAnswerModel model){
        onClicked("Like", context);

    }
    void onDislikedClicked(final Context context,final UserAnswerModel model){
        onClicked("Dislike", context);
    }
    private void goToHomeAnswerActivity(final Context context,final UserAnswerModel model){
        Intent intent=new Intent(context, AnswerActivity.class);
        intent.putExtra("askerImageUrl", model.getAskerImageUrl());
        intent.putExtra("timeOfAsking",model.getTimeOfAsking() );
        intent.putExtra("askerName", model.getAskerName());
        intent.putExtra("askerBio", model.getAskerBio());
        intent.putExtra("questionId", model.getQuestionId());
        intent.putExtra("question", model.getQuestion());
        intent.putExtra("askerId",model.getAskerId());
        intent.putExtra("anonymous", model.isAnonymous());
        context.startActivity(intent);

    }
    private void onClicked(final String message,final Context context){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
