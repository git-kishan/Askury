package com.droid.solver.askapp.Notification;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import com.droid.solver.askapp.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class SurveyViewHolder extends RecyclerView.ViewHolder {
    CardView rootCardView;
    TextView timeAgo;
    TextView statusTextView;
    CircleImageView dot;
    public SurveyViewHolder(@NonNull View itemView) {
        super(itemView);
        rootCardView=itemView.findViewById(R.id.root_card);
        timeAgo=itemView.findViewById(R.id.time_ago);
        statusTextView=itemView.findViewById(R.id.text_view1);
        dot=itemView.findViewById(R.id.dot);
    }

     void onCardClicked(Context context, SurveyModel model){
         Intent intent=new Intent(context,NotificationSurveyActivity.class);
         intent.putExtra("surveyId", model.getSurveyId());
         intent.putExtra("askerId", model.getAskerId());
         intent.putExtra("askerName", model.getAskerName());
         intent.putExtra("askerBio",model.getAskerBio());
         intent.putExtra("askerImageUrlLow", model.getAskerImageUrlLow());
         intent.putExtra("question", model.getQuestion());
         intent.putExtra("timeOfSurvey", model.getTimeOfSurvey());
         intent.putExtra("option1Value", model.getOption1Value());
         intent.putExtra("option2Value", model.getOption2Value());
         intent.putExtra("option3Value", model.getOption3Value());
         intent.putExtra("option4Value", model.getOption4Value());
         intent.putExtra("option1Count", model.getOption1Count());
         intent.putExtra("option2Count", model.getOption2Count());
         intent.putExtra("option3Count", model.getOption3Count());
         intent.putExtra("option4Count", model.getOption4Count());
         intent.putExtra("option1", model.isOption1());
         intent.putExtra("option2", model.isOption2());
         intent.putExtra("option3", model.isOption3());
         intent.putExtra("option4", model.isOption4());
         intent.putExtra("type", model.getType());
         intent.putExtra("notifiedTime", model.getNotifiedTime());
         context.startActivity(intent);
    }
}
