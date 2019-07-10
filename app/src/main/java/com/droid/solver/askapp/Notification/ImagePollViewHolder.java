package com.droid.solver.askapp.Notification;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.droid.solver.askapp.Main.LocalDatabase;
import com.droid.solver.askapp.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class ImagePollViewHolder extends RecyclerView.ViewHolder {
    CardView rootCardView;
    CircleImageView image1,image2,dot;
    TextView timeAgo,statusTextView,percent1,percent2;
    public ImagePollViewHolder(@NonNull View itemView) {
        super(itemView);
        rootCardView=itemView.findViewById(R.id.root_card_view);
        image1=itemView.findViewById(R.id.image1);
        image2=itemView.findViewById(R.id.image2);
        timeAgo=itemView.findViewById(R.id.time_ago);
        statusTextView=itemView.findViewById(R.id.text_view1);
        percent1=itemView.findViewById(R.id.textViewa);
        percent2=itemView.findViewById(R.id.textViewb);
        dot=itemView.findViewById(R.id.dot);
    }


    void onCardClicked(Context context, ImagePollModel model){
        model.setStoredLocally(true);
        dot.setVisibility(View.GONE);
        saveNotificationToLocalDatabase(model.getImagePollId(), model.getType(), model, context);
        Intent intent=new Intent(context,NotificationImagePollActivity.class);
        intent.putExtra("imagePollId", model.getImagePollId());
        intent.putExtra("question",model.getQuestion());
        intent.putExtra("askerId",model.getAskerId());
        intent.putExtra("askerName", model.getAskerName());
        intent.putExtra("askerBio",model.getAskerBio());
        intent.putExtra("askerImageUrlLow", model.getAskerImageUrlLow());
        intent.putExtra("image1Url", model.getImage1Url());
        intent.putExtra("image2Url", model.getImage2Url());
        intent.putExtra("image1LikeNo", model.getImage1LikeNo());
        intent.putExtra("image2LikeNo", model.getImage2LikeNo());
        intent.putExtra("containVioloanceOrAdult", model.isContainVioloanceOrAdult());
        intent.putExtra("reported",model.isReported());
        intent.putExtra("type", model.getType());
        intent.putExtra("notifiedTime",model.getNotifiedTime());
        context.startActivity(intent);
    }
    private void saveNotificationToLocalDatabase(final String notificationId,final String type,final ImagePollModel model,final Context context){

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                LocalDatabase db=new LocalDatabase(context.getApplicationContext());
                db.insertNotification(notificationId, type);
                db.insertNotificationImagePoll(model);
            }
        });

    }
}
