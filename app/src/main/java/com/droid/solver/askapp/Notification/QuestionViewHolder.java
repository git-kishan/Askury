package com.droid.solver.askapp.Notification;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import com.droid.solver.askapp.Main.LocalDatabase;
import com.droid.solver.askapp.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class QuestionViewHolder extends RecyclerView.ViewHolder {

    CardView rootCardView;
    CircleImageView likerImage,dot;
    TextView timeAgo,statusTextView,likes;

    public QuestionViewHolder(@NonNull View itemView) {
        super(itemView);
        rootCardView=itemView.findViewById(R.id.root_card);
        likerImage=itemView.findViewById(R.id.liker_image);
        timeAgo=itemView.findViewById(R.id.time_ago);
        statusTextView=itemView.findViewById(R.id.text_view1);
        likes=itemView.findViewById(R.id.text_view2);
        dot=itemView.findViewById(R.id.dot);
    }

     void onCardClicked(Context context, QuestionModel model){
         Intent intent=new Intent(context,NotificationQuestionActivity.class);
         intent.putExtra("isStoredLocally", model.isStoredLocally());
         if(!model.isStoredLocally()){
             model.setStoredLocally(true);
             saveNotificationToLocalDatabase(model.getAnswerId(), model.getType(), model, context);
         }
         intent.putExtra("likerId", model.getLikerId());
         intent.putExtra("likerName", model.getLikerName());
         intent.putExtra("likerImageUrl", model.getLikerImageUrl());
         intent.putExtra("likerBio", model.getLikerBio());
         intent.putExtra("askerId", model.getAskerId());
         intent.putExtra("answerId", model.getAnswerId());
         intent.putExtra("answererId", model.getAnswererId());
         intent.putExtra("questionId", model.getQuestionId());
         intent.putExtra("type", model.getType());
         intent.putExtra("notifiedTime", model.getNotifiedTime());
         dot.setVisibility(View.GONE);
         context.startActivity(intent);
    }

    private void saveNotificationToLocalDatabase(final String notificationId,final String type,final QuestionModel model,final Context context){

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                LocalDatabase db=new LocalDatabase(context.getApplicationContext());
                db.insertNotification(notificationId, type);
                db.insertNotificationQuestion(model);
            }
        });

    }


}
