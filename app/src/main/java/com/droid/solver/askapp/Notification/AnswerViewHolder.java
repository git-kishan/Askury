package com.droid.solver.askapp.Notification;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.droid.solver.askapp.Account.OtherAccount.OtherAccountActivity;
import com.droid.solver.askapp.Main.LocalDatabase;
import com.droid.solver.askapp.Main.MainActivity;
import com.droid.solver.askapp.R;

import de.hdodenhof.circleimageview.CircleImageView;
 class AnswerViewHolder extends RecyclerView.ViewHolder {

     CardView rootCardView;
     CircleImageView profileImage;
     TextView status,timeAgo;
     FrameLayout frameLayout;
     AnswerViewHolder(@NonNull View itemView) {
        super(itemView);
        rootCardView=itemView.findViewById(R.id.root_card);
        timeAgo=itemView.findViewById(R.id.time_ago);
        profileImage=itemView.findViewById(R.id.liker_image);
        status=itemView.findViewById(R.id.text_view1);
        frameLayout=itemView.findViewById(R.id.frameLayout);

    }

    void onCardClicked(final Context context,final AnswerModel model){
        Activity activity= (Activity) context;
        Intent intent=new Intent(context, NotificationAnswerActivity.class);
        intent.putExtra("isStoredLocally", model.isStoredLocally());
        if(!model.isStoredLocally()){
            model.setStoredLocally(true);
            saveNotificationToLocalDatabase(model.getAnswerId(), model, context);
        }
         boolean isLikedByMe=false;
         int likeCount=0;
        if(MainActivity.answerLikeList!=null&&MainActivity.answerLikeList.size()>0){
            if(MainActivity.answerLikeList.contains(model.getAnswerId())){
                isLikedByMe=true;
                likeCount=1;
            }
        }
        intent.putExtra("askerId", model.getAskerId());
        intent.putExtra("answerId",model.getAnswerId() );
        intent.putExtra("answererId", model.getAnswererId());
        intent.putExtra("askerName", model.getAskerName());
        intent.putExtra("askerBio", model.getAskerBio());
        intent.putExtra("answererName", model.getAnswererName());
        intent.putExtra("answererBio", model.getAnswererBio());
        intent.putExtra("timeOfAsking",model.getTimeOfAnswering());
        intent.putExtra("timeOfAnswering", model.getTimeOfAnswering());
        intent.putExtra("question",model.getQuestion());
        intent.putExtra("answer", model.getAnswer());
        intent.putExtra("anonymous", model.isAnonymous());
        intent.putExtra("fontUsed", model.getFontUsed());
        intent.putExtra("isLikedByMe", isLikedByMe);
        intent.putExtra("likeCount", likeCount);
        intent.putExtra("imageAttached", model.isImageAttached());
        intent.putExtra("imageAttachedUrl", model.getImageAttachedUrl());
        frameLayout.setVisibility(View.GONE);
        context.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);


    }

    void onProfilePicClicked(final Context context,final AnswerModel model){
         Activity activity=(Activity)context;
         Intent intent=new Intent(context, OtherAccountActivity.class);
         intent.putExtra("profile_image", model.getAnswererId());
         intent.putExtra("uid", model.getAnswererId());
         intent.putExtra("user_name", model.getAnswererName());
         intent.putExtra("bio", model.getAnswererBio());
         context.startActivity(intent);
         activity.overridePendingTransition(R.anim.slide_in_up,R.anim.slide_out_up);



    }

     private void saveNotificationToLocalDatabase(final String notificationId, final AnswerModel model, final Context context) {
             AsyncTask.execute(new Runnable() {
                 @Override
                 public void run() {
                     LocalDatabase db = new LocalDatabase(context.getApplicationContext());
                     db.insertNotification(notificationId, "answer");
                     db.insertNotificationAnswer(model);
                 }
             });

         }

}
