package com.droid.solver.askapp.Account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.emoji.widget.EmojiTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import com.droid.solver.askapp.Answer.UserAnswerModel;
import com.droid.solver.askapp.Main.Constants;
import com.droid.solver.askapp.R;
import com.droid.solver.askapp.homeAnswer.AnswerActivity;
import com.like.LikeButton;

import de.hdodenhof.circleimageview.CircleImageView;

 class AccountQuestionAnswerRecyclerViewHolder extends RecyclerView.ViewHolder {

    CircleImageView askerImageView,answererImageView;
    EmojiTextView askerName,askerBio,questionTextView,answerTextView;
    LikeButton likeButton;

     AccountQuestionAnswerRecyclerViewHolder(View itemView){
        super(itemView);
        askerImageView=itemView.findViewById(R.id.profile_image);
        answererImageView=itemView.findViewById(R.id.circleImageView);
        askerName=itemView.findViewById(R.id.asker_name);
        askerBio=itemView.findViewById(R.id.about_textview);
        questionTextView=itemView.findViewById(R.id.question_textview);
        answerTextView=itemView.findViewById(R.id.answer_text_view);
        CardView rootCardView=itemView.findViewById(R.id.root_card_view);
        likeButton=itemView.findViewById(R.id.likeButton);

    }
     void onAskerImageClicked(final Context context, final UserAnswerModel model){
        Intent intent=new Intent(context,OtherAccountActivity.class);
        intent.putExtra("profile_image", model.getAskerImageUrl());
        intent.putExtra("uid", model.getAskerId());
        intent.putExtra("user_name", model.getAskerName());
        intent.putExtra("bio", model.getAskerBio());
        context.startActivity(intent);
    }

     void onAnswererImageClicked(final Context context,final UserAnswerModel model){
         SharedPreferences preferences=context.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
         Intent intent=new Intent(context,OtherAccountActivity.class);
         intent.putExtra("profile_image", model.getAnswerId());
         intent.putExtra("uid", model.getAnswererId());
         intent.putExtra("user_name", preferences.getString(Constants.userName, "Someone"));
         intent.putExtra("bio", preferences.getString(Constants.bio, ""));
         context.startActivity(intent);
    }

     void onQuestionClicked(final Context context,final UserAnswerModel model){
        goToHomeAnswerActivity(context, model);
    }

     void onAnswerClicked(final Context context,final UserAnswerModel model){
        goToHomeAnswerActivity(context, model);
    }

     void onAskerNameClicked(final Context context ,final UserAnswerModel model){
         Intent intent=new Intent(context,OtherAccountActivity.class);
         intent.putExtra("profile_image", model.getAskerImageUrl());
         intent.putExtra("uid", model.getAskerId());
         intent.putExtra("user_name", model.getAskerName());
         intent.putExtra("bio", model.getAskerBio());
         context.startActivity(intent);
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
