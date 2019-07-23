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
import com.droid.solver.askapp.Account.OtherAccountActivity;
import com.droid.solver.askapp.Main.LocalDatabase;
import com.droid.solver.askapp.R;
import de.hdodenhof.circleimageview.CircleImageView;

 class FollowerViewHolder extends RecyclerView.ViewHolder {
    CardView rootCardView;
    CircleImageView profileImage;
    TextView status,timeAgo,bio;
    FrameLayout frameLayout;
     FollowerViewHolder(@NonNull View itemView) {
        super(itemView);
        rootCardView=itemView.findViewById(R.id.root_card);
        profileImage=itemView.findViewById(R.id.liker_image);
        timeAgo=itemView.findViewById(R.id.time_ago);
        status=itemView.findViewById(R.id.text_view1);
        bio=itemView.findViewById(R.id.bio);
        frameLayout=itemView.findViewById(R.id.frameLayout);
    }
    void onCardClicked(final Context context,final FollowerModel model){
        if(!model.isStoredLocally()){
            model.setStoredLocally(true);
            saveNotificationToLocalDatabase(model.getFollowerId(), model, context);
        }
        Activity activity = (Activity) context;
        Intent intent=new Intent(context, OtherAccountActivity.class);
        intent.putExtra("profile_image", model.getFollowerImageUrl());
        intent.putExtra("uid", model.getFollowerId());
        intent.putExtra("user_name", model.getFollowerName());
        intent.putExtra("bio", model.getFollowerBio());
        intent.putExtra("notification", true);
        frameLayout.setVisibility(View.GONE);
        context.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);

    }
    private void saveNotificationToLocalDatabase(final String notificationId, final FollowerModel model, final Context context){

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                LocalDatabase db=new LocalDatabase(context.getApplicationContext());
                db.insertNotification(notificationId, "follower");
                db.insertNotificationFollower(model);
            }
        });

    }
}
