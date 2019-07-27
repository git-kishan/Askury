package com.droid.solver.askapp.homeAnswer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.emoji.widget.EmojiTextView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.droid.solver.askapp.Account.OtherAccount.OtherAccountActivity;
import com.droid.solver.askapp.Answer.AnswerActivity;
import com.droid.solver.askapp.R;
import de.hdodenhof.circleimageview.CircleImageView;

 class QuestionViewHolder extends RecyclerView.ViewHolder {

    CircleImageView profileImage;
    EmojiTextView question,askerName,askerBio;
    ImageView wantToAnswerImage;
    TextView timeAgo;
     QuestionViewHolder(@NonNull View itemView) {
        super(itemView);
        profileImage=itemView.findViewById(R.id.circleImageView2);
        askerName=itemView.findViewById(R.id.textView15);
        askerBio=itemView.findViewById(R.id.textView16);
        question=itemView.findViewById(R.id.textView1);
        wantToAnswerImage=itemView.findViewById(R.id.imageView18);
        timeAgo=itemView.findViewById(R.id.textView28);


    }
    void onWantToAnswerClicked(final Context context, final QuestionModel model){
        Activity activity=(Activity) context;
        Intent intent=new Intent(context, AnswerActivity.class);
        intent.putExtra("askerUid", model.getAskerId());
        intent.putExtra("questionId", model.getQuestionId());
        intent.putExtra("question", model.getQuestion());
        intent.putExtra("timeOfAsking", model.getTimeOfAsking());
        intent.putExtra("askerName", model.getAskerName());
        intent.putExtra("askerBio", model.getAskerBio());
        context.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);

    }

    void onProfilePicClicked(final Context context,final QuestionModel model){
         Activity activity=(Activity)context;
         if(model.isAnonymous()){
             Toast.makeText(context, "Anonymous user",Toast.LENGTH_LONG).show();
         }else {
             Intent intent=new Intent(context, OtherAccountActivity.class);
             intent.putExtra("profile_image",model.getAskerImageUrlLow());
             intent.putExtra("uid", model.getAskerId());
             intent.putExtra("user_name", model.getAskerName());
             intent.putExtra("bio", model.getAskerBio());
             context.startActivity(intent);
             activity.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
         }

    }
}
